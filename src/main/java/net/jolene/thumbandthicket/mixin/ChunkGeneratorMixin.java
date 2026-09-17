package net.jolene.thumbandthicket.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin {

    @Inject(method = "generateFeatures", at = @At("TAIL"))
    private void thumbandthicket$replaceDirtWithMud(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo ci) {
        if (!(chunk instanceof ProtoChunk protoChunk)) return;
        thumbandthicket$convertBlocks(world, protoChunk);
    }

    @Unique
    private void thumbandthicket$convertBlocks(StructureWorldAccess world, ProtoChunk protoChunk) {
        Set<BlockPos> mudCandidates = new HashSet<>();
        Set<BlockPos> grassCandidates = new HashSet<>();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = protoChunk.getBottomY(); y < protoChunk.getTopY(); y++) {
                    BlockPos pos = new BlockPos(protoChunk.getPos().getStartX() + x, y, protoChunk.getPos().getStartZ() + z);
                    BlockState state = protoChunk.getBlockState(pos);

                    if (state.isIn(BlockTags.DIRT) && thumbandthicket$touchesWater(world, pos)) {
                        mudCandidates.add(pos);
                        thumbandthicket$spreadMud(world, protoChunk, pos, mudCandidates, 3, BlockTags.DIRT);
                    }

                    if (state.getBlock() instanceof LeavesBlock) {
                        BlockState newState = state;
                        newState = newState.with(ConnectingBlock.UP, world.getBlockState(pos.up()).isOf(state.getBlock())).with(ConnectingBlock.DOWN, world.getBlockState(pos.down()).isOf(state.getBlock())).with(ConnectingBlock.NORTH, world.getBlockState(pos.north()).isOf(state.getBlock())).with(ConnectingBlock.EAST, world.getBlockState(pos.east()).isOf(state.getBlock())).with(ConnectingBlock.SOUTH, world.getBlockState(pos.south()).isOf(state.getBlock())).with(ConnectingBlock.WEST, world.getBlockState(pos.west()).isOf(state.getBlock()));
                        protoChunk.setBlockState(pos, newState, false);
                    }

//                    if (world.getBiomeFabric(pos).isIn(ConventionalBiomeTags.IS_DESERT)) {
//                        if (thumbandthicket$touchesWater(world, pos)) {
//                            mudCandidates.add(pos);
//                            thumbandthicket$spreadMud(world, protoChunk, pos, mudCandidates, 3, BlockTags.SAND);
//                        }
//                        if (thumbandthicket$touchesMud(world, pos)) {
//                            grassCandidates.add(pos);
//                            thumbandthicket$spreadMud(world, protoChunk, pos, mudCandidates, 8, BlockTags.SAND);
//                        }
//                    }
                }
            }
        }
        for (BlockPos pos : mudCandidates) {
            if (protoChunk.getBlockState(pos).isIn(BlockTags.DIRT)) protoChunk.setBlockState(pos, Blocks.MUD.getDefaultState(), false);
        }
        for (BlockPos pos : grassCandidates) {
            if (protoChunk.getBlockState(pos).isIn(BlockTags.SAND) && world.getBlockState(pos.up()).isAir()) protoChunk.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState(), false);
            if (protoChunk.getBlockState(pos).isIn(BlockTags.SAND) && !world.getBlockState(pos.up()).isAir()) protoChunk.setBlockState(pos, Blocks.DIRT.getDefaultState(), false);
        }
    }

    @Unique
    private void thumbandthicket$spreadMud(StructureWorldAccess world, ProtoChunk chunk, BlockPos origin, Set<BlockPos> candidates, int distances, TagKey blockTag) {
        Queue<BlockPos> blockPosQueue = new ArrayDeque<>();
        Queue<Integer> distancesQueue = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();

        blockPosQueue.add(origin);
        distancesQueue.add(0);
        visited.add(origin);

        while (!blockPosQueue.isEmpty()) {
            BlockPos current = blockPosQueue.poll();
            int distance = distancesQueue.poll();

            if (distance >= distances) continue;

            for (Direction dir : Direction.Type.HORIZONTAL) {
                BlockPos next = current.offset(dir);

                if (visited.contains(next)) continue;
                visited.add(next);

                if (!chunk.getBlockState(next).isIn(blockTag)) continue;

                float chance = getChance(distances, distance);

                if (world.getRandom().nextFloat() < chance) {
                    candidates.add(next);
                    blockPosQueue.add(next);
                    distancesQueue.add(distance + 1);
                }
            }
        }
    }

    @Unique
    private static float getChance(int distances, int distance) {
        float chance;
        int nextDistance = distance + 1;
        if (distances == 3) {
            chance = switch (nextDistance) {
                case 1 -> 0.3f;
                case 2 -> 0.2f;
                case 3 -> 0.15f;
                default -> 0.05f;
            };
        } else {
            float progress = (float) (nextDistance - 1) / (distances - 1);
            chance = 0.5f - (0.15f * progress);
        }
        return chance;
    }

    @Unique
    private boolean thumbandthicket$touchesWater(StructureWorldAccess world, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            if (world.getFluidState(pos.offset(dir)).isIn(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }

    @Unique
    private boolean thumbandthicket$touchesMud(StructureWorldAccess world, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            if (world.getBlockState(pos.offset(dir)).isOf(Blocks.MUD)) {
                return true;
            }
        }
        return false;
    }


}
