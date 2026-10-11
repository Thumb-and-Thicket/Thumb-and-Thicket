package net.jolene.thumbandthicket.block;

import net.jolene.thumbandthicket.sound.ModSounds;
import net.jolene.thumbandthicket.util.Rooty;
import net.jolene.thumbandthicket.util.Slice;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

import static net.jolene.thumbandthicket.ThumbAndThicket.*;
import static net.jolene.thumbandthicket.util.ModProperties.*;

public class FungusStemBlock extends PillarBlock {
    public FungusStemBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(ROOTY, Rooty.NONE).with(SLICE, Slice.ZERO).with(ROOTY, Rooty.NONE).with(BRANCH, false).with(HOLLOW, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SLICE).add(ROOTY).add(BRANCH).add(HOLLOW);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        state = thumbandthicket$determineRootSide(state, world, pos);
        int random = Random.create().nextBetween(1,5);
        if (state.get(ROOTY) != Rooty.NONE) state = thumbandthicket$calculateSlice(state, world, pos);
        else {
            state = state.with(BRANCH, random == 1);
            if (!state.get(BRANCH)) state = state.with(HOLLOW, random == 5);
        }
        state = thumbandthicket$inheritSlice(state, world, pos);
        return state;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState newState = state;
        Direction.Axis axis = newState.get(AXIS);
        Direction rootBlockDirection = thumbandthicket$determineRootBlockDirection(state, pos, world, ModBlocks.ROOT_BLOCK);

        Direction logBlockDirection = thumbandthicket$determineRootBlockDirection(state, pos, world, state.getBlock());
        if (newState.get(ROOTY) != Rooty.NONE) newState = thumbandthicket$calculateSlice(newState, world, pos);

        if (logBlockDirection != null){
            Direction invertedLogBlockDirection = logBlockDirection.getOpposite();
            BlockState logBelow = world.getBlockState(pos.offset(logBlockDirection));
            BlockState logBelow1 = world.getBlockState(pos.offset(invertedLogBlockDirection));
            if (logBelow.contains(SLICE) && logBelow.get(SLICE) != Slice.ZERO && logBelow.get(AXIS) == axis) newState = newState.with(SLICE, logBelow.get(SLICE));
            else if (logBelow1.contains(SLICE) && logBelow1.get(SLICE) != Slice.ZERO && logBelow1.get(AXIS) == axis) newState = newState.with(SLICE, logBelow1.get(SLICE));
        }

        if (rootBlockDirection != null) {
            BlockState blockBelow = world.getBlockState(pos.offset(rootBlockDirection));
            if (blockBelow.contains(AXIS) && blockBelow.get(AXIS) == axis) newState = thumbandthicket$determineRootSide(newState, world, pos);
        }
        return newState;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.contains(ROOTY) && state.get(ROOTY) != Rooty.NONE) return VoxelShapes.cuboid(-0.00001, 0, -0.00001, 1.00001, 1, 1.00001);
        return VoxelShapes.fullCube();
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockState property = state;
        if (state.isIn(BlockTags.LOGS) && stack.isOf(Items.SHEARS) && !world.isClient) {
            boolean rooty = state.get(ROOTY) != Rooty.NONE;
            boolean branch = state.get(BRANCH);
            boolean hollow = state.get(HOLLOW);
            if (rooty || branch || hollow) {
                EquipmentSlot slot = null;
                switch (hand) {
                    case MAIN_HAND -> slot = EquipmentSlot.MAINHAND;
                    case OFF_HAND -> slot = EquipmentSlot.OFFHAND;
                }
                if (rooty) property = property.with(ROOTY, Rooty.NONE);
                if (branch) property = property.with(BRANCH, false);
                if (hollow) property = property.with(HOLLOW, false);

                if (!player.isCreative()) stack.damage(1, player, slot);
                BlockState newState = property;
                world.setBlockState(pos, newState);
                if (world instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(
                            new BlockStateParticleEffect(ParticleTypes.BLOCK, newState),
                            pos.getX() + 0.25,
                            pos.getY() + 0.25,
                            pos.getZ() + 0.25,
                            16,
                            0.5, 0.5, 0.5,
                            0.5
                    );
                }
                if (rooty || branch) PillarBlock.dropStack(world, pos, new ItemStack(Items.STICK));
                float f = MathHelper.nextBetween(world.random, 0.8f, 1.2f);
                world.playSound(
                        null,
                        pos,
                        ModSounds.SNIP,
                        SoundCategory.BLOCKS,
                        1.0F,
                        f
                );
                world.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH, pos, GameEvent.Emitter.of(player));
                return ItemActionResult.success(true);
            }
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}
