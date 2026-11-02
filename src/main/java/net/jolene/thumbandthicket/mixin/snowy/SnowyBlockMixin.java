package net.jolene.thumbandthicket.mixin.snowy;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.util.render.Snowable;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowyBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SnowyBlock.class)
public class SnowyBlockMixin {

    @WrapMethod(method = "isSnow")
    private static boolean thumbandthicket$onlyWithLayers(BlockState state, Operation<Boolean> original) {
        if (state.getBlock() instanceof Snowable) {
            return state.get(ModProperties.LAYERS) > 0;
        } else return original.call(state);
    }
}
