package net.jolene.thumbandthicket.entity.custom.goals;

import net.jolene.thumbandthicket.entity.custom.BeaverEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class GnawLogGoal extends Goal {

    private final BeaverEntity mob;
    private final int range;

    @Nullable
    private BlockPos targetPos;

    @Nullable
    private BlockPos standPos;

    private int gnawTime;
    private int cooldown;
    private int pathDelay;
    private int soundDelay;

    public GnawLogGoal(BeaverEntity mob, int range) {
        this.mob = mob;
        this.range = range;

        this.setControls(EnumSet.of(Control.LOOK, Control.MOVE
        ));
    }

    @Override
    public boolean canStart() {
        if (this.mob.isTouchingWater()) return false;
        if (this.cooldown > 0) {
            this.cooldown--;
            return false;
        }

        if (this.mob.getRandom().nextFloat() >= 0.5F) return false;
        this.targetPos = findVerticalLog(this.mob.getWorld(), this.mob, this.range);
        if (this.targetPos == null) return false;
        this.standPos = findStandPosition(this.targetPos);

        return this.standPos != null;
    }

    @Nullable
    private static BlockPos findVerticalLog(BlockView world, Entity entity, int range) {
        return BlockPos.findClosest(entity.getBlockPos(), range, range, pos -> isVerticalLog(world.getBlockState(pos))).orElse(null);
    }

    @Nullable
    private BlockPos findStandPosition(BlockPos logPos) {

        BlockPos closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos pos = logPos.offset(direction);

            if (!this.mob.getWorld().isAir(pos) || !this.mob.getWorld().isAir(pos.up())) continue;
            BlockPos ground = pos.down();

            if (!this.mob.getWorld().getBlockState(ground).isSideSolidFullSquare(this.mob.getWorld(), ground, Direction.UP)) continue;

            double dx = this.mob.getX() - (pos.getX() + 0.5D);
            double dz = this.mob.getZ() - (pos.getZ() + 0.5D);
            double distance = dx * dx + dz * dz;

            if (distance < closestDistance) {
                closestDistance = distance;
                closest = pos;
            }
        }
        return closest;
    }

    @Override
    public boolean shouldContinue() {
        return this.targetPos != null && this.standPos != null && this.gnawTime > 0 && this.mob.isAlive() && !this.mob.isTouchingWater() && isVerticalLog(this.mob.getWorld().getBlockState(this.targetPos)
        );
    }

    @Override
    public void start() {
        this.gnawTime = this.getTickCount(200 + this.mob.getRandom().nextInt(200));

        this.pathDelay = 0;
        this.soundDelay = 0;

        this.mob.setGnawing(false);
    }

    @Override
    public void stop() {

        this.mob.setGnawing(false);
        this.mob.getNavigation().stop();

        this.targetPos = null;
        this.standPos = null;

        this.cooldown = this.getTickCount(200);
    }

    @Override
    public void tick() {
        if (this.targetPos == null || this.standPos == null) return;

        BlockState state = this.mob.getWorld().getBlockState(this.targetPos);

        if (!isVerticalLog(state)) {
            this.stop();
            return;
        }

        double standX = this.standPos.getX() + 0.5D;
        double standY = this.standPos.getY();
        double standZ = this.standPos.getZ() + 0.5D;
        double dx = this.mob.getX() - standX;
        double dz = this.mob.getZ() - standZ;
        double distanceSquared = dx * dx + dz * dz;

        if (distanceSquared > 0.16D) {
            this.mob.setGnawing(false);
            if (--this.pathDelay <= 0 || this.mob.getNavigation().isIdle()) {
                this.pathDelay = 5;
                this.mob.getNavigation().startMovingTo(standX, standY, standZ, 0.75D);
            }
            return;
        }

        this.mob.getNavigation().stop();
//        faceLog();
        this.mob.setGnawing(true);

        if (this.mob.getRandom().nextInt(3) == 0) spawnParticles(state);
        if (--this.soundDelay <= 0) {
            this.soundDelay = 5;
            this.mob.getWorld().playSound(null, this.mob.getBlockPos(), SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.NEUTRAL, 0.35F, 1.15F + this.mob.getRandom().nextFloat() * 0.15F);
        }

        this.gnawTime--;
    }

    private static boolean isVerticalLog(BlockState state) {
        return state.isIn(BlockTags.LOGS) && state.contains(Properties.AXIS) && state.get(Properties.AXIS) == Direction.Axis.Y;
    }

    private void faceLog() {
        double logX = this.targetPos.getX() + 0.5D;
        double logZ = this.targetPos.getZ() + 0.5D;

        float yaw = (float) Math.toDegrees(Math.atan2(logZ - this.mob.getZ(), logX - this.mob.getX())) - 90.0F;

        this.mob.setYaw(yaw);
        this.mob.setHeadYaw(yaw);
        this.mob.bodyYaw = yaw;
    }

    private void spawnParticles(BlockState state) {
        if (!(this.mob.getWorld() instanceof ServerWorld world)) return;
        double yaw = Math.toRadians(this.mob.getYaw());

        world.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, state), this.mob.getX() - Math.sin(yaw) * 0.7D, this.mob.getY() + this.mob.getHeight() * 0.7D, this.mob.getZ() + Math.cos(yaw) * 0.7D, 4, 0.2D, 0.2D, 0.2D, 0.1D);
    }
}