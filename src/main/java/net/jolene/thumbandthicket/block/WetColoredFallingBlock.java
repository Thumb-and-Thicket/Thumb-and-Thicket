package net.jolene.thumbandthicket.block;

import net.jolene.thumbandthicket.util.Soakable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ColoredFallingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ColorCode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class WetColoredFallingBlock extends ColoredFallingBlock implements Soakable {
    private final Soakable.WetnessLevel wetnessLevel;
    private final int fallDelay;

    private static World WORLD = null;
    private static BlockPos POS = null;

    public WetColoredFallingBlock(ColorCode color, Settings settings, WetnessLevel wetnessLevel, int fallDelay) {
        super(color, settings);
        this.wetnessLevel = wetnessLevel;
        this.fallDelay = fallDelay;
    }

    @Override
    public WetnessLevel getSoakingLevel() {
        return wetnessLevel;
    }

    @Override
    public int getFallDelay() {
        return fallDelay;
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        WORLD = world;
        POS = pos;
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!world.isClient() && world instanceof ServerWorld serverWorld) this.tickSoaking(state, serverWorld, pos);
        world.scheduleBlockTick(pos, this, this.getFallDelay());
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        WORLD = world;
        POS = pos;
        this.tickSoaking(state, world, pos);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        if (!world.isClient() && world instanceof ServerWorld serverWorld) this.tickSoaking(this.getDefaultState(), serverWorld, pos);
        return super.getPlacementState(ctx);
    }

    //    @Override
//    protected boolean hasRandomTicks(BlockState state) {
//        World world = WORLD;
//        BlockPos pos = POS;
//        if (world != null && pos != null) {
//            return this.wetnessLevel.ordinal() > 0 && !thumbandthicket$touchesWater(world, pos);
//        }
//        return false;
//    }
}
