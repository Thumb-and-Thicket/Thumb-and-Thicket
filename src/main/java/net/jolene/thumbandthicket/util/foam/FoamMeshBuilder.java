package net.jolene.thumbandthicket.util.foam;

import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;

public class FoamMeshBuilder {

    public static void rebuild(ClientWorld world, FoamChunk chunk) {
        chunk.quads.clear();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int startX = chunk.pos.getStartX();
        int startZ = chunk.pos.getStartZ();
        int bottomY = world.getBottomY();
        int topY = world.getTopY();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = bottomY; y < topY; y++) {
                    mutable.set(startX + x, y, startZ + z);
                    BlockState state = world.getBlockState(mutable);

                    if (state.getFluidState().getFluid() != Fluids.WATER) continue;
                    if (!state.getFluidState().isStill()) continue;
                    if (!world.getBlockState(mutable.up()).getFluidState().isEmpty()) continue;
                    if (world.getBlockState(mutable.up()).isOpaqueFullCube(world, mutable.up())) continue;

                    FoamShapeUtil set = FoamShapeUtil.getSpriteSet(world, mutable);

                    if (set == FoamShapeUtil.DISCONNECTED) continue;

                    chunk.quads.add(new FoamQuad(set.sprites[0], mutable.getX(), mutable.getY() + 0.901f, mutable.getZ(), mutable.toImmutable()));
                }
            }
        }
    }
}