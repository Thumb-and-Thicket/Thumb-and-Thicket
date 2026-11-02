package net.jolene.thumbandthicket.mixin.entity;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.jolene.thumbandthicket.util.render.CoatTwoUtil;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends MobEntity implements CoatTwoUtil {

    @Shadow
    public abstract boolean isShearable();

    @Unique
    private static final TrackedData<Boolean> SECOND_COAT = DataTracker.registerData(SheepEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    protected SheepEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean thumbandthicket$hasCoatTwo() {
        return this.dataTracker.get(SECOND_COAT);
    }

    @Unique
    private void setSecondCoat(boolean bool) {
        this.dataTracker.set(SECOND_COAT, bool);
    }

    @Unique
    private boolean getSecondCoat() {
        return this.dataTracker.get(SECOND_COAT);
    }

    @Inject(method = "onEatingGrass", at = @At("HEAD"))
    public void onEatingGrass(CallbackInfo ci) {
        SheepEntity sheepEntity = (SheepEntity)(Object)(this);
        if (!sheepEntity.isSheared() && canHaveSecondCoat(sheepEntity)){
            setSecondCoat(true);
        }
    }

    @Unique
    private boolean canHaveSecondCoat(SheepEntity sheepEntity) {
        World world = sheepEntity.getWorld();
        BlockPos pos = sheepEntity.getBlockPos();
        return world.getBiome(pos).isIn(ConventionalBiomeTags.IS_COLD);
    }

    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;isShearable()Z", shift = At.Shift.AFTER))
    private void thumbandthicket$setCoatFalse(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (getSecondCoat() && isShearable()) setSecondCoat(false);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void thumbandthicket$writeCoatTwo(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("SecondCoat", getSecondCoat());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void thumbandthicket$readCoatTwo(NbtCompound nbt, CallbackInfo ci) {
        setSecondCoat(nbt.getBoolean("SecondCoat"));
    }

    @Inject(method = "initialize", at = @At("HEAD"))
    private void thumbandthicket$setCoatTwo(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, CallbackInfoReturnable<EntityData> cir) {
        SheepEntity sheepEntity = (SheepEntity)(Object)(this);
        if (canHaveSecondCoat(sheepEntity)) {
            setSecondCoat(true);
        }
    }

    @ModifyVariable(method = "sheared(Lnet/minecraft/sound/SoundCategory;)V", at = @At(value = "STORE"), ordinal = 0)
    private int thumbandthicket$modifyWoolDropCount(int originalAmount) {
        if (getSecondCoat() && isShearable()) return 2 + Random.create().nextBetween(1,3);
        return Random.create().nextBetween(1,3);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private static void thumbandthicket$initSecondCoat(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(SECOND_COAT, false);
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    private void thumbandthicket$addNestGoal(CallbackInfo ci) {
        super.initGoals();
    }
}
