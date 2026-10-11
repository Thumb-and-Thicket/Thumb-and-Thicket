package net.jolene.thumbandthicket.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.UndergroundPlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModFeatureReplacements {
    public static void replaceFeatures() {
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_grass")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasTag(ConventionalBiomeTags.IS_SNOWY) && context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_GRASS_BADLANDS)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.PATCH_GRASS_BADLANDS
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_grass_and_ferns")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasTag(ConventionalBiomeTags.IS_SNOWY) && context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_GRASS_TAIGA_2)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.PATCH_GRASS_TAIGA_2
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_large_ferns")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasTag(ConventionalBiomeTags.IS_SNOWY) && context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_LARGE_FERN)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.PATCH_LARGE_FERN
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_tall_grass")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasTag(ConventionalBiomeTags.IS_SNOWY) && context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_TALL_GRASS_2)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.PATCH_TALL_GRASS_2
                )
        );
        removeFeature(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL, ModPlacedFeatures.RED_MUSHROOM_NORMAL, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.RED_MUSHROOM_NETHER, ModPlacedFeatures.RED_MUSHROOM_NETHER, GenerationStep.Feature.UNDERGROUND_DECORATION);
        removeFeature(VegetationPlacedFeatures.RED_MUSHROOM_TAIGA, ModPlacedFeatures.RED_MUSHROOM_TAIGA, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.RED_MUSHROOM_OLD_GROWTH, ModPlacedFeatures.RED_MUSHROOM_OLD_GROWTH, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.RED_MUSHROOM_SWAMP, ModPlacedFeatures.RED_MUSHROOM_SWAMP, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL, ModPlacedFeatures.BROWN_MUSHROOM_NORMAL, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_NETHER, ModPlacedFeatures.BROWN_MUSHROOM_NETHER, GenerationStep.Feature.UNDERGROUND_DECORATION);
        removeFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_TAIGA, ModPlacedFeatures.BROWN_MUSHROOM_TAIGA, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_OLD_GROWTH, ModPlacedFeatures.BROWN_MUSHROOM_OLD_GROWTH, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_SWAMP, ModPlacedFeatures.BROWN_MUSHROOM_SWAMP, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(UndergroundPlacedFeatures.SPORE_BLOSSOM, ModPlacedFeatures.AGED_SPORE_BLOSSOM, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.FLOWER_DEFAULT, ModPlacedFeatures.FLOWER_DEFAULT, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.FLOWER_SWAMP, ModPlacedFeatures.FLOWER_SWAMP, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.FLOWER_FLOWER_FOREST, ModPlacedFeatures.FLOWER_FLOWER_FOREST, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.FLOWER_FOREST_FLOWERS, ModPlacedFeatures.FLOWER_FOREST_FLOWERS, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.FOREST_FLOWERS, ModPlacedFeatures.FOREST_FLOWERS, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.FLOWER_WARM, ModPlacedFeatures.FLOWER_WARM, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.DARK_FOREST_VEGETATION, ModPlacedFeatures.DARK_FOREST_VEGETATION, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.PATCH_WATERLILY, ModPlacedFeatures.LILY_PAD, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.PATCH_MELON, ModPlacedFeatures.MELON_PATCH, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.PATCH_MELON_SPARSE, ModPlacedFeatures.MELON_PATCH_SPARSE, GenerationStep.Feature.VEGETAL_DECORATION);
        removeFeature(VegetationPlacedFeatures.PATCH_PUMPKIN, ModPlacedFeatures.PUMPKIN_PATCH, GenerationStep.Feature.VEGETAL_DECORATION);
    }

    public static void addFeatures() {
        BiomeModifications.create(Identifier.of(MOD_ID, "add_snowy_grass")).add(
                ModificationPhase.ADDITIONS,
                context -> ((context.hasTag(ConventionalBiomeTags.IS_SNOWY)) && context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_GRASS_BADLANDS)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.SNOWY_GRASS
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_snowy_grass_and_ferns")).add(
                ModificationPhase.ADDITIONS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_GRASS_TAIGA_2) && context.hasTag(ConventionalBiomeTags.IS_SNOWY)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.SNOWY_TAIGA_GRASS
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_snowy_large_fern")).add(
                ModificationPhase.ADDITIONS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_LARGE_FERN) && context.hasTag(ConventionalBiomeTags.IS_SNOWY)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.SNOWY_LARGE_FERN
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_snowy_tall_grass")).add(
                ModificationPhase.ADDITIONS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_TALL_GRASS_2) && context.hasTag(ConventionalBiomeTags.IS_SNOWY)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.SNOWY_TALL_GRASS
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_normal_red_mushrooms")).add(
                ModificationPhase.ADDITIONS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.RED_MUSHROOM_NORMAL
                )
        );

        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_TEMPERATE), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.CLOVERS);
        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_DRY), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.TINGED_GRASS);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.MUSHROOM_FIELDS), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.PURPLE_MUSHROOM_TAIGA);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.MEADOW), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.MILKWEED);
        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_TEMPERATE), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.CATTAIL);
        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_MUSHROOM), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.MYCELIAL_SPROUTS);
//        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_RIVER), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.LILY_PAD);
        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_RIVER), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.DUCKWEED);
        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_SWAMP), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.DUCKWEED_SWAMP);
//        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_OVERWORLD), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BROWN_MUSHROOM_TREE);
//        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_OVERWORLD), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.RED_MUSHROOM_TREE);
//        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_OVERWORLD), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.PURPLE_MUSHROOM_TREE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.CAVE_PARSNIPS);

    }

    private static void removeFeature(RegistryKey<PlacedFeature> feature, RegistryKey<PlacedFeature> feature1, GenerationStep.Feature step) {
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_"+feature.getValue().getPath())).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(feature)),
                context -> context.getGenerationSettings().removeFeature(
                        step,
                        feature
                )
        );
        addFeature(feature, feature1, step);
    }

    private static void addFeature(RegistryKey<PlacedFeature> feature, RegistryKey<PlacedFeature> feature1, GenerationStep.Feature step) {
        BiomeModifications.create(Identifier.of(MOD_ID, "add_"+feature1.getValue().getPath())).add(
                ModificationPhase.ADDITIONS,
                context -> (context.hasPlacedFeature(feature)),
                context -> context.getGenerationSettings().addFeature(
                        step,
                        feature1
                )
        );
    }

}
