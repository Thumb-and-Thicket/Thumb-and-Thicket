package net.jolene.thumbandthicket.mixin.tree;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jolene.thumbandthicket.block.LogBlock;
import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.util.Rooty;
import net.jolene.thumbandthicket.util.Slice;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiConsumer;

import static net.jolene.thumbandthicket.ThumbAndThicket.thumbandthicket$determineRootSide;
import static net.jolene.thumbandthicket.util.ModProperties.*;

@Mixin(DarkOakTrunkPlacer.class)
public class DarkOakTrunkPlacerMixin {

    @WrapOperation(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/trunk/DarkOakTrunkPlacer;getAndSetState(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)Z", ordinal = 0))
    private boolean gay(DarkOakTrunkPlacer instance, TestableWorld testableWorld, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, TreeFeatureConfig treeFeatureConfig, Operation<Boolean> original) {
        BlockState newState = treeFeatureConfig.trunkProvider.get(random, pos);
        if (newState.getBlock() instanceof LogBlock) {
            newState = thumbandthicket$determineRootSide(newState, (WorldAccess) testableWorld, pos).with(ModProperties.SLICE, Slice.THREE);
            int randomInt = random.nextBetween(1, 5);
            if (newState.get(ROOTY) == Rooty.NONE) {
                newState = newState.with(BRANCH, randomInt == 1);
                if (!newState.get(BRANCH)) newState = newState.with(HOLLOW, randomInt == 5);
            }
            replacer.accept(pos, newState);
        }

        return original.call(instance, testableWorld, replacer, random, pos, treeFeatureConfig);
    }
    @WrapOperation(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/trunk/DarkOakTrunkPlacer;getAndSetState(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)Z", ordinal = 1))
    private boolean gay1(DarkOakTrunkPlacer instance, TestableWorld testableWorld, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, TreeFeatureConfig treeFeatureConfig, Operation<Boolean> original) {
        BlockState newState = treeFeatureConfig.trunkProvider.get(random, pos);
        if (newState.getBlock() instanceof LogBlock) {
            newState = thumbandthicket$determineRootSide(newState, (WorldAccess) testableWorld, pos).with(ModProperties.SLICE, Slice.FOUR);
            int randomInt = random.nextBetween(1, 5);
            if (newState.get(ROOTY) == Rooty.NONE) {
                newState = newState.with(BRANCH, randomInt == 1);
                if (!newState.get(BRANCH)) newState = newState.with(HOLLOW, randomInt == 5);
            }
            replacer.accept(pos, newState);
        }

        return original.call(instance, testableWorld, replacer, random, pos, treeFeatureConfig);
    }
    @WrapOperation(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/trunk/DarkOakTrunkPlacer;getAndSetState(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)Z", ordinal = 2))
    private boolean gay2(DarkOakTrunkPlacer instance, TestableWorld testableWorld, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, TreeFeatureConfig treeFeatureConfig, Operation<Boolean> original) {
        BlockState newState = treeFeatureConfig.trunkProvider.get(random, pos);
        if (newState.getBlock() instanceof LogBlock) {
            newState = thumbandthicket$determineRootSide(newState, (WorldAccess) testableWorld, pos).with(ModProperties.SLICE, Slice.TWO);
            int randomInt = random.nextBetween(1, 5);
            if (newState.get(ROOTY) == Rooty.NONE) {
                newState = newState.with(BRANCH, randomInt == 1);
                if (!newState.get(BRANCH)) newState = newState.with(HOLLOW, randomInt == 5);
            }
            replacer.accept(pos, newState);
        }
        return original.call(instance, testableWorld, replacer, random, pos, treeFeatureConfig);
    }
    @WrapOperation(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/trunk/DarkOakTrunkPlacer;getAndSetState(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)Z", ordinal = 3))
    private boolean gay3(DarkOakTrunkPlacer instance, TestableWorld testableWorld, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, TreeFeatureConfig treeFeatureConfig, Operation<Boolean> original) {
        BlockState newState = treeFeatureConfig.trunkProvider.get(random, pos);
        if (newState.getBlock() instanceof LogBlock) {
            newState = thumbandthicket$determineRootSide(newState, (WorldAccess) testableWorld, pos).with(ModProperties.SLICE, Slice.ONE);
            int randomInt = random.nextBetween(1, 5);
            if (newState.get(ROOTY) == Rooty.NONE) {
                newState = newState.with(BRANCH, randomInt == 1);
                if (!newState.get(BRANCH)) newState = newState.with(HOLLOW, randomInt == 5);
            }
            replacer.accept(pos, newState);
        }

        return original.call(instance, testableWorld, replacer, random, pos, treeFeatureConfig);
    }
}
