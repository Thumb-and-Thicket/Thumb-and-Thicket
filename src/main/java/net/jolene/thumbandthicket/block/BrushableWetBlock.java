package net.jolene.thumbandthicket.block;

import net.jolene.thumbandthicket.util.Soakable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrushableBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class BrushableWetBlock extends BrushableBlock implements Soakable {

    private final Soakable.WetnessLevel wetnessLevel;

    private static World WORLD = null;
    private static BlockPos POS = null;

    public BrushableWetBlock(Block baseBlock, SoundEvent brushingSound, SoundEvent brushingCompleteSound, Settings settings, WetnessLevel wetnessLevel) {
        super(baseBlock, brushingSound, brushingCompleteSound, settings);
        this.wetnessLevel = wetnessLevel;
    }

    @Override
    public WetnessLevel getSoakingLevel() {
        return wetnessLevel;
    }

//    @Override
//    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//        this.tickSoaking(state, world, pos);
//    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.getSoakingLevel() != WetnessLevel.WET) super.scheduledTick(state, world, pos, random);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        WORLD = world;
        POS = pos;
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!world.isClient() && world instanceof ServerWorld serverWorld) this.tickSoaking(state, serverWorld, pos);
        world.scheduleBlockTick(pos, this, 2);
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
