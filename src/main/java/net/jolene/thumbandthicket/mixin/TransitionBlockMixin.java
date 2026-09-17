package net.jolene.thumbandthicket.mixin;

import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class TransitionBlockMixin {

    // PLACEMENT TRANSITIONS

    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void thumbandthicket$updatePlacedTransitionStates(ItemPlacementContext context, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = cir.getReturnValue();

        if (state == null) return;
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        // DAMP SAND

        if (state.isOf(Blocks.SAND) && state.contains(ModProperties.DAMP)) {
            boolean damp = false;

            for (Direction direction : Direction.Type.HORIZONTAL) {
                if (world.getBlockState(pos.offset(direction)).isOf(ModBlocks.WET_SAND)) {
                    damp = true;
                    break;
                }
            }
            state = state.with(ModProperties.DAMP, damp);
        }

        // STONY DIRT

        if (state.isOf(Blocks.DIRT) && state.contains(ModProperties.STONY)) {

            boolean touchesStone = false;
            for (Direction direction : Direction.values()) {

                if (world.getBlockState(pos.offset(direction)).isOf(Blocks.STONE)) {
                    touchesStone = true;
                    break;
                }
            }
            boolean stony = touchesStone && world.getRandom().nextBoolean();
            state = state.with(ModProperties.STONY, stony);
        }

        cir.setReturnValue(state);
    }
}