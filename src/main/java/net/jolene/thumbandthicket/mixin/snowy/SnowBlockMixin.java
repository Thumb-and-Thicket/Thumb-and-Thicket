package net.jolene.thumbandthicket.mixin.snowy;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.util.render.Snowable;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SnowBlock.class)
public class SnowBlockMixin {
    @WrapMethod(method = "canReplace")
    private boolean thumbandthicket$canBeReplacedBySnowyPlants(BlockState state, ItemPlacementContext context, Operation<Boolean> original) {
        if (context.getStack().getItem() instanceof BlockItem blockItem) if (blockItem.getBlock() instanceof Snowable) return true;
        return original.call(state, context);
    }

    @WrapMethod(method = "getPlacementState")
    public BlockState getPlacementState(ItemPlacementContext ctx, Operation<BlockState> original) {
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        BlockState state = world.getBlockState(pos);

        return state.getBlock() instanceof Snowable ? state.with(ModProperties.LAYERS, Math.min(8, state.get(ModProperties.LAYERS) + 1)) : original.call(ctx);
    }

    @WrapMethod(method = "canPlaceAt")
    private boolean thumbandthicket$canPlaceOnSnowable(BlockState state, WorldView world, BlockPos pos, Operation<Boolean> original) {
        BlockState state1 = world.getBlockState(pos.down());
        if (state1.getBlock() instanceof Snowable && state1.get(ModProperties.LAYERS) == 8) return true;
        return original.call(state, world, pos);
    }
}
