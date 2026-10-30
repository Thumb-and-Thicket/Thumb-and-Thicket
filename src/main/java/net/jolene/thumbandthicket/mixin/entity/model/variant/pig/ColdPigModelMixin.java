package net.jolene.thumbandthicket.mixin.entity.model.variant.pig;

import com.blackgear.vanillabackport.client.level.model.entity.pig.ColdPigModel;
import net.jolene.thumbandthicket.util.ModelUtil;
import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ColdPigModel.class)
public class ColdPigModelMixin {
    /**
     * @author gayasslily
     * @reason change cow model
     */
    @Overwrite
    public static TexturedModelData createBodyLayer() {
        ModelData modelData = ModelUtil.createPigModel();

        return TexturedModelData.of(modelData, 128, 128);
    }
}
