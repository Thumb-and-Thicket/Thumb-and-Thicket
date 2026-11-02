package net.jolene.thumbandthicket.mixin.snowy;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.util.render.Snowable;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;


@Mixin(PaneBlock.class)
public abstract class PaneMixin extends HorizontalConnectingBlock implements Snowable {

    @Unique
    private static final VoxelShape[] LAYERS_TO_SHAPE = new VoxelShape[]{VoxelShapes.empty(), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};

    protected PaneMixin(float radius1, float radius2, float boundingHeight1, float boundingHeight2, float collisionHeight, Settings settings) {
        super(radius1, radius2, boundingHeight1, boundingHeight2, collisionHeight, settings);
        this.stateManager.getDefaultState().with(ModProperties.LAYERS, 0);
    }


    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void gay(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(ModProperties.LAYERS);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = super.getOutlineShape(state, world, pos, context);
        if (state.get(ModProperties.LAYERS) > 0){
            return VoxelShapes.union(shape, LAYERS_TO_SHAPE[state.get(ModProperties.LAYERS)]);
        }
        return shape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = super.getOutlineShape(state, world, pos, context);
        if (state.get(ModProperties.LAYERS) > 1) {
            return VoxelShapes.union(shape, LAYERS_TO_SHAPE[state.get(ModProperties.LAYERS) -1]);
        }
        return shape;
    }

    @WrapMethod(method = "getCameraCollisionShape")
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, Operation<VoxelShape> original) {
        VoxelShape shape = original.call(state, world, pos, context);
        if (state.get(ModProperties.LAYERS) > 0){
            return VoxelShapes.union(shape, LAYERS_TO_SHAPE[state.get(ModProperties.LAYERS)]);
        }
        return shape;
    }

    @Override
    public ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.isOf(Items.SNOW) && state.get(ModProperties.LAYERS) < 8) {
            world.setBlockState(pos, state.cycle(ModProperties.LAYERS), Block.NOTIFY_LISTENERS);
            stack.decrementUnlessCreative(1, player);
            return ItemActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @WrapMethod(method = "getPlacementState")
    public BlockState getPlacementState(ItemPlacementContext ctx, Operation<BlockState> original) {
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        BlockState state = world.getBlockState(pos);

        if (state.isOf(Blocks.SNOW)) {
            int layers = state.get(Properties.LAYERS);
            return Objects.requireNonNull(original.call(ctx)).with(ModProperties.LAYERS, layers);
        }
        return original.call(ctx);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.shouldCancelInteraction() && context.getStack().isOf(Items.SNOW) && state.get(ModProperties.LAYERS) < 8 || super.canReplace(state, context);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (state.get(ModProperties.LAYERS) > 0) {
            world.setBlockState(pos, Blocks.SNOW.getDefaultState().with(Properties.LAYERS, state.get(ModProperties.LAYERS)), NOTIFY_ALL);
            tool.damage(1, player, EquipmentSlot.MAINHAND);
        }
        else if (tool.isIn(ItemTags.SHOVELS) && state.get(ModProperties.LAYERS) > 0) {
            world.setBlockState(pos, state.with(ModProperties.LAYERS, 0), NOTIFY_ALL);
        }
        super.afterBreak(world, player, pos, state, blockEntity, tool);
    }
}
