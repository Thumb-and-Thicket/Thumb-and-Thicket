package net.jolene.thumbandthicket.mixin.immersiveinteractions;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.block.ModBlockTags;
import net.jolene.thumbandthicket.datagen.ModItemTagProvider;
import net.jolene.thumbandthicket.item.ChiselItem;
import net.jolene.thumbandthicket.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Objects;

import static net.jolene.thumbandthicket.ThumbAndThicket.thumbandthicket$copyProperty;

@Mixin(Item.class)
public class ItemMixin {

    @WrapMethod(method = "useOnBlock")
    private ActionResult thumbandthicket$useOnBlock(ItemUsageContext context, Operation<ActionResult> original) {
        ItemStack itemStack = context.getStack();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        String blockIdString = Registries.BLOCK.getId(state.getBlock()).toString();
        String[] baseBlock = blockIdString.split(":");
        Item barkFD = Registries.ITEM.get(Identifier.of("farmersdelight", "tree_bark"));

        if (!world.isClient) {
            ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();

            SoundEvent soundEvent = null;
            String prependString = "";
            String appendString = "";
            String targetString = "";
            String replaceString = "";
            String blockString = baseBlock[1];
            ItemStack stack = ItemStack.EMPTY;

            if (itemStack.getItem() instanceof PickaxeItem && state.isIn(ModBlockTags.CRACKABLE_BLOCKS)) {
                prependString = "cracked_";
                soundEvent = SoundEvents.BLOCK_DEEPSLATE_BRICKS_HIT;
            }
            if (state.isIn(ModBlockTags.CRACKED_BLOCKS) && itemStack.isIn(ModItemTagProvider.CAN_REPAIR_BRICK) && !blockString.contains("infested")) {
                targetString = "cracked_";
                soundEvent = SoundEvents.BLOCK_MUD_STEP;
            }
            if (state.isIn(ModBlockTags.MOSSABLE_BLOCKS) && itemStack.isIn(ModItemTagProvider.CAN_APPLY_MOSS)) {
                prependString = "mossy_";
                soundEvent = SoundEvents.BLOCK_MOSS_HIT;
            }
            if (itemStack.getItem() instanceof ShearsItem && state.isIn(ModBlockTags.MOSSY_BLOCKS)) {
                targetString = "mossy_";
                soundEvent = SoundEvents.BLOCK_GROWING_PLANT_CROP;
                stack = ModItems.MOSS_CLUMP.getDefaultStack();
            }
            if (state.isIn(BlockTags.LOGS) && (itemStack.isIn(ModItemTagProvider.CAN_APPLY_BARK) || itemStack.isOf(barkFD))) {
                if (blockString.contains("stripped_")) {
                    targetString = "stripped_";
                }
                if (blockString.contains("_log") && !blockString.contains("stripped_")) {
                    targetString = "_log";
                    replaceString = "_wood";
                }
                soundEvent = SoundEvents.BLOCK_WOOD_HIT;
            }
            if (itemStack.getItem() instanceof ChiselItem) {
                soundEvent = SoundEvents.BLOCK_GRINDSTONE_USE;
                if (state.isIn(ModBlockTags.CHISELABLE_BLOCKS)) {
                    prependString = "chiseled_";
                    if (state.getBlock() instanceof Oxidizable oxidizable) {
                        if (oxidizable.getDegradationLevel().ordinal() > 0){
                            String[] copperBlock = blockString.split("_");
                            prependString = "";
                            blockString = copperBlock[0] + "_chiseled_" + copperBlock[1];
                        }
                        if (blockString.contains("block")) blockString = "copper";
                    }
                    if (blockString.contains("waxed")) {
                        String[] waxedCopperBlock = blockString.split("_");
                        prependString = "";
                        blockString = waxedCopperBlock[0] + "_" + waxedCopperBlock[1] + "_chiseled_" + waxedCopperBlock[2];
                        if (blockString.contains("block")) blockString = waxedCopperBlock[0] + "_chiseled_" + waxedCopperBlock[1];
                    }
                }
                if (state.isIn(ModBlockTags.CHISELED_BLOCKS)) {
                    targetString = "chiseled_";
                    if (blockString.equals("waxed_chiseled_copper")) appendString = "_block";
                    if (state.getBlock() instanceof Oxidizable && blockString.equals("chiseled_copper")) appendString = "_block";
                }
            }

            if(!targetString.isEmpty()) blockString = blockString.replace(targetString, replaceString);
            if(!prependString.isEmpty()) blockString = prependString + blockString;
            if(!appendString.isEmpty()) blockString = blockString + appendString;

            Block newBlock = ThumbAndThicket.thumbandthicket$getBlockByName(blockString);
            if (!(newBlock instanceof AirBlock) && newBlock != state.getBlock()) {
                BlockState newState = newBlock.getDefaultState();
                for (Property<?> property : state.getProperties()) if (newState.contains(property)) newState = thumbandthicket$copyProperty(newState, state, property);

                if (newBlock instanceof ChiseledBookshelfBlock) newState = newState.with(HorizontalFacingBlock.FACING, context.getHorizontalPlayerFacing().getOpposite());

                world.setBlockState(pos, newState);
                if (soundEvent != null) world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS);
                if (itemStack.contains(DataComponentTypes.MAX_DAMAGE)) itemStack.damage(1, (ServerWorld) world, (ServerPlayerEntity) context.getPlayer(), item -> Objects.requireNonNull(context.getPlayer()).sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));
                if (!stack.isEmpty()) Block.dropStack(world, pos, stack);
                else itemStack.decrementUnlessCreative(1, player);
            }
            world.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH, pos, GameEvent.Emitter.of(player));
            return ActionResult.success(!(newBlock instanceof AirBlock) && !Objects.equals(blockString, baseBlock[1]));
        }
        return original.call(context);
    }
}
