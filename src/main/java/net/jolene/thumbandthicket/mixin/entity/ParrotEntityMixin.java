package net.jolene.thumbandthicket.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ParrotEntity.class)
public abstract class ParrotEntityMixin extends AnimalEntity {
    protected ParrotEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

//    @Inject(method = "initGoals", at = @At("TAIL"))
//    private void thumbandthicket$addNestGoal(CallbackInfo ci) {
//        super.initGoals();
//    }
}
