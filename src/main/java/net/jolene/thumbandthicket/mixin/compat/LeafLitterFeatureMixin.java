package net.jolene.thumbandthicket.mixin.compat;

import com.blackgear.vanillabackport.common.level.worldgen.features.LeafLitterFeature;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LeafLitterFeature.class)
public class LeafLitterFeatureMixin {

    @WrapOperation(method = "findTreeBase", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean gay(BlockState instance, TagKey tagKey, Operation<Boolean> original) {
        return instance.isOf(ModBlocks.ROOT_BLOCK);
    }
}
