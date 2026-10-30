package net.jolene.thumbandthicket.mixin.compat;

import com.blackgear.platform.common.CreativeTabs;
import com.blackgear.vanillabackport.client.integrations.VanillaTabIntegrations;
import com.blackgear.vanillabackport.common.registries.blocks.ModBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jolene.thumbandthicket.item.ModItems;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;
import java.util.List;

@Mixin(VanillaTabIntegrations.class)
public class CreativeTabIntegrationMixin {

//    @WrapOperation(method = "lambda$registerSpringToLife$7", at = @At(value = "INVOKE", target = "Lcom/blackgear/platform/common/CreativeTabs$Output;addAllAfter(Lnet/minecraft/item/ItemConvertible;Ljava/util/Collection;)V", ordinal = 3))
//    private static void thumbandthicket$addLeafLitter(CreativeTabs.Output instance, ItemConvertible target, Collection<ItemConvertible> stacks, Operation<Void> original) {
//        Collection<ItemConvertible> items = List.of(ModBlocks.WILDFLOWERS.get(), ModItems.LEAF_LITTER);
//        original.call(instance, target, items);
//    }
}
