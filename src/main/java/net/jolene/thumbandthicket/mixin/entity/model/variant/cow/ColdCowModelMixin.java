package net.jolene.thumbandthicket.mixin.entity.model.variant.cow;

import com.blackgear.vanillabackport.client.level.model.entity.cow.ColdCowModel;
import net.jolene.thumbandthicket.util.ModelUtil;
import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ColdCowModel.class)
public class ColdCowModelMixin {
    /**
     * @author gayasslily
     * @reason change cow model
     */
    @Overwrite
    public static TexturedModelData createBodyLayer() {
        ModelData modelData = ModelUtil.createCowModel();
        ModelPartData root = modelData.getRoot();

        root.getChild("head").addChild("right_horn", ModelPartBuilder.create().uv(0, 0).cuboid(4.0F, -5.0F, -9.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.NONE);
        root.getChild("head").addChild("left_horn", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -5.0F, -9.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.NONE);

        return TexturedModelData.of(modelData, 96, 96);
    }
}
