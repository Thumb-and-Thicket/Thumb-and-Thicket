package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.util.render.Snowable;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jolene.thumbandthicket.util.ModProperties.*;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Shadow private BlockState defaultState;

    @Shadow
    public abstract BlockState getDefaultState();

    @Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
    private void thumbandthicket$modifyPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        Block block = (Block)(Object)this;
        BlockState state = cir.getReturnValue();
        if (block instanceof FarmlandBlock) cir.setReturnValue(state.with(FERTILIZED, false));
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void thumbandthicket$appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci){
        Block block = (Block) (Object) this;
    }

    @WrapMethod(method = "getDefaultState")
    private BlockState thumbandthicket$changeDefaultState(Operation<BlockState> original) {
        Block block = (Block)(Object)this;
        if (block instanceof FarmlandBlock) return original.call().with(FERTILIZED, false);
        return original.call();
    }

    @WrapMethod(method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V")
    private static void thumbandthicket$dropFlowers(BlockState state, World world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool, Operation<Void> original) {
        if (world instanceof ServerWorld) {
            if (state.contains(FLOWERS)) {
                for (int i = 0; i < state.get(FLOWERS); i++) {
                    Block.getDroppedStacks(state, (ServerWorld) world, pos, blockEntity, entity, tool).forEach(stack -> Block.dropStack(world, pos, stack));
                    state.onStacksDropped((ServerWorld) world, pos, tool, false);
                }
                return;
            }
            if (state.getBlock() instanceof Snowable && state.get(LAYERS) > 0 && tool.isIn(ItemTags.SHOVELS)) {
                Block.getDroppedStacks(Blocks.SNOW.getDefaultState().with(Properties.LAYERS, state.get(LAYERS)), (ServerWorld) world, pos, blockEntity, entity, tool).forEach(stack -> Block.dropStack(world, pos, stack));
                state.onStacksDropped((ServerWorld) world, pos, tool, false);
                return;
            }
        }
        original.call(state, world, pos, blockEntity, entity, tool);
    }
}
