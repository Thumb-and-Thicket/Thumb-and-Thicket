package net.jolene.thumbandthicket.mixin.vegetation;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.mixin.BlockAccessor;
import net.jolene.thumbandthicket.util.Soakable;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.jolene.thumbandthicket.util.ModProperties.SNIPPED;

@Mixin(SugarCaneBlock.class)
public class SugarCaneBlockMixin {

    @WrapMethod(method = "randomTick")
    private void thumbandthicket$cancelWhenSnipped(BlockState state, ServerWorld world, BlockPos pos, Random random, Operation<Void> original) {
        if (!state.get(SNIPPED)) original.call(state, world, pos, random);
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void thumbandthicket$addSnipped(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(SNIPPED);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void thumbandthicket$appendSnippedProperty(AbstractBlock.Settings settings, CallbackInfo ci) {
        Block sugarCaneBlock = SugarCaneBlock.class.cast(this);
        BlockState defaultBlockState = sugarCaneBlock.getDefaultState();
        ((BlockAccessor) sugarCaneBlock).invokeSetDefaultState(defaultBlockState.with(SNIPPED, false));
    }

    @WrapMethod(method = "canPlaceAt")
    private boolean thumbandthicket$preferWetSand(BlockState state, WorldView world, BlockPos pos, Operation<Boolean> original) {
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.getBlock() instanceof Soakable soakable) return soakable.getSoakingLevel() == Soakable.WetnessLevel.WET;
        return blockState.isOf(Blocks.SUGAR_CANE) || blockState.isOf(Blocks.MUD);
    }
}
