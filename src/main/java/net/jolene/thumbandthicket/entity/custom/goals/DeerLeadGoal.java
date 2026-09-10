package net.jolene.thumbandthicket.entity.custom.goals;

import net.jolene.thumbandthicket.entity.custom.DeerEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;

import java.util.EnumSet;

public class DeerLeadGoal extends Goal {

    private static final double RANGE = 12.0D;
    private static final double SPEED = 0.5D;
    private static final int STARE_TIME = 40;

    private final DeerEntity deer;

    private PlayerEntity player;
    private int stareTicks;

    public DeerLeadGoal(DeerEntity deer) {

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

        PlayerEntity nearest = null;
        double closest = RANGE * RANGE;

        for (PlayerEntity player :
                this.deer.getWorld().getPlayers()) {

            if (!player.isAlive()
                    || !this.deer.isPlayerHoldingFood(player)) {
                continue;
            }

            double distance =
                    this.deer.squaredDistanceTo(player);

            if (distance < closest) {
                closest = distance;
                nearest = player;
            }
        }

        this.player = nearest;

        return this.player != null;
    }

    @Override
    public boolean shouldContinue() {

        return this.player != null
                && this.player.isAlive()
                && this.deer.isPlayerHoldingFood(this.player)
                && this.deer.squaredDistanceTo(this.player)
                <= RANGE * RANGE;
    }

    @Override
    public void start() {

        this.stareTicks = STARE_TIME;

        // FOOD BREAKS TRANCE
        this.deer.setPanicking(false);
        this.deer.setStartled(false);
        this.deer.setTransfixed(false);
        this.deer.setStaring(true);

        this.deer.getNavigation().stop();
    }

    @Override
    public void tick() {

        if (this.player == null) {
            return;
        }

        this.deer.getLookControl().lookAt(this.player);

        if (this.stareTicks > 0) {

            this.stareTicks--;

            this.deer.setStaring(true);
            this.deer.getNavigation().stop();

            return;
        }

        // FOLLOW
        this.deer.setStaring(false);

        this.deer.getNavigation().startMovingTo(
                this.player,
                SPEED
        );
    }

    @Override
    public void stop() {

        this.deer.getNavigation().stop();
        this.deer.setStaring(false);

        this.player = null;
        this.stareTicks = 0;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }
}