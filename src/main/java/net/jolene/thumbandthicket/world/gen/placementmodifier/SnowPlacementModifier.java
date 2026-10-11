package net.jolene.thumbandthicket.world.gen.placementmodifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

import java.util.stream.Stream;

import static net.jolene.thumbandthicket.world.gen.placementmodifier.ModPlacementModifierType.SNOWY_BELOW;

public class SnowPlacementModifier extends PlacementModifier {
    public static final MapCodec<SnowPlacementModifier> MODIFIER_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.BOOL.fieldOf("should_be_snowy").forGetter(mod -> mod.shouldBeSnowy)).apply(instance, SnowPlacementModifier::new));
    private final boolean shouldBeSnowy;

    public SnowPlacementModifier(boolean shouldBeSnowy) {
        this.shouldBeSnowy = shouldBeSnowy;
    }

    @Override
    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {
        BlockState state = context.getWorld().getBlockState(pos);
        BlockState stateBelow = context.getWorld().getBlockState(pos.down());


        if (shouldBeSnowy && context.getWorld().getBiome(pos).value().canSetSnow(context.getWorld(), pos)) {
            if (stateBelow.contains(Properties.SNOWY)) {
                context.getWorld().setBlockState(pos.down(), stateBelow.with(Properties.SNOWY, true), 2);
            }
            return Stream.of(pos);
        }
        if (!shouldBeSnowy && !context.getWorld().getBiome(pos).value().canSetSnow(context.getWorld(), pos) && !context.getWorld().getBiome(pos).value().canSetIce(context.getWorld(), pos)) {
            return Stream.of(pos);
        }


        return Stream.empty();
    }

    @Override
    public PlacementModifierType<?> getType() {
        return SNOWY_BELOW;
    }

    public static SnowPlacementModifier of(boolean shouldBeSnowy) {
        return new SnowPlacementModifier(shouldBeSnowy);
    }
}
