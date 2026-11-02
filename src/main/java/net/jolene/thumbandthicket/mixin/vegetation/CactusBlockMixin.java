package net.jolene.thumbandthicket.mixin.vegetation;

import com.blackgear.vanillabackport.common.registries.blocks.ModBlocks;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.item.ModItems;
import net.jolene.thumbandthicket.mixin.BlockAccessor;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CactusBlock.class)
public class CactusBlockMixin {

    @WrapMethod(method = "onEntityCollision")
    private void thumbandthicket$dontDamageCacti(BlockState state, World world, BlockPos pos, Entity entity, Operation<Void> original) {
        if (entity instanceof ItemEntity item && (item.getStack().isOf(ModItems.PRICKLY_PEAR) || item.getStack().isOf(ModBlocks.CACTUS_FLOWER.get().asItem()) || item.getStack().isOf(Blocks.CACTUS.asItem()))) {
            return;
        }
        original.call(state, world, pos, entity);
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void thumbandthicket$appendSnipped(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(ModProperties.SNIPPED);
    }

    @WrapMethod(method = "randomTick")
    private void thumbandthicket$cancelGrowthIfSnipped(BlockState state, ServerWorld world, BlockPos pos, Random random, Operation<Void> original) {
        if (!state.get(ModProperties.SNIPPED)) original.call(state, world, pos, random);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void thumbandthicket$appendSnippedProperty(AbstractBlock.Settings settings, CallbackInfo ci) {
        Block cactusBlock = CactusBlock.class.cast(this);
        BlockState defaultBlockState = cactusBlock.getDefaultState();
        ((BlockAccessor) cactusBlock).invokeSetDefaultState(defaultBlockState.with(ModProperties.SNIPPED, false));
    }
}
