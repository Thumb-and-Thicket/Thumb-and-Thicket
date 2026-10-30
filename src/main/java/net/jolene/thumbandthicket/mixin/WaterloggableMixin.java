package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Waterloggable.class)
public interface WaterloggableMixin {

    @WrapMethod(method = "tryFillWithFluid")
    private boolean thumbandthicket$onlyFillIfAdjacentWaterEight(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState, Operation<Boolean> original) {
        if (!fluidState.isEqualAndStill(fluidState.getFluid())) return false;
        return original.call(world, pos, state, fluidState);
    }
}
