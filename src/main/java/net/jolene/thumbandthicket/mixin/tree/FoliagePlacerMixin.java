package net.jolene.thumbandthicket.mixin.tree;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FoliagePlacer.class)
public class FoliagePlacerMixin {

    @WrapOperation(method = "placeFoliageBlock(Lnet/minecraft/world/TestableWorld;Lnet/minecraft/world/gen/foliage/FoliagePlacer$BlockPlacer;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;Lnet/minecraft/util/math/BlockPos;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/foliage/FoliagePlacer$BlockPlacer;placeBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"))
    private static void gay(FoliagePlacer.BlockPlacer instance, BlockPos pos, BlockState blockState, Operation<Void> original, @Local(argsOnly = true) TestableWorld world) {
        BlockState newState = blockState;
        WorldAccess worldAccess = (WorldAccess) world;
        Block block = blockState.getBlock();
        if (block instanceof LeavesBlock) newState = newState.with(ConnectingBlock.UP, worldAccess.getBlockState(pos.up()).isOf(block)).with(ConnectingBlock.DOWN, worldAccess.getBlockState(pos.down()).isOf(block)).with(ConnectingBlock.NORTH, worldAccess.getBlockState(pos.north()).isOf(block)).with(ConnectingBlock.EAST, worldAccess.getBlockState(pos.east()).isOf(block)).with(ConnectingBlock.SOUTH, worldAccess.getBlockState(pos.south()).isOf(block)).with(ConnectingBlock.WEST, worldAccess.getBlockState(pos.west()).isOf(block));
        original.call(instance, pos, newState);
    }
}
