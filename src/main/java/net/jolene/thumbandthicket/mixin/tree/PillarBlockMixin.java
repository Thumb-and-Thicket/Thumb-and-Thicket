package net.jolene.thumbandthicket.mixin.tree;

import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.mixin.BlockAccessor;
import net.jolene.thumbandthicket.mixin.SettingsAccessor;
import net.jolene.thumbandthicket.sound.ModSounds;
import net.jolene.thumbandthicket.util.Rooty;
import net.jolene.thumbandthicket.util.Slice;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jolene.thumbandthicket.util.ModProperties.*;
import static net.jolene.thumbandthicket.ThumbAndThicket.*;

@Mixin(value = PillarBlock.class, priority = 990)
public class PillarBlockMixin extends Block {

    @Shadow
    @Final
    public static EnumProperty<Direction.Axis> AXIS;

    public PillarBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void appendLogProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci){
        Settings settings = (PillarBlock.class.cast(this)).getSettings();
        SettingsAccessor accessor = (SettingsAccessor) settings;
        if (accessor.getInstrument() == NoteBlockInstrument.BASS && accessor.getSoundGroup() == BlockSoundGroup.WOOD) builder.add(SLICE).add(ROOTY).add(BRANCH).add(HOLLOW);
        if (accessor.getInstrument() == NoteBlockInstrument.BASS && accessor.getSoundGroup() == BlockSoundGroup.CHERRY_WOOD) builder.add(SLICE).add(ROOTY).add(BRANCH).add(HOLLOW);
        if (accessor.getInstrument() == NoteBlockInstrument.BASS && accessor.getSoundGroup() == BlockSoundGroup.NETHER_STEM) builder.add(SLICE).add(ROOTY).add(BRANCH).add(HOLLOW);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void appendLogPropertiesValue(Settings settings, CallbackInfo ci) {
        Block pillarBlock = PillarBlock.class.cast(this);
        BlockState defaultBlockState = pillarBlock.getDefaultState();
        if (defaultBlockState.contains(ROOTY) && defaultBlockState.contains(SLICE)) {
            ((BlockAccessor)pillarBlock).invokeSetDefaultState(defaultBlockState.with(ROOTY, Rooty.NONE).with(SLICE, Slice.ZERO).with(ROOTY, Rooty.NONE).with(BRANCH, false).with(HOLLOW, false));
        }
    }

    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void modifyPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = cir.getReturnValue();
        if (state.contains(ROOTY) && state.contains(SLICE)) {
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

            cir.setReturnValue(state);
        }
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState newState = state;
        Direction.Axis axis = newState.get(AXIS);
        Direction rootBlockDirection = thumbandthicket$determineRootBlockDirection(state, pos, world, ModBlocks.ROOT_BLOCK);

        if (newState.contains(SLICE)) {
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
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.contains(ROOTY) && state.get(ROOTY) != Rooty.NONE) return VoxelShapes.cuboid(-0.00001, 0, -0.00001, 1.00001, 1, 1.00001);
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getCollisionShape(
            BlockState state,
            BlockView world,
            BlockPos pos,
            ShapeContext context
    ) {
        if (state.contains(ROOTY)
                && state.get(ROOTY) != Rooty.NONE) {

            return VoxelShapes.cuboid(
                    -0.000001,
                    0,
                    -0.000001,
                    1.000001,
                    1,
                    1.000001
            );
        }

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