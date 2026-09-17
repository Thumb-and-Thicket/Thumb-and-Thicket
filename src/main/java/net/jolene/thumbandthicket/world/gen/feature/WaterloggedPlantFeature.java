package net.jolene.thumbandthicket.world.gen.feature;

import com.mojang.serialization.Codec;
import net.jolene.thumbandthicket.world.gen.feature.config.WaterloggedPlantFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import static net.minecraft.state.property.Properties.WATERLOGGED;

public class WaterloggedPlantFeature extends Feature<WaterloggedPlantFeatureConfig> {
    public WaterloggedPlantFeature(Codec<WaterloggedPlantFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<WaterloggedPlantFeatureConfig> context) {
        WaterloggedPlantFeatureConfig config = context.getConfig();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos origin = context.getOrigin();
        BlockState state = config.stateProvider().get(random, origin);

        int placed = 0;
        int tries = config.tries();
        int ySpread = config.ySpread();

        for (int i = 0; i < tries; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double radius = random.nextDouble();
            radius = radius * radius;

            int spread = config.xzSpread();
            int dx = (int) (Math.cos(angle) * radius * spread);
            int dz = (int) (Math.sin(angle) * radius * spread);

            BlockPos pos = origin.add(dx, 0, dz);
            pos = new BlockPos(pos.getX(), world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ()), pos.getZ());
            pos = pos.down();

            if (!world.getBlockState(pos).getFluidState().isOf(Fluids.WATER) && !world.getBlockState(pos.up()).isAir()) continue;

            boolean waterNearby = false;
            for (BlockPos currentPos : BlockPos.iterate(pos.add(ySpread, 0, ySpread), pos.add(-ySpread, -0, -ySpread))) if (BlockPredicate.matchingFluids(Fluids.WATER).test(world, currentPos)) waterNearby = true;
            if (!waterNearby) continue;
            if (!state.canPlaceAt(world, pos.mutableCopy())) continue;
            world.setBlockState(pos.mutableCopy(), state.with(WATERLOGGED, true),2);
            placed++;
        }

        return placed > 0;
    }
}
