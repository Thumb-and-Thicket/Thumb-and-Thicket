package net.jolene.thumbandthicket.block;

import com.mojang.serialization.MapCodec;
import net.jolene.thumbandthicket.block.entity.ClamSlabBlockEntity;
import net.jolene.thumbandthicket.block.entity.ModBlockEntities;
import net.jolene.thumbandthicket.util.ModLootTableUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.state.property.Properties.*;

public class ClamSlabBlock extends BlockWithEntity implements Waterloggable {

    public static final MapCodec<ClamSlabBlock> CODEC = ClamSlabBlock.createCodec(ClamSlabBlock::new);

    protected ClamSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(Properties.FACING, Direction.NORTH).with(WATERLOGGED, false).with(Properties.OPEN, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ClamSlabBlockEntity(pos, state);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.getBlockEntity(pos) instanceof ClamSlabBlockEntity clamSlabBlockEntity && !clamSlabBlockEntity.isEmpty() && !player.isCreative()) {
            ItemStack itemStack = new ItemStack(this);
            itemStack.applyComponentsFrom(clamSlabBlockEntity.createComponentMap());

            world.getBlockEntity(pos).setStackNbt(itemStack, world.getRegistryManager());
            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof ClamSlabBlockEntity clamSlabBlockEntity){
            if (!state.get(OPEN)) {
                world.addSyncedBlockEvent(pos, state.getBlock(), 1, 1);
                world.setBlockState(pos, state.with(OPEN, true));
                if (world.getFluidState(pos).getFluid().matchesType(Fluids.WATER)) world.scheduleBlockTick(pos, this, 60);
                return ItemActionResult.success(true);
            } else {
                if (clamSlabBlockEntity.isEmpty() && !stack.isEmpty()) {
                    clamSlabBlockEntity.setStack(0, stack.copyWithCount(1));
                    stack.decrementUnlessCreative(1, player);

                    clamSlabBlockEntity.markDirty();
                    world.updateListeners(pos, state, state, 0);
                    return ItemActionResult.success(true);
                }
                if (player.isSneaking()) {
                    ItemStack clamSlabBlockEntityStack = clamSlabBlockEntity.getStack(0);
                    if (player.getInventory().getMainHandStack().isEmpty()) player.getInventory().insertStack(player.getInventory().selectedSlot, clamSlabBlockEntityStack);
                    else player.getInventory().offerOrDrop(clamSlabBlockEntityStack);
                    world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1f);
                    clamSlabBlockEntity.clear();

                    clamSlabBlockEntity.markDirty();
                    world.updateListeners(pos, state, state, 0);
                    return ItemActionResult.success(true);
                }
                world.addSyncedBlockEvent(pos, state.getBlock(), 1, 0);
                world.setBlockState(pos, state.with(OPEN, false));
                return ItemActionResult.success(true);
            }
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.addSyncedBlockEvent(pos, state.getBlock(), 1, 0);
        world.setBlockState(pos, state.with(OPEN, false));
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? validateTicker(type, ModBlockEntities.CLAM_SLAB_BLOCK, (world1, pos, state1, blockEntity) -> ClamSlabBlockEntity.clientTick(blockEntity)) : null;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(Properties.FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED).add(OPEN).add(FACING);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    }

    @Override
    protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    }

    @Override
    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBlockEntity(pos) instanceof ClamSlabBlockEntity clamSlabBlockEntity && clamSlabBlockEntity.isEmpty() && !state.get(OPEN)) {
            if (random.nextBetween(0,10) == 0 && world.getFluidState(pos).getFluid().matchesType(Fluids.WATER)) {
                LootTable lootTable = world.getServer().getReloadableRegistries().getLootTable(ModLootTableUtil.CLAM_LOOT);
                LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(world).add(LootContextParameters.ORIGIN, pos.toCenterPos()).add(LootContextParameters.BLOCK_ENTITY, clamSlabBlockEntity).build(ModLootTableUtil.CLAM);
                List<ItemStack> list = lootTable.generateLoot(lootContextParameterSet);

                clamSlabBlockEntity.setStack(0, list.getFirst());
                clamSlabBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            }
        }
        super.randomTick(state, world, pos, random);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return !state.get(OPEN);
    }
}
