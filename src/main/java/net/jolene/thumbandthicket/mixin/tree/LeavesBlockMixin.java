package net.jolene.thumbandthicket.mixin.tree;

import net.jolene.thumbandthicket.mixin.BlockAccessor;
import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin extends Block {

    @Unique
    private static final BooleanProperty NORTH = ConnectingBlock.NORTH;
    @Unique
    private static final BooleanProperty EAST = ConnectingBlock.EAST;
    @Unique
    private static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
    @Unique
    private static final BooleanProperty WEST = ConnectingBlock.WEST;
    @Unique
    private static final BooleanProperty UP = ConnectingBlock.UP;
    @Unique
    private static final BooleanProperty DOWN = ConnectingBlock.DOWN;
    @Unique
    private static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES;

    public LeavesBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "getStateForNeighborUpdate", at = @At("RETURN"), cancellable = true)
    private void thumbandthicket$checkIfTop(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        BlockState state1 = world.getBlockState(pos.offset(direction));
        cir.setReturnValue(state.with(FACING_PROPERTIES.get(direction), state1.getBlock() instanceof LeavesBlock));
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void thumbandthicket$addTop(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void thumbandthicket$appendProperty(AbstractBlock.Settings settings, CallbackInfo ci) {
        Block leavesBlock = LeavesBlock.class.cast(this);
        BlockState defaultBlockState = leavesBlock.getDefaultState();
        ((BlockAccessor) leavesBlock).invokeSetDefaultState(defaultBlockState.with(UP, false).with(DOWN, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
    }
}
