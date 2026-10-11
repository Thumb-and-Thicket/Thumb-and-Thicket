package net.jolene.thumbandthicket.mixin.tree;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.jolene.thumbandthicket.block.LogBlock;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.util.Rooty;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.BiConsumer;

import static net.jolene.thumbandthicket.util.ModProperties.*;
import static net.jolene.thumbandthicket.ThumbAndThicket.*;

@Debug(export = true)
@Mixin(TrunkPlacer.class)
public class TrunkPlacerMixin {
    @WrapMethod(method = "setToDirt")
    private static void setToRoot(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, TreeFeatureConfig config, Operation<Void> original) {
        Block rootBlock = ModBlocks.ROOT_BLOCK;
        BlockState state = rootBlock.getDefaultState();

        replacer.accept(pos, state);
    }

    @WrapOperation(method = "getAndSetState(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;Ljava/util/function/Function;)Z", at = @At(value = "INVOKE", target = "Ljava/util/function/BiConsumer;accept(Ljava/lang/Object;Ljava/lang/Object;)V"))
    private void modifyPlacedState(BiConsumer instance, Object posObj, Object stateObj, Operation<Void> original, @Local(ordinal = 0, argsOnly = true) TestableWorld world) {
        BlockPos pos = (BlockPos) posObj;
        BlockState state = (BlockState) stateObj;
        BlockState newState = state;

        if (!((TrunkPlacer) (Object) this instanceof DarkOakTrunkPlacer)) {
            if (state.getBlock() instanceof LogBlock) {
                WorldAccess worldAccess = (WorldAccess) world;

                newState = thumbandthicket$determineRootSide(newState, worldAccess, pos);
                int random = Random.create().nextBetween(1,5);

                if (newState.get(ROOTY) != Rooty.NONE) newState = thumbandthicket$calculateSlice(newState, worldAccess, pos);
                else {
                    newState = newState.with(BRANCH, random == 1);
                    if (!newState.get(BRANCH)) newState = newState.with(HOLLOW, random == 5);
                }
                newState = thumbandthicket$inheritSlice(newState, worldAccess, pos);
            }
        }
        original.call(instance, pos, newState);
    }
}
