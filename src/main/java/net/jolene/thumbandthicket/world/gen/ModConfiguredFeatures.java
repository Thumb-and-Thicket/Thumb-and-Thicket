package net.jolene.thumbandthicket.world.gen;

import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.world.gen.feature.ModFeatures;
import net.jolene.thumbandthicket.world.gen.feature.config.DuckweedFeatureConfig;
import net.jolene.thumbandthicket.world.gen.feature.config.HangingRootsFeatureConfig;
import net.jolene.thumbandthicket.world.gen.feature.config.TallWaterloggedPlantFeatureConfig;
import net.jolene.thumbandthicket.world.gen.feature.config.WaterloggedPlantFeatureConfig;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.stateprovider.*;

import java.util.List;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;
import static net.jolene.thumbandthicket.world.gen.feature.ModFeatures.HUGE_PURPLE_MUSHROOM_FEATURE;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_PURPLE_MUSHROOM_KEY = registerKey("huge_purple_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SNOWY_TAIGA_GRASS_KEY = registerKey("snowy_taiga_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SNOWY_GRASS_KEY = registerKey("snowy_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SNOWY_TALL_GRASS_KEY = registerKey("snowy_tall_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SNOWY_LARGE_FERN_KEY = registerKey("snowy_large_fern");

    public static final RegistryKey<ConfiguredFeature<?, ?>> TINGED_GRASS_KEY = registerKey("tinged_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CLOVERS_KEY = registerKey("clovers");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MILKWEED_KEY = registerKey("milkweed");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CATTAIL_KEY = registerKey("cattail");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BEACH_GRASS_KEY = registerKey("beach_grass");

    public static final RegistryKey<ConfiguredFeature<?, ?>> LILY_PAD_KEY = registerKey("lily_pad");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DUCKWEED_SWAMP = registerKey("duckweed_swamp");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DUCKWEED = registerKey("duckweed");

    public static final RegistryKey<ConfiguredFeature<?, ?>> RED_MUSHROOM_NORMAL_KEY = registerKey("red_mushroom_normal");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BROWN_MUSHROOM_NORMAL_KEY = registerKey("brown_mushroom_normal");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PURPLE_MUSHROOM_NORMAL_KEY = registerKey("purple_mushroom_normal");
    public static final RegistryKey<ConfiguredFeature<?, ?>> RED_MUSHROOM_TREE_KEY = registerKey("red_mushroom_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BROWN_MUSHROOM_TREE_KEY = registerKey("brown_mushroom_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PURPLE_MUSHROOM_TREE_KEY = registerKey("purple_mushroom_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MYCELIAL_SPROUTS_KEY = registerKey("mycelial_sprouts");

    public static final RegistryKey<ConfiguredFeature<?, ?>> ROOTED_GRASS_KEY = registerKey("rooted_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ROOTS_KEY = registerKey("hanging_roots");

    public static final RegistryKey<ConfiguredFeature<?, ?>> CAVE_PARSNIPS_KEY = registerKey("cave_parsnips");

    public static final RegistryKey<ConfiguredFeature<?, ?>> AGED_SPORE_BLOSSOM_KEY = registerKey("spore_blossom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_DEFAULT_KEY = registerKey("flower_default");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_SWAMP_KEY = registerKey("flower_swamp");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_FLOWER_FOREST_KEY = registerKey("flower_forest");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_FLOWER_FOREST_2_KEY = registerKey("flower_forest_2");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FOREST_FLOWERS_KEY = registerKey("forest_flowers");

    public static final RegistryKey<ConfiguredFeature<?, ?>> DARK_FOREST_VEGETATION_KEY = registerKey("dark_forest_vegetation");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MUSHROOM_ISLAND_VEGETATION_KEY = registerKey("mushroom_island_vegetation");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        var placedFeatures = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);

        register(context, HUGE_PURPLE_MUSHROOM_KEY, HUGE_PURPLE_MUSHROOM_FEATURE, new HugeMushroomFeatureConfig(BlockStateProvider.of(ModBlocks.PURPLE_MUSHROOM_BLOCK), BlockStateProvider.of(Blocks.MUSHROOM_STEM),1));
        register(context, SNOWY_TAIGA_GRASS_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig( 32, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.SNOWY_SHORT_GRASS.getDefaultState().with(ModProperties.LAYERS, 1)).add(ModBlocks.SNOWY_SHORT_FERN.getDefaultState().with(ModProperties.LAYERS, 1)).build())))));
        register(context, SNOWY_GRASS_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(32, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.SNOWY_SHORT_GRASS.getDefaultState().with(ModProperties.LAYERS, 1))))));
        register(context, SNOWY_TALL_GRASS_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(32, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.SNOWY_TALL_GRASS.getDefaultState().with(ModProperties.LAYERS, 1))))));
        register(context, SNOWY_LARGE_FERN_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(32, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.SNOWY_LARGE_FERN.getDefaultState().with(ModProperties.LAYERS, 1))))));

        register(context, TINGED_GRASS_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(32, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.TINGED_SHORT_GRASS)))));
        register(context, MILKWEED_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(20, 5, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.MILKWEED)))));
        register(context, CATTAIL_KEY, ModFeatures.TALL_WATERLOGGED_PLANT_FEATURE, new TallWaterloggedPlantFeatureConfig(16, 7, 2, BlockStateProvider.of(ModBlocks.CATTAIL)));

        DataPool.Builder<BlockState> cloversBuilder = DataPool.builder();
        for (int i = 1; i <= 4; ++i) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                cloversBuilder.add(ModBlocks.CLOVERS.getDefaultState().with(FlowerbedBlock.FLOWER_AMOUNT, i).with(FlowerbedBlock.FACING, direction), 1);
            }
        }
        register(context, CLOVERS_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(8, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(cloversBuilder)))));

        register(context, RED_MUSHROOM_NORMAL_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(72, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(mushroomBuilder(Blocks.RED_MUSHROOM))))));
        register(context, BROWN_MUSHROOM_NORMAL_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(72, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(mushroomBuilder(Blocks.BROWN_MUSHROOM))))));
        register(context, PURPLE_MUSHROOM_NORMAL_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(72, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(mushroomBuilder(ModBlocks.PURPLE_MUSHROOM))))));
//        register(context, RED_MUSHROOM_TREE_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(72, 7, 12, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(treeMushroomBuilder(Blocks.RED_MUSHROOM))))));
//        register(context, BROWN_MUSHROOM_TREE_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(72, 7, 12, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(treeMushroomBuilder(Blocks.BROWN_MUSHROOM))))));
//        register(context, PURPLE_MUSHROOM_TREE_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(72, 7, 8, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(treeMushroomBuilder(ModBlocks.PURPLE_MUSHROOM))))));
        register(context, MYCELIAL_SPROUTS_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(32, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.MYCELIAL_SPROUTS)))));

        register(context, CAVE_PARSNIPS_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(72, 15, 10, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.CAVE_PARSNIP_CROP.getDefaultState().with(Properties.AGE_7, 7))))));
        register(context, ROOTS_KEY, ModFeatures.HANGING_ROOTS_FEATURE, new HangingRootsFeatureConfig(15, 7, 10, BlockStateProvider.of(Blocks.HANGING_ROOTS)));

        register(context, FLOWER_DEFAULT_KEY, Feature.FLOWER, new RandomPatchFeatureConfig(64, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(flowerBuilder(Blocks.POPPY,2,Blocks.DANDELION,1))))));
        register(context, FLOWER_SWAMP_KEY, Feature.FLOWER, new RandomPatchFeatureConfig(64, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(flowerBuilder(Blocks.BLUE_ORCHID,1,null,0))))));
        register(context, FLOWER_FLOWER_FOREST_KEY, Feature.FLOWER, new RandomPatchFeatureConfig(96, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new NoiseBlockStateProvider(2345L, new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0), 0.020833334f, List.of(Blocks.DANDELION.getDefaultState(), Blocks.POPPY.getDefaultState(), Blocks.ALLIUM.getDefaultState(), Blocks.AZURE_BLUET.getDefaultState(), Blocks.RED_TULIP.getDefaultState(), Blocks.ORANGE_TULIP.getDefaultState(), Blocks.WHITE_TULIP.getDefaultState(), Blocks.PINK_TULIP.getDefaultState(), Blocks.OXEYE_DAISY.getDefaultState(), Blocks.CORNFLOWER.getDefaultState(), Blocks.LILY_OF_THE_VALLEY.getDefaultState(), ModBlocks.SHORT_LILAC.getDefaultState()))))));
        register(context, FLOWER_FLOWER_FOREST_2_KEY, Feature.FLOWER, new RandomPatchFeatureConfig(96, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new NoiseBlockStateProvider(2345L, new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0), 0.020833334f, List.of(Blocks.DANDELION.getDefaultState(), Blocks.POPPY.getDefaultState(), Blocks.ALLIUM.getDefaultState(), Blocks.AZURE_BLUET.getDefaultState(), Blocks.RED_TULIP.getDefaultState(), Blocks.ORANGE_TULIP.getDefaultState(), Blocks.WHITE_TULIP.getDefaultState(), Blocks.PINK_TULIP.getDefaultState(), Blocks.OXEYE_DAISY.getDefaultState(), Blocks.CORNFLOWER.getDefaultState(), Blocks.LILY_OF_THE_VALLEY.getDefaultState(), ModBlocks.SHORT_LILAC.getDefaultState()))))));
        register(context, FOREST_FLOWERS_KEY, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfig(RegistryEntryList.of(PlacedFeatures.createEntry(Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.LILAC))), new PlacementModifier[0]), PlacedFeatures.createEntry(Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.ROSE_BUSH))), new PlacementModifier[0]), PlacedFeatures.createEntry(Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.PEONY))), new PlacementModifier[0]), PlacedFeatures.createEntry(Feature.NO_BONEMEAL_FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(flowerBuilder(Blocks.LILY_OF_THE_VALLEY, 1, null, 0)))), new PlacementModifier[0]))));

        register(context, DARK_FOREST_VEGETATION_KEY, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(configuredFeatures.getOrThrow(TreeConfiguredFeatures.HUGE_BROWN_MUSHROOM)), 0.025f), new RandomFeatureEntry(PlacedFeatures.createEntry(configuredFeatures.getOrThrow(TreeConfiguredFeatures.HUGE_RED_MUSHROOM)), 0.05f), new RandomFeatureEntry(PlacedFeatures.createEntry(configuredFeatures.getOrThrow(ModConfiguredFeatures.HUGE_PURPLE_MUSHROOM_KEY)), 0.015f), new RandomFeatureEntry(placedFeatures.getOrThrow(TreePlacedFeatures.DARK_OAK_CHECKED), 0.6666667f), new RandomFeatureEntry(placedFeatures.getOrThrow(TreePlacedFeatures.BIRCH_CHECKED), 0.2f), new RandomFeatureEntry(placedFeatures.getOrThrow(TreePlacedFeatures.FANCY_OAK_CHECKED), 0.1f)), placedFeatures.getOrThrow(TreePlacedFeatures.OAK_CHECKED)));

        DataPool.Builder<BlockState> sporeBlossomBuilder = DataPool.builder();
        for (int i = 0; i <= 2; ++i) {
            sporeBlossomBuilder.add(Blocks.SPORE_BLOSSOM.getDefaultState().with(Properties.AGE_2, i), 1);
        }
        register(context, AGED_SPORE_BLOSSOM_KEY, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(sporeBlossomBuilder)));
        register(context, LILY_PAD_KEY, ModFeatures.WATERLOGGED_PLANT_FEATURE, new WaterloggedPlantFeatureConfig(32, 10, 0, BlockStateProvider.of(Blocks.LILY_PAD)));
        register(context, DUCKWEED_SWAMP, ModFeatures.DUCKWEED_PLANT_FEATURE, new DuckweedFeatureConfig(72, 15, 0, BlockStateProvider.of(ModBlocks.DUCKWEED)));
        register(context, DUCKWEED, ModFeatures.DUCKWEED_PLANT_FEATURE, new DuckweedFeatureConfig(32, 10, 0, BlockStateProvider.of(ModBlocks.DUCKWEED)));

        register(context, ROOTED_GRASS_KEY, Feature.DISK, new DiskFeatureConfig(new PredicatedStateProvider(BlockStateProvider.of(Blocks.ROOTED_DIRT), List.of(new PredicatedStateProvider.Rule(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.solid(Direction.UP.getVector()), BlockPredicate.matchingFluids(Direction.UP.getVector(), Fluids.WATER), BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(Blocks.PODZOL))), BlockStateProvider.of(ModBlocks.ROOTED_GRASS)), new PredicatedStateProvider.Rule(BlockPredicate.allOf(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.solid(Direction.UP.getVector()), BlockPredicate.matchingFluids(Direction.UP.getVector(), Fluids.WATER), BlockPredicate.IS_AIR)), BlockPredicate.matchingBlocks(Blocks.PODZOL)), BlockStateProvider.of(ModBlocks.ROOTED_PODZOL)), new PredicatedStateProvider.Rule(BlockPredicate.allOf(BlockPredicate.solid(Direction.UP.getVector()), BlockPredicate.not(BlockPredicate.IS_AIR), BlockPredicate.matchingBlockTag(BlockTags.DIRT)), BlockStateProvider.of(Blocks.ROOTED_DIRT)), new PredicatedStateProvider.Rule(BlockPredicate.allOf(BlockPredicate.solid(Direction.UP.getVector()), BlockPredicate.IS_AIR), BlockStateProvider.of(Blocks.HANGING_ROOTS)))), BlockPredicate.allOf(BlockPredicate.matchingBlockTag(BlockTags.DIRT)), UniformIntProvider.create(2, 4), 2));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MOD_ID, name));
    }

    static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    private static DataPool.Builder<BlockState> mushroomBuilder(Block block) {
        DataPool.Builder<BlockState> mushroomBuilder = DataPool.builder();
        for (int i = 1; i <= 4; ++i) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                mushroomBuilder.add(block.getDefaultState().with(ModProperties.AMOUNT, i).with(Properties.FACING, direction).with(Properties.BLOCK_FACE, BlockFace.FLOOR), 1);
            }
        }
        return mushroomBuilder;
    }

    private static DataPool.Builder<BlockState> treeMushroomBuilder(Block block) {
        DataPool.Builder<BlockState> mushroomBuilder = DataPool.builder();
        for (int i = 1; i <= 4; ++i) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                mushroomBuilder.add(block.getDefaultState().with(ModProperties.AMOUNT, i).with(Properties.FACING, direction).with(Properties.BLOCK_FACE, BlockFace.WALL), 1);
            }
        }
        return mushroomBuilder;
    }

    private static DataPool.Builder<BlockState> flowerBuilder(Block block, int weight, Block block1, int weight1) {
        DataPool.Builder<BlockState> flowerBuilder = DataPool.builder();
        for (int i = 1; i <= 3; ++i) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                flowerBuilder.add(block.getDefaultState().with(ModProperties.FLOWERS, i).with(Properties.FACING, direction), weight);
            }
            if (block1 != null) {
                for (Direction direction : Direction.Type.HORIZONTAL) {
                    flowerBuilder.add(block1.getDefaultState().with(ModProperties.FLOWERS, i).with(Properties.FACING, direction), weight1);
                }
            }
        }
        return flowerBuilder;
    }

    private static List<BlockState> multiFlowerBuilder(List<Block> list) {
        List<BlockState> stateList = new java.util.ArrayList<>(List.of());
        for (Block block : list) {
            if (block instanceof FlowerBlock) for (int i = 1; i <= 3; ++i) {
                for (Direction direction : Direction.Type.HORIZONTAL) {
                    stateList.add(block.getDefaultState().with(ModProperties.FLOWERS, i).with(Properties.FACING, direction));
                }
            }
            else {
                stateList.add(block.getDefaultState());
            }
        }
        return stateList;
    }
}