package net.jolene.thumbandthicket.mixin.entity.model;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.jolene.thumbandthicket.util.render.CoatTwoUtil;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.SheepEntityRenderer;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SheepEntityRenderer.class)
public abstract class SheepEntityRendererMixin extends MobEntityRenderer<SheepEntity, SheepEntityModel<SheepEntity>> {

    public SheepEntityRendererMixin(EntityRendererFactory.Context context, SheepEntityModel<SheepEntity> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Override
    protected boolean isShaking(SheepEntity entity) {
        CoatTwoUtil secondLayerSheep = (CoatTwoUtil) entity;
        BlockPos pos = entity.getBlockPos();
        World world = entity.getWorld();
        return super.isShaking(entity) || (world.getBiome(pos).isIn(ConventionalBiomeTags.IS_COLD) && (entity.isSheared() || !secondLayerSheep.thumbandthicket$hasCoatTwo()));
    }
}
