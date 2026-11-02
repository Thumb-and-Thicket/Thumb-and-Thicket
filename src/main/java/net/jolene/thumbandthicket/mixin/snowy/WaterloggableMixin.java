package net.jolene.thumbandthicket.mixin.snowy;

import com.blackgear.vanillabackport.common.level.blocks.LeafLitterBlock;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.util.render.Snowable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Waterloggable.class)
public interface WaterloggableMixin {

    @WrapMethod(method = "tryFillWithFluid")
    private boolean thumbandthicket$fillIfNotSnowy(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState, Operation<Boolean> original) {
        if (state.getBlock() instanceof LeafLitterBlock) return false;
        return original.call(world, pos, state, fluidState);
    }

    @WrapOperation(method = "tryFillWithFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean thumbandthicket$removeSnowOnWaterlog(WorldAccess instance, BlockPos pos, BlockState state, int i, Operation<Boolean> original) {
        if (state.getBlock() instanceof Snowable && state.get(ModProperties.LAYERS) > 0) return original.call(instance, pos, state.with(ModProperties.LAYERS, 0), i);
        return original.call(instance, pos, state, i);
    }
}
