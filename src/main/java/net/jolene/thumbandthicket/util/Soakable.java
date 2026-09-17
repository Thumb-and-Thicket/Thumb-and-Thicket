//package net.jolene.thumbandthicket.util;
//
//import com.google.common.base.Suppliers;
//import com.google.common.collect.BiMap;
//import com.google.common.collect.ImmutableBiMap;
//import com.mojang.serialization.Codec;
//import net.jolene.thumbandthicket.block.ModBlocks;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.Blocks;
//import net.minecraft.block.Oxidizable;
//import net.minecraft.util.StringIdentifiable;
//
//import java.util.Optional;
//import java.util.function.Supplier;
//
//import static net.jolene.thumbandthicket.block.ModBlocks.*;
//
//public interface Soakable extends Wettable {
//    static final Supplier<BiMap<Object, Object>> SOAKED_LEVEL_INCREASES = Suppliers.memoize(() -> ImmutableBiMap.builder()
//            .put(Blocks.SAND, ModBlocks.DAMP_SAND).put(ModBlocks.DAMP_SAND, WET_SAND)
//            .put(Blocks.RED_SAND, ModBlocks.DAMP_RED_SAND).put(ModBlocks.DAMP_RED_SAND, WET_RED_SAND)
//            .put(Blocks.SUSPICIOUS_SAND, ModBlocks.DAMP_SUSPICIOUS_SAND).put(ModBlocks.DAMP_SUSPICIOUS_SAND, WET_SUSPICIOUS_SAND)
//            .build());
//    static final Supplier<BiMap<Object, Object>> SOAKED_LEVEL_DECREASES = Suppliers.memoize(() -> SOAKED_LEVEL_INCREASES.get().inverse());
//
//    public static Optional<Block> getDecreasedOxidationBlock(Block block) {
//        return Optional.ofNullable((Block)SOAKED_LEVEL_DECREASES.get().get(block));
//    }
//
//    public static Block getUnaffectedOxidationBlock(Block block) {
//        Block block2 = block;
//        Block block3 = (Block)SOAKED_LEVEL_DECREASES.get().get(block2);
//        while (block3 != null) {
//            block2 = block3;
//            block3 = (Block)SOAKED_LEVEL_DECREASES.get().get(block2);
//        }
//        return block2;
//    }
//
//    public static Optional<BlockState> getDecreasedOxidationState(BlockState state) {
//        return getDecreasedOxidationBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
//    }
//
//    public static Optional<Block> getIncreasedOxidationBlock(Block block) {
//        return Optional.ofNullable((Block)SOAKED_LEVEL_INCREASES.get().get(block));
//    }
//
//    public static BlockState getUnaffectedOxidationState(BlockState state) {
//        return getUnaffectedOxidationBlock(state.getBlock()).getStateWithProperties(state);
//    }
//
//    @Override
//    default public Optional<BlockState> getDegradationResult(BlockState state) {
//        return getIncreasedOxidationBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
//    }
//
//
//    public static enum OxidationLevel implements StringIdentifiable
//    {
//        DRY("dry"),
//        DAMP("damp"),
//        WET("wet");
//
//        public static final Codec<Oxidizable.OxidationLevel> CODEC;
//        private final String id;
//
//        private OxidationLevel(String id) {
//            this.id = id;
//        }
//
//        @Override
//        public String asString() {
//            return this.id;
//        }
//
//        static {
//            CODEC = StringIdentifiable.createCodec(Oxidizable.OxidationLevel::values);
//        }
//    }
//}
