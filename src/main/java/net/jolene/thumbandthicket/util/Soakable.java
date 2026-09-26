package net.jolene.thumbandthicket.util;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.serialization.Codec;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Oxidizable;
import net.minecraft.util.StringIdentifiable;

import java.util.Optional;
import java.util.function.Supplier;

import static net.jolene.thumbandthicket.block.ModBlocks.*;

public interface Soakable extends Wettable<Soakable.WetnessLevel> {
    Supplier<BiMap<Object, Object>> SOAKED_LEVEL_INCREASES = Suppliers.memoize(() -> ImmutableBiMap.builder()
            .put(Blocks.SAND, ModBlocks.DAMP_SAND).put(ModBlocks.DAMP_SAND, WET_SAND)
            .put(Blocks.RED_SAND, ModBlocks.DAMP_RED_SAND).put(ModBlocks.DAMP_RED_SAND, WET_RED_SAND)
            .put(Blocks.SUSPICIOUS_SAND, ModBlocks.DAMP_SUSPICIOUS_SAND).put(ModBlocks.DAMP_SUSPICIOUS_SAND, WET_SUSPICIOUS_SAND)
            .build());
    Supplier<BiMap<Object, Object>> SOAKED_LEVEL_DECREASES = Suppliers.memoize(() -> SOAKED_LEVEL_INCREASES.get().inverse());

    static Optional<Block> getDecreasedSoakedBlock(Block block) {
        return Optional.ofNullable((Block)SOAKED_LEVEL_DECREASES.get().get(block));
    }

    static Block getUnaffectedBlock(Block block) {
        Block block2 = block;
        Block block3 = (Block)SOAKED_LEVEL_DECREASES.get().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = (Block)SOAKED_LEVEL_DECREASES.get().get(block2);
        }
        return block2;
    }

    @Override
    default Optional<BlockState> getDecreasedSoakedState(BlockState state) {
        return getDecreasedSoakedBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    static Optional<Block> getIncreasedWetnessBlock(Block block) {
        return Optional.ofNullable((Block)SOAKED_LEVEL_INCREASES.get().get(block));
    }

    static BlockState getUnaffectedState(BlockState state) {
        return getUnaffectedBlock(state.getBlock()).getStateWithProperties(state);
    }

    @Override
    default Optional<BlockState> getMaxSoakedResult(BlockState state) {
        return getIncreasedWetnessBlock(state.getBlock()).flatMap(block -> {
            if (block instanceof Soakable soakable) return soakable.getSoakingResult(block.getStateWithProperties(state));
            return Optional.empty();
        });
    }

    @Override
    default Optional<BlockState> getSoakingResult(BlockState state) {
        return getIncreasedWetnessBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    enum WetnessLevel implements StringIdentifiable {
        DRY("dry"),
        DAMP("damp"),
        WET("wet");

        public static final Codec<Oxidizable.OxidationLevel> CODEC;
        private final String id;

        WetnessLevel(String id) {
            this.id = id;
        }

        @Override
        public String asString() {
            return this.id;
        }

        static {
            CODEC = StringIdentifiable.createCodec(Oxidizable.OxidationLevel::values);
        }
    }
}
