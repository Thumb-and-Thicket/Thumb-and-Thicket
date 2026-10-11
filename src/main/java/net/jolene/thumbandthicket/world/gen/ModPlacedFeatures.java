package net.jolene.thumbandthicket.world.gen;

import net.jolene.thumbandthicket.world.gen.placementmodifier.SnowPlacementModifier;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ClampedIntProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModPlacedFeatures {


    public static final RegistryKey<PlacedFeature> HUGE_PURPLE_MUSHROOM_PLACED = registerKey("huge_purple_mushroom_placed");
    public static final RegistryKey<PlacedFeature> SNOWY_GRASS = registerKey("snowy_grass_placed");
    public static final RegistryKey<PlacedFeature> SNOWY_TAIGA_GRASS = registerKey("snowy_taiga_grass_placed");
    public static final RegistryKey<PlacedFeature> SNOWY_LARGE_FERN = registerKey("snowy_large_fern_placed");
    public static final RegistryKey<PlacedFeature> SNOWY_TALL_GRASS = registerKey("snowy_tall_grass_placed");

    public static final RegistryKey<PlacedFeature> TINGED_GRASS = registerKey("tinged_grass_placed");
    public static final RegistryKey<PlacedFeature> CLOVERS = registerKey("clover_placed");
    public static final RegistryKey<PlacedFeature> MILKWEED = registerKey("milkweed_placed");
    public static final RegistryKey<PlacedFeature> CATTAIL = registerKey("cattail_placed");
    public static final RegistryKey<PlacedFeature> AGED_SPORE_BLOSSOM = registerKey("spore_blossom");
    public static final RegistryKey<PlacedFeature> LILY_PAD = registerKey("lily_pad_placed");
    public static final RegistryKey<PlacedFeature> DUCKWEED_SWAMP = registerKey("duckweed_swamp_placed");
    public static final RegistryKey<PlacedFeature> DUCKWEED = registerKey("duckweed_placed");

    public static final RegistryKey<PlacedFeature> RED_MUSHROOM_NORMAL = registerKey("red_mushroom_normal_placed");
    public static final RegistryKey<PlacedFeature> RED_MUSHROOM_NETHER = registerKey("red_mushroom_nether_placed");
    public static final RegistryKey<PlacedFeature> RED_MUSHROOM_TAIGA = registerKey("red_mushroom_taiga_placed");
    public static final RegistryKey<PlacedFeature> RED_MUSHROOM_OLD_GROWTH = registerKey("red_mushroom_old_growth_placed");
    public static final RegistryKey<PlacedFeature> RED_MUSHROOM_SWAMP = registerKey("red_mushroom_swamp_placed");
    public static final RegistryKey<PlacedFeature> RED_MUSHROOM_TREE = registerKey("red_mushroom_tree_placed");

    public static final RegistryKey<PlacedFeature> BROWN_MUSHROOM_NORMAL = registerKey("brown_mushroom_normal_placed");
    public static final RegistryKey<PlacedFeature> BROWN_MUSHROOM_NETHER = registerKey("brown_mushroom_nether_placed");
    public static final RegistryKey<PlacedFeature> BROWN_MUSHROOM_TAIGA = registerKey("brown_mushroom_taiga_placed");
    public static final RegistryKey<PlacedFeature> BROWN_MUSHROOM_OLD_GROWTH = registerKey("brown_mushroom_old_growth_placed");
    public static final RegistryKey<PlacedFeature> BROWN_MUSHROOM_SWAMP = registerKey("brown_mushroom_swamp_placed");
    public static final RegistryKey<PlacedFeature> BROWN_MUSHROOM_TREE = registerKey("brown_mushroom_tree_placed");

    public static final RegistryKey<PlacedFeature> PURPLE_MUSHROOM_NORMAL = registerKey("purple_mushroom_normal_placed");
    public static final RegistryKey<PlacedFeature> PURPLE_MUSHROOM_TAIGA = registerKey("purple_mushroom_taiga_placed");
    public static final RegistryKey<PlacedFeature> PURPLE_MUSHROOM_OLD_GROWTH = registerKey("purple_mushroom_old_growth_placed");
    public static final RegistryKey<PlacedFeature> PURPLE_MUSHROOM_SWAMP = registerKey("purple_mushroom_swamp_placed");
    public static final RegistryKey<PlacedFeature> PURPLE_MUSHROOM_TREE = registerKey("purple_mushroom_tree_placed");
    public static final RegistryKey<PlacedFeature> MYCELIAL_SPROUTS = registerKey("mycelial_sprouts_placed");

    public static final RegistryKey<PlacedFeature> FLOWER_DEFAULT = registerKey("flower_default_placed");
    public static final RegistryKey<PlacedFeature> FLOWER_WARM = registerKey("flower_warm_placed");
    public static final RegistryKey<PlacedFeature> FLOWER_SWAMP = registerKey("flower_swamp_placed");
    public static final RegistryKey<PlacedFeature> FLOWER_FLOWER_FOREST = registerKey("flower_flower_forest_placed");
    public static final RegistryKey<PlacedFeature> FLOWER_FLOWER_FOREST_2 = registerKey("flower_flower_forest_placed_2");
    public static final RegistryKey<PlacedFeature> FLOWER_FOREST_FLOWERS = registerKey("flower_forest_flower_placed");
    public static final RegistryKey<PlacedFeature> FOREST_FLOWERS = registerKey("forest_flowers_placed");

    public static final RegistryKey<PlacedFeature> DARK_FOREST_VEGETATION = registerKey("dark_fores_vegetation_placed");

    public static final RegistryKey<PlacedFeature> ROOTED_GRASS = registerKey("rooted_grass_placed");
    public static final RegistryKey<PlacedFeature> HANGING_ROOTS = registerKey("hanging_roots_placed");

    public static final RegistryKey<PlacedFeature> PUMPKIN_PATCH = registerKey("pumpkin_placed");
    public static final RegistryKey<PlacedFeature> MELON_PATCH = registerKey("melon_placed");
    public static final RegistryKey<PlacedFeature> MELON_PATCH_SPARSE = registerKey("melon_sparse_placed");

    public static final RegistryKey<PlacedFeature> CAVE_PARSNIPS = registerKey("cave_parsnips_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> brownMushroom = registryEntryLookup.getOrThrow(ModConfiguredFeatures.BROWN_MUSHROOM_NORMAL_KEY);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> redMushroom = registryEntryLookup.getOrThrow(ModConfiguredFeatures.RED_MUSHROOM_NORMAL_KEY);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> purpleMushroom = registryEntryLookup.getOrThrow(ModConfiguredFeatures.PURPLE_MUSHROOM_NORMAL_KEY);

        register(context, SNOWY_GRASS, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNOWY_GRASS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SnowPlacementModifier.of(true), BiomePlacementModifier.of());
        register(context, SNOWY_TALL_GRASS, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNOWY_TALL_GRASS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SnowPlacementModifier.of(true), BiomePlacementModifier.of());
        register(context, SNOWY_LARGE_FERN, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNOWY_LARGE_FERN_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SnowPlacementModifier.of(true), BiomePlacementModifier.of());
        register(context, SNOWY_TAIGA_GRASS, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNOWY_TAIGA_GRASS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SnowPlacementModifier.of(true), BiomePlacementModifier.of());

        register(context, TINGED_GRASS, configuredFeatures.getOrThrow(ModConfiguredFeatures.TINGED_GRASS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SnowPlacementModifier.of(false), BiomePlacementModifier.of());
        register(context, MILKWEED, configuredFeatures.getOrThrow(ModConfiguredFeatures.MILKWEED_KEY), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of(), RarityFilterPlacementModifier.of(5));
        register(context, CATTAIL, configuredFeatures.getOrThrow(ModConfiguredFeatures.CATTAIL_KEY), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of(), RarityFilterPlacementModifier.of(2));
        register(context, LILY_PAD, configuredFeatures.getOrThrow(ModConfiguredFeatures.LILY_PAD_KEY), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of(), RarityFilterPlacementModifier.of(2), SnowPlacementModifier.of(false));
        register(context, DUCKWEED, configuredFeatures.getOrThrow(ModConfiguredFeatures.DUCKWEED), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of(), RarityFilterPlacementModifier.of(2), SnowPlacementModifier.of(false));
        register(context, DUCKWEED_SWAMP, configuredFeatures.getOrThrow(ModConfiguredFeatures.DUCKWEED_SWAMP), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of(), RarityFilterPlacementModifier.of(2), SnowPlacementModifier.of(false));
        register(context, CLOVERS, configuredFeatures.getOrThrow(ModConfiguredFeatures.CLOVERS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SnowPlacementModifier.of(false), BiomePlacementModifier.of());

        register(context, RED_MUSHROOM_NORMAL, redMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(512));
        register(context, RED_MUSHROOM_NETHER, redMushroom, SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, RarityFilterPlacementModifier.of(2));
        register(context, RED_MUSHROOM_TAIGA, redMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(256));
        register(context, RED_MUSHROOM_OLD_GROWTH, redMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(171));
        register(context, RED_MUSHROOM_SWAMP, redMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(64));
//        register(context, RED_MUSHROOM_TREE, redMushroom, SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(30), YOffset.aboveBottom(80)), RarityFilterPlacementModifier.of(64));

        register(context, BROWN_MUSHROOM_NORMAL, brownMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(256));
        register(context, BROWN_MUSHROOM_NETHER, brownMushroom, SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, RarityFilterPlacementModifier.of(2));
        register(context, BROWN_MUSHROOM_TAIGA, brownMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(4));
        register(context, BROWN_MUSHROOM_OLD_GROWTH, brownMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(4), CountPlacementModifier.of(3));
        register(context, BROWN_MUSHROOM_SWAMP, brownMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(1), CountPlacementModifier.of(2));
//        register(context, BROWN_MUSHROOM_TREE, brownMushroom, SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(30), YOffset.aboveBottom(80)), RarityFilterPlacementModifier.of(1), CountPlacementModifier.of(2));

        register(context, PURPLE_MUSHROOM_NORMAL, purpleMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(512));
        register(context, PURPLE_MUSHROOM_TAIGA, purpleMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(8));
        register(context, PURPLE_MUSHROOM_OLD_GROWTH, purpleMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(44), CountPlacementModifier.of(3));
        register(context, PURPLE_MUSHROOM_SWAMP, purpleMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(161), CountPlacementModifier.of(2));
//        register(context, PURPLE_MUSHROOM_TREE, purpleMushroom, SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(30), YOffset.aboveBottom(80)), RarityFilterPlacementModifier.of(161), CountPlacementModifier.of(2));

        register(context, CAVE_PARSNIPS, configuredFeatures.getOrThrow(ModConfiguredFeatures.CAVE_PARSNIPS_KEY), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-64), YOffset.fixed(50)), BiomePlacementModifier.of(), CountPlacementModifier.of(5));
        register(context, HANGING_ROOTS, configuredFeatures.getOrThrow(ModConfiguredFeatures.ROOTS_KEY), SquarePlacementModifier.of(), BiomePlacementModifier.of(), CountPlacementModifier.of(5));

        register(context, MYCELIAL_SPROUTS, configuredFeatures.getOrThrow(ModConfiguredFeatures.MYCELIAL_SPROUTS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, HUGE_PURPLE_MUSHROOM_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.HUGE_PURPLE_MUSHROOM_KEY));

        register(context, PUMPKIN_PATCH, configuredFeatures.getOrThrow(ModConfiguredFeatures.PUMPKIN_KEY), new PlacementModifier[]{RarityFilterPlacementModifier.of(300), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()});
        register(context, MELON_PATCH, configuredFeatures.getOrThrow(ModConfiguredFeatures.MELON_KEY), new PlacementModifier[]{RarityFilterPlacementModifier.of(6), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()});
        register(context, MELON_PATCH_SPARSE, configuredFeatures.getOrThrow(ModConfiguredFeatures.MELON_KEY), new PlacementModifier[]{RarityFilterPlacementModifier.of(64), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()});

        register(context, FLOWER_WARM, configuredFeatures.getOrThrow(ModConfiguredFeatures.FLOWER_DEFAULT_KEY), RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, FLOWER_DEFAULT, configuredFeatures.getOrThrow(ModConfiguredFeatures.FLOWER_DEFAULT_KEY), RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());PlacedFeatures.register(context, FLOWER_FLOWER_FOREST_2, configuredFeatures.getOrThrow(ModConfiguredFeatures.FLOWER_FLOWER_FOREST_2_KEY), CountPlacementModifier.of(3), RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, FLOWER_FLOWER_FOREST, configuredFeatures.getOrThrow(ModConfiguredFeatures.FLOWER_FLOWER_FOREST_KEY), CountPlacementModifier.of(3), RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, FLOWER_SWAMP, configuredFeatures.getOrThrow(ModConfiguredFeatures.FLOWER_SWAMP_KEY), RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, FLOWER_FOREST_FLOWERS, configuredFeatures.getOrThrow(ModConfiguredFeatures.FOREST_FLOWERS_KEY), RarityFilterPlacementModifier.of(7), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, CountPlacementModifier.of(ClampedIntProvider.create(UniformIntProvider.create(-1, 3), 0, 3)), BiomePlacementModifier.of());
        register(context, FOREST_FLOWERS, configuredFeatures.getOrThrow(ModConfiguredFeatures.FOREST_FLOWERS_KEY), RarityFilterPlacementModifier.of(7), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, CountPlacementModifier.of(ClampedIntProvider.create(UniformIntProvider.create(-3, 1), 0, 1)), BiomePlacementModifier.of());

        register(context, DARK_FOREST_VEGETATION, configuredFeatures.getOrThrow(ModConfiguredFeatures.DARK_FOREST_VEGETATION_KEY), CountPlacementModifier.of(16), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of());

        register(context, AGED_SPORE_BLOSSOM, configuredFeatures.getOrThrow(ModConfiguredFeatures.AGED_SPORE_BLOSSOM_KEY), CountPlacementModifier.of(25), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, EnvironmentScanPlacementModifier.of(Direction.UP, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12), RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)), BiomePlacementModifier.of());
        register(context, ROOTED_GRASS, configuredFeatures.getOrThrow(ModConfiguredFeatures.ROOTED_GRASS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
