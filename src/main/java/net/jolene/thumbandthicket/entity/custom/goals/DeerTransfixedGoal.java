package net.jolene.thumbandthicket.entity.custom.goals;

import net.jolene.thumbandthicket.entity.custom.DeerEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class DeerTransfixedGoal extends Goal {

    private static final int SEARCH_RADIUS = 8;
    private static final int CHECK_INTERVAL = 10;

    private static final double STARE_RANGE = 4.0D;
    private static final double WALK_SPEED = 0.65D;

    private static final int TRANCE_TIME = 400;

    private final DeerEntity deer;

    private BlockPos lightPos;
    private int tranceTicks;
    private int lightCheckTicks;

    public DeerTransfixedGoal(DeerEntity deer) {

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

        if (this.deer.isTransfixed()
                || this.deer.isLightOnCooldown()
                || this.deer.hasStartleRequest()) {
            return false;
        }

        this.lightPos = findLight();

        return this.lightPos != null;
    }

    @Override
    public boolean shouldContinue() {

        return this.lightPos != null
                && !this.deer.hasStartleRequest();
    }

    @Override
    public void start() {

        this.tranceTicks = 0;
        this.lightCheckTicks = 0;

        this.deer.setPanicking(false);
        this.deer.setStartled(false);
        this.deer.setStaring(false);
        this.deer.setTransfixed(false);

        this.deer.getNavigation().stop();
    }

    @Override
    public void tick() {

        if (this.lightPos == null) {
            return;
        }

        double x = this.lightPos.getX() + 0.5D;
        double y = this.lightPos.getY() + 0.5D;
        double z = this.lightPos.getZ() + 0.5D;

        // FACE LIGHT.
        this.deer.getLookControl().lookAt(x, y, z);

        if (!this.deer.isTransfixed()) {

            double distance = this.deer.squaredDistanceTo(
                    x,
                    y,
                    z
            );

            if (distance <= STARE_RANGE * STARE_RANGE) {

                // ARRIVE AT LIGHT.
                this.deer.getNavigation().stop();

                this.deer.setStaring(true);
                this.deer.setTransfixed(true);

                this.tranceTicks = TRANCE_TIME;
                this.lightCheckTicks = CHECK_INTERVAL;

                return;
            }

            // WALK TOWARDS LIGHT.
            this.deer.setStaring(false);

            this.deer.getNavigation().startMovingTo(
                    x,
                    this.lightPos.getY(),
                    z,
                    WALK_SPEED
            );

            return;
        }

        this.deer.getNavigation().stop();
        this.deer.setStaring(true);
        this.deer.setPanicking(false);
        this.deer.setStartled(false);

        if (--this.lightCheckTicks <= 0) {

            this.lightCheckTicks = CHECK_INTERVAL;

            if (!isValidLight(this.lightPos)
                    || !hasLineOfSight(this.lightPos)) {

                this.deer.setTransfixed(false);
                this.deer.setStaring(false);

                this.lightPos = null;

                return;
            }
        }

        if (--this.tranceTicks <= 0) {

            this.deer.setTransfixed(false);
            this.deer.setStaring(false);
            this.deer.startLightCooldown();

            this.lightPos = null;
        }
    }

    private BlockPos findLight() {

        BlockPos origin = this.deer.getBlockPos();
        BlockPos.Mutable pos = new BlockPos.Mutable();

        BlockPos closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (int x = -SEARCH_RADIUS; x <= SEARCH_RADIUS; x++) {
            for (int y = -SEARCH_RADIUS; y <= SEARCH_RADIUS; y++) {
                for (int z = -SEARCH_RADIUS; z <= SEARCH_RADIUS; z++) {

                    if (x * x + y * y + z * z
                            > SEARCH_RADIUS * SEARCH_RADIUS) {
                        continue;
                    }

                    pos.set(
                            origin.getX() + x,
                            origin.getY() + y,
                            origin.getZ() + z
                    );

                    BlockState state =
                            this.deer.getWorld().getBlockState(pos);

                    if (state.getLuminance() <= 0) {
                        continue;
                    }

                    double distance =
                            this.deer.squaredDistanceTo(
                                    pos.getX() + 0.5D,
                                    pos.getY() + 0.5D,
                                    pos.getZ() + 0.5D
                            );

                    if (distance >= closestDistance) {
                        continue;
                    }

                    // DO NOT TARGET LIGHT THROUGH BLOCKS.
                    if (!hasLineOfSight(pos)) {
                        continue;
                    }

                    closestDistance = distance;
                    closest = pos.toImmutable();
                }
            }
        }

        return closest;
    }

    private boolean hasLineOfSight(BlockPos pos) {

        Vec3d start = new Vec3d(
                this.deer.getX(),
                this.deer.getEyeY(),
                this.deer.getZ()
        );

        Vec3d end = new Vec3d(
                pos.getX() + 0.5D,
                pos.getY() + 0.5D,
                pos.getZ() + 0.5D
        );

        return this.deer.getWorld()
                .raycast(
                        new net.minecraft.world.RaycastContext(
                                start,
                                end,
                                net.minecraft.world.RaycastContext.ShapeType.COLLIDER,
                                net.minecraft.world.RaycastContext.FluidHandling.NONE,
                                this.deer
                        )
                )
                .getBlockPos()
                .equals(pos);
    }

    private boolean isValidLight(BlockPos pos) {

        return this.deer.getWorld()
                .getBlockState(pos)
                .getLuminance() > 0;
    }

    @Override
    public void stop() {

        this.deer.getNavigation().stop();

        if (this.deer.isTransfixed()) {
            this.deer.startLightCooldown();
        }

        this.deer.setTransfixed(false);
        this.deer.setStaring(false);

        this.lightPos = null;
        this.tranceTicks = 0;
        this.lightCheckTicks = 0;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }
}