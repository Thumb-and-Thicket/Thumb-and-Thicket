package net.jolene.thumbandthicket.entity.custom.goals;

import net.jolene.thumbandthicket.entity.ModEntities;
import net.jolene.thumbandthicket.entity.custom.DeerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.EnumSet;
import java.util.Set;

public class DeerStartleGoal extends Goal {

    private static final double THREAT_RANGE = 10.0D;
    private static final double RUN_SPEED = 1.2D;

    private static final int STARTLE_TIME = 10;
    private static final int RUN_TIME = 80;

    // NON HOSTILE THREATS HERE
    private static final Set<EntityType<?>> EXTRA_THREATS = Set.of(
            EntityType.POLAR_BEAR,
            ModEntities.BROWN_BEAR
    );

    private final DeerEntity deer;

    private Entity threat;
    private int ticks;

    public DeerStartleGoal(DeerEntity deer) {

        this.deer = deer;

        this.setControls(
                EnumSet.of(
                        Control.MOVE,
                        Control.LOOK
                )
        );
    }

    @Override
    public boolean canStart() {

        if (this.deer.isTransfixed()) {
            return false;
        }

        this.threat = findThreat();

        return this.deer.hasStartleRequest()
                || this.threat != null;
    }

    @Override
    public boolean shouldContinue() {

        return this.ticks > 0
                && !this.deer.isTransfixed();
    }

    @Override
    public void start() {

        this.ticks =
                STARTLE_TIME + RUN_TIME;

        this.deer.setStartleRequested(false);
        this.deer.setStaring(false);
        this.deer.setPanicking(false);
        this.deer.setStartled(true);

        this.deer.getNavigation().stop();

        // PARTICLES
        if (this.deer.getWorld() instanceof ServerWorld world) {

            world.spawnParticles(
                    ParticleTypes.ANGRY_VILLAGER,
                    this.deer.getX(),
                    this.deer.getY() + 1.5D,
                    this.deer.getZ(),
                    1,
                    0.0D,
                    0.05D,
                    0.0D,
                    0.0D
            );
        }
    }

    @Override
    public void tick() {

        this.ticks--;

        if (this.ticks > RUN_TIME) {

            this.deer.setStartled(true);
            this.deer.setPanicking(false);

            this.deer.getNavigation().stop();

            if (this.threat != null
                    && this.threat.isAlive()) {

                this.deer.getLookControl().lookAt(
                        this.threat
                );
            }

            return;
        }

        this.deer.setStartled(false);
        this.deer.setPanicking(true);

        if (this.threat != null
                && this.threat.isAlive()) {

            this.runAway();
        }
    }

    private void runAway() {

        double dx =
                this.deer.getX() - this.threat.getX();

        double dz =
                this.deer.getZ() - this.threat.getZ();

        double length =
                Math.sqrt(dx * dx + dz * dz);

        if (length < 0.001D) {
            return;
        }

        dx /= length;
        dz /= length;

        this.deer.getNavigation().startMovingTo(
                this.deer.getX() + dx * 12.0D,
                this.deer.getY(),
                this.deer.getZ() + dz * 12.0D,
                RUN_SPEED
        );
    }

    private Entity findThreat() {

        Box box =
                this.deer.getBoundingBox()
                        .expand(THREAT_RANGE);

        Entity closest = null;
        double distance = Double.MAX_VALUE;

        for (Entity entity :
                this.deer.getWorld().getOtherEntities(
                        this.deer,
                        box,
                        this::isThreat
                )) {

            double current =
                    this.deer.squaredDistanceTo(entity);

            if (current < distance) {
                distance = current;
                closest = entity;
            }
        }

        return closest;
    }

    private boolean isThreat(Entity entity) {

        if (!entity.isAlive()) {
            return false;
        }

        // CREATIVE PLAYERS DON'T SCARE
        if (entity instanceof PlayerEntity player) {
            return !player.isCreative()
                    && !player.isSpectator()
                    && !this.deer.isPlayerHoldingFood(player);
        }

        return entity instanceof HostileEntity
                || EXTRA_THREATS.contains(
                entity.getType()
        );
    }

    @Override
    public void stop() {

        this.deer.getNavigation().stop();

        this.deer.setStartled(false);
        this.deer.setPanicking(false);

        this.threat = null;
        this.ticks = 0;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }
}