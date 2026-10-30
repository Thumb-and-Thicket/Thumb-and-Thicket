package net.jolene.thumbandthicket.mixin.entity.model.variant.chicken;

import com.blackgear.vanillabackport.client.level.model.entity.chicken.ColdChickenModel;
import net.jolene.thumbandthicket.util.ModelUtil;
import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ColdChickenModel.class)
public class ColdChickenModelMixin {
    /**
     * @author gayasslily
     * @reason change chicken model
     */
    @Overwrite
    public static TexturedModelData createBodyLayer() {
        ModelData modelData = ModelUtil.createChickenModel();

        return TexturedModelData.of(modelData, 64, 64);
    }
}
