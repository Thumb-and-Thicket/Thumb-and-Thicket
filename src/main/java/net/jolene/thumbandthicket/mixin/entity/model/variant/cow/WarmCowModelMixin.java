package net.jolene.thumbandthicket.mixin.entity.model.variant.cow;

import com.blackgear.vanillabackport.client.level.model.entity.cow.WarmCowModel;
import net.jolene.thumbandthicket.util.ModelUtil;
import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(WarmCowModel.class)
public class WarmCowModelMixin {

    /**
     * @author gayasslily
     * @reason change cow model
     */
    @Overwrite
    public static TexturedModelData createBodyLayer() {
        ModelData modelData = ModelUtil.createCowModel();
        ModelPartData root = modelData.getRoot();

        root.getChild("head").addChild("right_horn", ModelPartBuilder.create().mirrored().uv(0, 16).cuboid(-7.0F, -5.0F, -6.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .mirrored().uv(12, 0).cuboid(-7.0F, -7.0F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.NONE);
        root.getChild("head").addChild("left_horn", ModelPartBuilder.create().uv(0, 16).cuboid(4.0F, -5.0F, -6.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(12, 0).cuboid(5.0F, -7.0F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.NONE);

        return TexturedModelData.of(modelData, 96, 96);
    }
}
