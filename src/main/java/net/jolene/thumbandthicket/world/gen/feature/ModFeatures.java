package net.jolene.thumbandthicket.world.gen.feature;

import net.jolene.thumbandthicket.world.gen.feature.config.DuckweedFeatureConfig;
import net.jolene.thumbandthicket.world.gen.feature.config.HangingRootsFeatureConfig;
import net.jolene.thumbandthicket.world.gen.feature.config.TallWaterloggedPlantFeatureConfig;
import net.jolene.thumbandthicket.world.gen.feature.config.WaterloggedPlantFeatureConfig;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModFeatures {

    public static final Identifier HUGE_PURPLE_MUSHROOM_ID = Identifier.of(MOD_ID, "huge_purple_mushroom_feature");
    public static final HugePurpleMushroomFeature HUGE_PURPLE_MUSHROOM_FEATURE = new HugePurpleMushroomFeature(HugeMushroomFeatureConfig.CODEC);

    public static final Identifier TALL_WATERLOGGED_PLANT_ID = Identifier.of(MOD_ID, "tall_waterlogged_plant_feature");
    public static final TallWaterloggedPlantFeature TALL_WATERLOGGED_PLANT_FEATURE = new TallWaterloggedPlantFeature(TallWaterloggedPlantFeatureConfig.CODEC);

    public static final Identifier WATERLOGGED_PLANT_ID = Identifier.of(MOD_ID, "waterlogged_plant_feature");
    public static final WaterloggedPlantFeature WATERLOGGED_PLANT_FEATURE = new WaterloggedPlantFeature(WaterloggedPlantFeatureConfig.CODEC);

    public static final Identifier DUCKWEED_PLANT_ID = Identifier.of(MOD_ID, "duckweed_plant_feature");
    public static final DuckweedFeature DUCKWEED_PLANT_FEATURE = new DuckweedFeature(DuckweedFeatureConfig.CODEC);

    public static final Identifier HANGING_ROOTS_ID = Identifier.of(MOD_ID, "hanging_roots_feature");
    public static final HangingRootsFeature HANGING_ROOTS_FEATURE = new HangingRootsFeature(HangingRootsFeatureConfig.CODEC);

    public static void registerModFeatures() {
        Registry.register(Registries.FEATURE, HUGE_PURPLE_MUSHROOM_ID, HUGE_PURPLE_MUSHROOM_FEATURE);
        Registry.register(Registries.FEATURE, TALL_WATERLOGGED_PLANT_ID, TALL_WATERLOGGED_PLANT_FEATURE);
        Registry.register(Registries.FEATURE, WATERLOGGED_PLANT_ID, WATERLOGGED_PLANT_FEATURE);
        Registry.register(Registries.FEATURE, DUCKWEED_PLANT_ID, DUCKWEED_PLANT_FEATURE);
        Registry.register(Registries.FEATURE, HANGING_ROOTS_ID, HANGING_ROOTS_FEATURE);
    }
}
