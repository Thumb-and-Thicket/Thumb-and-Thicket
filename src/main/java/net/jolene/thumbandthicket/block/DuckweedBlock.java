package net.jolene.thumbandthicket.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.state.property.Properties.WATERLOGGED;

public class DuckweedBlock extends PlantBlock implements Waterloggable {
    public static final MapCodec<DuckweedBlock> CODEC = createCodec(DuckweedBlock::new);

    public static final BooleanProperty INNER = BooleanProperty.of("inner");

    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);

    public DuckweedBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(INNER, false));
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.up());

        return (fluidState.getFluid() == Fluids.WATER || floor.getBlock() instanceof IceBlock) && fluidState2.getFluid() == Fluids.EMPTY;
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        FluidState fluid = world.getFluidState(pos);
        FluidState fluid1 = world.getFluidState(pos.up());

        return fluid.isIn(FluidTags.WATER) && fluid.getLevel() == 8 && fluid1.isEmpty();
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());

        if (fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8) {
            return this.getDefaultState().with(WATERLOGGED, true).with(INNER, isSurroundedByDuckweed(ctx.getWorld(), ctx.getBlockPos()));
        }
        return null;
    }

    private boolean isSurroundedByDuckweed(BlockView world, BlockPos pos) {
        return world.getBlockState(pos.north()).getBlock() == this && world.getBlockState(pos.south()).getBlock() == this && world.getBlockState(pos.east()).getBlock() == this && world.getBlockState(pos.west()).getBlock() == this;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState newState = state;
        if (direction.getAxis().isHorizontal()) {
            boolean inner = isSurroundedByDuckweed(world, pos);
            if (state.get(INNER) != inner) newState = newState.with(INNER, inner);
        }

        return super.getStateForNeighborUpdate(newState, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, INNER);
        super.appendProperties(builder);
    }
}