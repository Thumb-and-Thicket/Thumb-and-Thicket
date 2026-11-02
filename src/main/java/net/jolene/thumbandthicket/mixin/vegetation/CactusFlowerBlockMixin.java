package net.jolene.thumbandthicket.mixin.vegetation;

import com.blackgear.vanillabackport.common.level.blocks.CactusFlowerBlock;
import com.blackgear.vanillabackport.common.registries.blocks.ModBlocks;
import net.jolene.thumbandthicket.item.ModItems;
import net.jolene.thumbandthicket.mixin.BlockAccessor;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.state.property.Properties.*;


@Mixin(CactusFlowerBlock.class)
public abstract class CactusFlowerBlockMixin extends Block implements Fertilizable {

    @Unique private static final int MAX_AGE = 2;
    @Unique private static final IntProperty AGE = Properties.AGE_2;

    public CactusFlowerBlockMixin(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(getAgeProperty(), 0));
    }

    @Unique
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Unique
    public int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return getAge(state) < MAX_AGE;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.down()).getBlock() instanceof CactusBlock;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (getAge(state) < MAX_AGE && world.getBlockState(pos.down()).getBlock() instanceof CactusBlock) {
            if (random.nextInt() < 1 && getAge(state) == 1) {
                world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1).with(ModProperties.GOLDEN, true), Block.NOTIFY_LISTENERS);
            } else world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i;
        if (world.random.nextInt(5) == 0 && (i = state.get(AGE)) < 2) {
            world.setBlockState(pos, state.with(AGE, i + 1), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 2;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        int i = state.get(AGE);
        if (i > 1) {
            ItemStack stack = new ItemStack(ModItems.PRICKLY_PEAR, 1);
            if (state.get(ModProperties.GOLDEN)) {
                stack = new ItemStack(ModItems.GOLDEN_PRICKLY_PEAR, 1);
            }
            CactusFlowerBlock.dropStack(world, pos, stack);
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0f, 0.8f + world.random.nextFloat() * 0.4f);
            BlockState blockState = state.with(AGE, 1).with(ModProperties.GOLDEN, false);
            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hit);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (getAge(state) == MAX_AGE){
            if (entity instanceof ItemEntity item && (item.getStack().isOf(ModItems.PRICKLY_PEAR) || item.getStack().isOf(ModBlocks.CACTUS_FLOWER.get().asItem()) || item.getStack().isOf(Blocks.CACTUS.asItem()))) {
                return;
            }
            entity.damage(world.getDamageSources().cactus(), 1.0f);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE_2);
        builder.add(ModProperties.GOLDEN);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void thumbandthicket$appendSnippedProperty(AbstractBlock.Settings settings, CallbackInfo ci) {
        Block cactusFlowerBlock = CactusFlowerBlock.class.cast(this);
        BlockState defaultBlockState = cactusFlowerBlock.getDefaultState();
        ((BlockAccessor) cactusFlowerBlock).invokeSetDefaultState(defaultBlockState.with(ModProperties.GOLDEN, false));
    }
}
