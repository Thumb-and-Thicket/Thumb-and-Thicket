package net.jolene.thumbandthicket.mixin.tree;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.jolene.thumbandthicket.block.LogBlock;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.util.Rooty;
import net.jolene.thumbandthicket.util.Slice;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiConsumer;

import static net.jolene.thumbandthicket.util.ModProperties.*;

@Mixin(GiantTrunkPlacer.class)
public class GiantTrunkPlacerMixin {

    @WrapOperation(method = "setLog", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos$Mutable;set(Lnet/minecraft/util/math/Vec3i;III)Lnet/minecraft/util/math/BlockPos$Mutable;"))
    private Mutable modifyPlacedLog(Mutable instance, Vec3i pos, int x, int y, int z, Operation<Mutable> original, @Local(argsOnly = true) TestableWorld world, @Local Mutable tmpPos, @Local(ordinal = 0) int dx, @Local(ordinal = 2) int dz, @Local(argsOnly = true) BiConsumer<BlockPos, BlockState> replacer, @Local(argsOnly = true) TreeFeatureConfig config, @Local(argsOnly = true) Random random) {
        Mutable newPos = original.call(instance, pos, x, y, z);

        BlockState state = config.trunkProvider.get(random, tmpPos);

        if (state != null && state.getBlock() instanceof LogBlock) {
            BlockState newState = state;
            WorldAccess worldAccess = (WorldAccess) world;

//            newState = thumbandthicket$determineRootSide(newState, worldAccess, tmpPos);

            if (worldAccess.getBlockState(newPos.down()).isOf(ModBlocks.ROOT_BLOCK)) {
                newState = newState.with(ModProperties.ROOTY, Rooty.BOTTOM);
            } else {
                newState = newState.with(ModProperties.ROOTY, Rooty.NONE);
            }

            if (dx == 1 && dz == 1) newState = newState.with(ModProperties.SLICE, Slice.ONE);
            else if (dx == 0 && dz == 1) newState = newState.with(ModProperties.SLICE, Slice.TWO);
            else if (dx == 0 && dz == 0) newState = newState.with(ModProperties.SLICE, Slice.THREE);
            else if (dx == 1 && dz == 0) newState = newState.with(ModProperties.SLICE, Slice.FOUR);

            int randomInt = random.nextBetween(1,5);
            if (newState.get(ROOTY) == Rooty.NONE){
                newState = newState.with(BRANCH, randomInt == 1);
                if (!newState.get(BRANCH)) newState = newState.with(HOLLOW, randomInt == 5);
            }

            replacer.accept(newPos, newState);
        }

        return newPos;
    }
}