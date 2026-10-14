package net.jolene.thumbandthicket.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.function.BiFunction;

import static net.jolene.thumbandthicket.util.ModProperties.AMOUNT;
import static net.minecraft.state.property.Properties.BLOCK_FACE;
import static net.minecraft.state.property.Properties.FACING;

public class ClusterMushroomPlantBlock extends MushroomPlantBlock implements Fertilizable {

    public ClusterMushroomPlantBlock(RegistryKey<ConfiguredFeature<?, ?>> featureKey, Settings settings) {
        super(featureKey, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(BLOCK_FACE, BlockFace.FLOOR).with(AMOUNT, 1));
    }

    private static final BiFunction<BlockState, Integer, VoxelShape> FACING_AND_AMOUNT_TO_SHAPE = Util.memoize((state, amount) -> {
        VoxelShape[] voxelShapesFloor = new VoxelShape[]{Block.createCuboidShape(8.0, 0.0, 8.0, 16.0, 9.0, 16.0), Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 9.0, 8.0), Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 9.0, 8.0), Block.createCuboidShape(0.0, 0.0, 8.0, 8.0, 9.0, 16.0)};
        VoxelShape[] voxelShapesWallSouth = new VoxelShape[]{Block.createCuboidShape(8, 8, 0, 16, 16, 3), Block.createCuboidShape(8, 0, 0, 16, 8, 3), Block.createCuboidShape(0, 0, 0, 8, 8, 3), Block.createCuboidShape(0, 8, 0, 8, 16, 3)};
        VoxelShape[] voxelShapesWallWest = new VoxelShape[]{Block.createCuboidShape(13, 8, 8, 16, 16, 16), Block.createCuboidShape(13, 0, 8, 16, 8, 16), Block.createCuboidShape(13, 0, 0, 16, 8, 8), Block.createCuboidShape(13, 8, 0, 16, 16, 8)};
        VoxelShape[] voxelShapesWallNorth = new VoxelShape[]{Block.createCuboidShape(8, 8, 13, 16, 16, 16), Block.createCuboidShape(8, 0, 13, 16, 8, 16), Block.createCuboidShape(0, 0, 13, 8, 8, 16), Block.createCuboidShape(0, 8, 13, 8, 16, 16)};
        VoxelShape[] voxelShapesWallEast = new VoxelShape[]{Block.createCuboidShape(0, 8, 8, 3, 16, 16), Block.createCuboidShape(0, 0, 8, 3, 8, 16), Block.createCuboidShape(0, 0, 0, 3, 8, 8), Block.createCuboidShape(0, 8, 0, 3, 16, 8)};
        VoxelShape[] voxelShapesCeiling = new VoxelShape[]{Block.createCuboidShape(8, 13, 8, 16, 16, 16), Block.createCuboidShape(8, 13, 0, 16, 16, 8), Block.createCuboidShape(0, 13, 0, 8, 16, 8), Block.createCuboidShape(0, 13, 8, 8, 16, 16)};
        VoxelShape voxelShape = VoxelShapes.empty();
        VoxelShape[] voxelShapes = new VoxelShape[]{voxelShape};
        Direction facing = state.get(FACING);
            switch (state.get(BLOCK_FACE)) {
                case FLOOR -> voxelShapes = voxelShapesFloor;
                case WALL -> {
                    switch (facing) {
                        case NORTH -> voxelShapes = voxelShapesWallNorth;
                        case EAST -> voxelShapes = voxelShapesWallEast;
                        case SOUTH -> voxelShapes = voxelShapesWallSouth;
                        case WEST -> voxelShapes = voxelShapesWallWest;
                    }
                }
                case CEILING -> voxelShapes = voxelShapesCeiling;
            }
            for (int i = 0; i < amount; ++i) {
                int j = Math.floorMod(i - facing.getHorizontal(), 4);
                voxelShape = VoxelShapes.union(voxelShape, voxelShapes[j]);
            }
        return voxelShape;
    });

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return FACING_AND_AMOUNT_TO_SHAPE.apply(state, state.get(AMOUNT));
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {

        Direction direction = thumbandthicket$getDirection(state).getOpposite();
        BlockPos blockPos = pos.offset(direction);
        BlockPos pos1 = pos.down();
        if (direction == Direction.DOWN) return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction.getOpposite()) && world.getBlockState(pos1).isIn(BlockTags.MUSHROOM_GROW_BLOCK);
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction.getOpposite());
    }

    private static Direction thumbandthicket$getDirection(BlockState state) {
        return switch (state.get(BLOCK_FACE)) {
            case CEILING -> Direction.DOWN;
            case FLOOR -> Direction.UP;
            default -> state.get(FACING);
        };
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(AMOUNT) < 4) return true;
        return super.canReplace(state, context);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Block block = this;
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.with(AMOUNT, Math.min(4, blockState.get(AMOUNT) + 1));
        }
        for (Direction direction : ctx.getPlacementDirections()) {
            BlockState blockState1 = direction.getAxis() == Direction.Axis.Y ? block.getDefaultState().with(BLOCK_FACE, direction == Direction.UP ? BlockFace.CEILING : BlockFace.FLOOR).with(FACING, ctx.getHorizontalPlayerFacing()) : block.getDefaultState().with(BLOCK_FACE, BlockFace.WALL).with(FACING, direction.getOpposite());
            if (!blockState1.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) continue;
            return blockState1;
        }
        return null;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (thumbandthicket$getDirection(state).getOpposite() == direction && !canPlaceAt(state, world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BLOCK_FACE).add(FACING).add(AMOUNT);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.get(BLOCK_FACE) == BlockFace.FLOOR) trySpawningBigMushroom(world, pos, state, random);
    }
}
