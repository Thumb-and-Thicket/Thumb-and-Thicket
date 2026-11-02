package net.jolene.thumbandthicket.block;

import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.util.render.Snowable;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TallSnowyPlant extends TallPlantBlock implements Snowable {
    public TallSnowyPlant(Settings settings) {
        super(settings);
        super.setDefaultState(this.getDefaultState().with(ModProperties.LAYERS, 0));
    }

    protected static final VoxelShape[] LAYERS_TO_SHAPE = new VoxelShape[]{VoxelShapes.empty(), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = super.getOutlineShape(state, world, pos, context);
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (state.get(ModProperties.LAYERS) > 0 && doubleBlockHalf == DoubleBlockHalf.LOWER){
            return VoxelShapes.union(shape, LAYERS_TO_SHAPE[state.get(ModProperties.LAYERS)]);
        }
        return super.getOutlineShape(state, world, pos, context);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (state.get(ModProperties.LAYERS) > 1 && doubleBlockHalf == DoubleBlockHalf.LOWER) {
            return LAYERS_TO_SHAPE[state.get(ModProperties.LAYERS) - 1];
        }
        return VoxelShapes.empty();
    }

    @Override
    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (state.get(ModProperties.LAYERS) > 1 && doubleBlockHalf == DoubleBlockHalf.LOWER) {
            return LAYERS_TO_SHAPE[state.get(ModProperties.LAYERS)];
        }
        return VoxelShapes.empty();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ModProperties.LAYERS);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (stack.isOf(Items.SNOW) && state.get(ModProperties.LAYERS) < 8 && doubleBlockHalf == DoubleBlockHalf.LOWER) {
            world.setBlockState(pos, state.cycle(ModProperties.LAYERS), Block.NOTIFY_LISTENERS);
            stack.decrementUnlessCreative(1, player);
            return ItemActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        if (floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.SNOW_BLOCK)) return true;
        return super.canPlantOnTop(floor, world, pos);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.shouldCancelInteraction() && context.getStack().isOf(Items.SNOW) && state.get(ModProperties.LAYERS) < 8 && state.get(HALF) != DoubleBlockHalf.UPPER || (super.canReplace(state, context) && state.get(ModProperties.LAYERS) == 0 && !context.getStack().isOf(Items.SNOW));
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        BlockState state = world.getBlockState(pos);

        if (state.isOf(Blocks.SNOW) && pos.getY() < world.getTopY() - 1 && world.getBlockState(pos.up()).canReplace(ctx)) {
            int layers = state.get(Properties.LAYERS);
            return this.getDefaultState().with(ModProperties.LAYERS, layers);
        }
        return super.getPlacementState(ctx);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (tool.isOf(Items.SHEARS) && state.get(ModProperties.LAYERS) > 0 && state.get(ModProperties.LAYERS) < 8) {
            if (doubleBlockHalf == DoubleBlockHalf.LOWER) {
                world.setBlockState(pos, Blocks.SNOW.getDefaultState().with(Properties.LAYERS, state.get(ModProperties.LAYERS)), NOTIFY_ALL);
            } else {
                world.setBlockState(pos.down(), Blocks.SNOW.getDefaultState().with(Properties.LAYERS, state.get(ModProperties.LAYERS)), NOTIFY_ALL);
            }
//            tool.damage(1, player, EquipmentSlot.MAINHAND);
        }
        else if (tool.isIn(ItemTags.SHOVELS) && state.get(ModProperties.LAYERS) > 0) {
            world.setBlockState(pos, state.with(ModProperties.LAYERS, 0), NOTIFY_ALL);
        }
        super.afterBreak(world, player, pos, state, blockEntity, tool);
    }

    @Override
    protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (state.get(ModProperties.LAYERS) > 0 && doubleBlockHalf == DoubleBlockHalf.LOWER) {
            return state.get(ModProperties.LAYERS) == 8 ? 0.2f : 1.0f;
        }
        return 1;
    }

    @Override
    protected float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        if (state.get(ModProperties.LAYERS) == 0 || player.getMainHandStack().getItem() instanceof ShearsItem) {
            return 1.0f;
        }
        return super.calcBlockBreakingDelta(state, player, world, pos);
    }


    @Override
    protected BlockSoundGroup getSoundGroup(BlockState state) {
        if (state.get(ModProperties.LAYERS) > 0) {
            return BlockSoundGroup.SNOW;
        }
        return super.getSoundGroup(state);
    }
}
