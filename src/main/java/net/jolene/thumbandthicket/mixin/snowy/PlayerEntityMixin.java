package net.jolene.thumbandthicket.mixin.snowy;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.util.render.Snowable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow
    @Final
    PlayerInventory inventory;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapMethod(method = "canHarvest")
    private boolean thumbandthicket$harvestSnowableWithShovel(BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof Snowable && this.inventory.getMainHandStack().isIn(ItemTags.SHOVELS) && state.get(ModProperties.LAYERS) > 0 || original.call(state);
    }

    @WrapOperation(method = "getBlockBreakingSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getBlockBreakingSpeed(Lnet/minecraft/block/BlockState;)F"))
    private float thumbandthicket$increaseSnowableBreakSpeed(PlayerInventory instance, BlockState block, Operation<Float> original) {
        if (block.getBlock() instanceof Snowable && block.get(ModProperties.LAYERS) > 0) {
            ItemStack heldItem = this.inventory.getMainHandStack();
            if (heldItem.isIn(ItemTags.SHOVELS)) return original.call(instance, Blocks.SNOW.getDefaultState());
        }
        return original.call(instance, block);
    }
}
