package net.jolene.thumbandthicket.block.entity;

import net.jolene.thumbandthicket.block.entity.renderer.ClamLidAnimator;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LidOpenable;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ClamSlabBlockEntity extends BlockEntity implements ImplementedInventory, LidOpenable {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private final ClamLidAnimator clamLidAnimator = new ClamLidAnimator();

    public ClamSlabBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CLAM_SLAB_BLOCK, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        super.readNbt(nbt, registryLookup);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    public static void clientTick(ClamSlabBlockEntity blockEntity) {
        blockEntity.clamLidAnimator.step();
    }

    static void playSound(World world, BlockPos pos, SoundEvent soundEvent) {
        double d = pos.getX() + 0.5;
        double e = pos.getY() + 0.5;
        double f = pos.getZ() + 0.5;

        world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.clamLidAnimator.setOpen(data > 0);
            return true;
        } else {
            return super.onSyncedBlockEvent(type, data);
        }
    }

    @Override
    public float getAnimationProgress(float tickDelta) {
        return this.clamLidAnimator.getProgress(tickDelta);
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);

        if (world != null && world.isClient) {
            boolean open = getCachedState().get(Properties.OPEN);
            clamLidAnimator.setOpenImmediately(open);
        }
    }
}
