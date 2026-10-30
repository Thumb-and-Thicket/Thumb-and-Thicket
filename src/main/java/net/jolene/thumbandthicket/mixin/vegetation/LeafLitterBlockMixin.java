package net.jolene.thumbandthicket.mixin.vegetation;

import com.blackgear.vanillabackport.common.level.blocks.LeafLitterBlock;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.item.ModItems;
import net.jolene.thumbandthicket.mixin.BlockAccessor;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiFunction;

import static com.blackgear.vanillabackport.common.level.blocks.LeafLitterBlock.AMOUNT;
import static com.blackgear.vanillabackport.common.level.blocks.LeafLitterBlock.FACING;
import static net.minecraft.state.property.Properties.WATERLOGGED;

@Mixin(LeafLitterBlock.class)
public abstract class LeafLitterBlockMixin extends PlantBlock implements Waterloggable {

    @Unique
    private static final BiFunction<Direction, Integer, VoxelShape> SHAPE_BY_PROPERTIES;

    protected LeafLitterBlockMixin(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
    }

    @Shadow
    public abstract boolean canReplace(BlockState state, ItemPlacementContext useContext);

    @WrapMethod(method = "canPlaceAt")
    private boolean thumbandthicket$canPlaceAt(BlockState state, WorldView world, BlockPos pos, Operation<Boolean> original) {
        BlockPos below = pos.down();
        FluidState fluid = world.getFluidState(pos);
        FluidState fluid1 = world.getFluidState(pos.up());
        return ((fluid.isIn(FluidTags.WATER) && fluid.getLevel() == 8) || (world.getBlockState(below).isSideSolidFullSquare(world, below, Direction.UP))) && fluid1.isEmpty();
    }

    @Inject(method = "appendProperties", at = @At("RETURN"))
    private void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(WATERLOGGED);
    }

    /**
     * @author gayasslily
     * @reason waterlogging
     */
    @Overwrite
    public @Nullable BlockState getPlacementState(ItemPlacementContext context) {
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        return state.isOf(this) ? state.with(AMOUNT, Math.min(4, state.get(AMOUNT) + 1)) : this.getDefaultState().with(FACING, context.getHorizontalPlayerFacing().getOpposite()).with(WATERLOGGED, context.getWorld().getFluidState(context.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @WrapMethod(method = "getOutlineShape")
    private VoxelShape thumbandthicket$changeOutlineShape(BlockState state, BlockView level, BlockPos pos, ShapeContext context, Operation<VoxelShape> original) {
        VoxelShape shape = original.call(state, level, pos, context);
        if (state.get(WATERLOGGED)) shape = SHAPE_BY_PROPERTIES.apply(state.get(FACING), state.get(AMOUNT));
        return shape;
    }

    static {
        SHAPE_BY_PROPERTIES = Util.memoize((direction, value) -> {
            VoxelShape shape = VoxelShapes.empty();
            VoxelShape[] shapes = new VoxelShape[]{Block.createCuboidShape(8.0F, 14.0F, 8.0F, 16.0F, 16.0F, 16.0F), Block.createCuboidShape(8.0F, 14.0F, 0.0F, 16.0F, 16.0F, 8.0F), Block.createCuboidShape(0.0F, 14.0F, 0.0F, 8.0F, 16.0F, 8.0F), Block.createCuboidShape(0.0F, 14.0F, 8.0F, 8.0F, 16.0F, 16.0F)};

            for(int index = 0; index < value; ++index) {
                int i = Math.floorMod(index - direction.getHorizontal(), 4);
                shape = VoxelShapes.union(shape, shapes[i]);
            }

            return shape.asCuboid();
        });
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return ModItems.LEAF_LITTER.getDefaultStack();
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void thumbandthicket$appendSnippedProperty(AbstractBlock.Settings settings, CallbackInfo ci) {
        Block leafLitterBlock = LeafLitterBlock.class.cast(this);
        BlockState defaultBlockState = leafLitterBlock.getDefaultState();
        ((BlockAccessor) leafLitterBlock).invokeSetDefaultState(defaultBlockState.with(WATERLOGGED, false));
    }
}
