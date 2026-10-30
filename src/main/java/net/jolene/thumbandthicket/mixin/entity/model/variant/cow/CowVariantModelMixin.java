package net.jolene.thumbandthicket.mixin.entity.model.variant.cow;

import com.blackgear.vanillabackport.client.level.model.entity.cow.CowVariantModel;
import net.jolene.thumbandthicket.util.ModelUtil;
import net.minecraft.client.model.ModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CowVariantModel.class)
public class CowVariantModelMixin {

    /**
     * @author gayasslily
     * @reason change cow model
     */
    @Overwrite
    static ModelData createBaseCowModel() {
        return ModelUtil.createCowModel();
    }
}
