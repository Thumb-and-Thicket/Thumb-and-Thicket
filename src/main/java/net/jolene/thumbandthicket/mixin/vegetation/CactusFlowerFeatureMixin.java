package net.jolene.thumbandthicket.mixin.vegetation;

import com.blackgear.vanillabackport.common.level.worldgen.features.CactusFlowerFeature;
import com.blackgear.vanillabackport.common.registries.blocks.ModBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CactusFlowerFeature.class)
public class CactusFlowerFeatureMixin {
    @WrapOperation(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean thumbandthicket$genWithStates(StructureWorldAccess instance, BlockPos pos, BlockState blockState, int i, Operation<Boolean> original, @Local(name = "random") Random random) {
        DataPool.Builder<BlockState> cactusFlowerBuilder = DataPool.builder();
        for (int j = 0; j <= 2; ++j) {
            cactusFlowerBuilder.add(ModBlocks.CACTUS_FLOWER.get().getDefaultState().with(Properties.AGE_2, j), 1);
        }
        BlockStateProvider provider = new WeightedBlockStateProvider(cactusFlowerBuilder);
        original.call(instance, pos, provider.get(random, pos), 1);
        return true;
    }
}
