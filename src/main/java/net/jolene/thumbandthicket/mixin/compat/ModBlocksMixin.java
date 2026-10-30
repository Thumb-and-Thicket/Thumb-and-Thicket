package net.jolene.thumbandthicket.mixin.compat;

import com.blackgear.platform.core.helper.BlockRegistry;
import com.blackgear.vanillabackport.common.level.blocks.LeafLitterBlock;
import com.blackgear.vanillabackport.common.registries.blocks.ModBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;
import java.util.function.Supplier;

@Mixin(ModBlocks.class)
public class ModBlocksMixin {

    @WrapOperation(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/blackgear/platform/core/helper/BlockRegistry;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/block/AbstractBlock$Settings;)Ljava/util/function/Supplier;", ordinal = 22))
    private static Supplier<Block> thumbandthicket$registerNoItem(BlockRegistry instance, String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings properties, Operation<Supplier<Block>> original) {
        return BlockRegistry.create("minecraft").registerNoItem("leaf_litter", LeafLitterBlock::new, properties);
    }
}
