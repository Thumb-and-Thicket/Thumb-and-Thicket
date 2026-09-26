package net.jolene.thumbandthicket.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static net.jolene.thumbandthicket.ThumbAndThicket.thumbandthicket$copyProperty;

public interface Wettable<T extends Enum<T>> {
    int SOAKING_RANGE = 3;

    Optional<BlockState> getSoakingResult(BlockState var1);
    Optional<BlockState> getMaxSoakedResult(BlockState var1);
    Optional<BlockState> getDecreasedSoakedState(BlockState var1);

    default void tickSoaking(BlockState state, ServerWorld world, BlockPos pos) {
        BlockEntity brushable = world.getBlockEntity(pos);
        trySoak(state, world, pos).ifPresent((wetState) -> {
            for (Property<?> property : state.getProperties()) if (wetState.contains(property)) wetState = thumbandthicket$copyProperty(wetState, state, property);
            world.setBlockState(pos, wetState);
            if (brushable instanceof BrushableBlockEntity brushableBlockEntity) {
                BlockEntity brushable2 = world.getBlockEntity(pos);
                if (brushable2 instanceof BrushableBlockEntity brushableBlockEntity2) brushableBlockEntity2.item = brushableBlockEntity.getItem();
            }
        });
    }

    T getSoakingLevel();

    default Optional<BlockState> trySoak(BlockState state, ServerWorld world, BlockPos pos) {
        int wetnessLevel = this.getSoakingLevel().ordinal();
        AtomicInteger maxLevel = new AtomicInteger(0);

        BlockPos.findClosest(pos, SOAKING_RANGE, SOAKING_RANGE, (pos2) -> {
            if (world.getFluidState(pos2).isIn(FluidTags.WATER)) {
                int distance = (int) pos2.getSquaredDistance(pos);
                if (SOAKING_RANGE >= distance) {
                    maxLevel.set(3 - distance);
                    return true;
                }
            }
            return false;
        });
        BlockPos[] positions = {pos.north(), pos.south(), pos.east(), pos.west(), pos.up(), pos.down()};
        for (BlockPos pos2 : positions) {
            if (world.getFluidState(pos2).isIn(FluidTags.WATER)) return this.getSoakingResult(state);
            if (world.getBlockState(pos2).getBlock() instanceof Soakable soakable) {
                if (soakable.getSoakingLevel().ordinal() > wetnessLevel && wetnessLevel < maxLevel.get()) return this.getSoakingResult(state);
            }
        }
        if (wetnessLevel > 0 && maxLevel.get() == 0 && Random.create().nextInt(8) == 0) return this.getDecreasedSoakedState(state);
        return Optional.empty();
    }
}
