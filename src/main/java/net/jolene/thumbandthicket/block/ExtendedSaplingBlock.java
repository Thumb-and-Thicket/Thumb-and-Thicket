package net.jolene.thumbandthicket.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import static net.jolene.thumbandthicket.util.ModProperties.SNIPPED;

public class ExtendedSaplingBlock extends SaplingBlock {
    private static final IntProperty AGE = Properties.AGE_3;
    private static final VoxelShape SHAPE_SMALL = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);
    private static final VoxelShape SHAPE_TALL = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 22.0, 14.0);

    public ExtendedSaplingBlock(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SNIPPED, false).with(AGE, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE);
        builder.add(SNIPPED);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int age = state.get(AGE);
        switch (age) {
            case 0 -> {
                return SHAPE_SMALL;
            }
            case 1, 2 -> {
                return SHAPE;
            }
            case 3 -> {
                return SHAPE_TALL;
            }
        }
        return null;
    }

    @Override
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        if (state.get(AGE) <= 2) {
            world.setBlockState(pos, state.cycle(AGE), Block.NOTIFY_NEIGHBORS);
        } else {
            this.generator.generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random);
        }
    }

    @Override
    protected float getMaxHorizontalModelOffset() {
        return 2f;
    }
}
