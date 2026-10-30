package net.jolene.thumbandthicket.mixin.compat;

import com.blackgear.vanillabackport.client.api.bundled_tabs.BundledTabs;
import com.blackgear.vanillabackport.client.registries.ModBundledTabs;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jolene.thumbandthicket.item.ModItems;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(ModBundledTabs.class)
public class ModBundledTabsMixin {

    @WrapOperation(method = "lambda$static$2", at = @At(value = "INVOKE", target = "Lcom/blackgear/vanillabackport/client/api/bundled_tabs/BundledTabs$Output;accept(Lnet/minecraft/item/ItemConvertible;)V", ordinal = 3))
    private static void thumbandthicket$addLeafLitter(BundledTabs.Output instance, ItemConvertible itemConvertible, Operation<Void> original) {
        original.call(instance, ModItems.LEAF_LITTER);
    }
}
