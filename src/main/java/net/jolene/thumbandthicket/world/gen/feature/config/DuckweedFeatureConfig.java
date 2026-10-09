package net.jolene.thumbandthicket.world.gen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record DuckweedFeatureConfig(int tries, int xzSpread, int ySpread, BlockStateProvider stateProvider) implements FeatureConfig {
    public static final Codec<DuckweedFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("tries").forGetter(DuckweedFeatureConfig::tries), Codec.INT.fieldOf("xz_spread").forGetter(DuckweedFeatureConfig::xzSpread),  Codec.INT.fieldOf("y_spread").forGetter(DuckweedFeatureConfig::ySpread), BlockStateProvider.TYPE_CODEC.fieldOf("state_provider").forGetter(DuckweedFeatureConfig::stateProvider)).apply(instance, DuckweedFeatureConfig::new));}
