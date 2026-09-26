package net.jolene.thumbandthicket.item;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.block.ModBlockTags;
import net.jolene.thumbandthicket.datagen.ModItemTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Items;
import net.minecraft.item.SignItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.PointOfInterestTypeTags;
import net.minecraft.text.Text;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.jolene.thumbandthicket.ThumbAndThicket.isModLoaded;

public class ModItemTooltips {
    public static void register() {
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            Block block = Block.getBlockFromItem(itemStack.getItem());
            List<Text> variants = new ArrayList<>();

            if (itemStack.isOf(Items.SHEARS)) {
                if (Screen.hasShiftDown()) {
                    list.add(1, Text.literal(""));
                    list.add(2, Text.translatable("tooltip.thumbandthicket.shears"));
                    list.add(3, Text.translatable("tooltip.thumbandthicket.shears1"));
                    list.add(4, Text.translatable("tooltip.thumbandthicket.shears2"));
                    list.add(5, Text.translatable("tooltip.thumbandthicket.shears3"));
                } else {
                    list.add(1, Text.translatable("tooltip.thumbandthicket.shears"));
                    addShift(2, list);
                }
            }
            {
                if (itemStack.isOf(ModItems.PATINA)) {
                    if (Screen.hasShiftDown()) list.add(1, Text.translatable("tooltip.thumbandthicket.copper_patina"));
                    else addShift(1, list);
                }
                if (itemStack.isOf(ModItems.MOSS_CLUMP)) {
                    if (Screen.hasShiftDown()) list.add(1, Text.translatable("tag.item.thumbandthicket.can_apply_moss"));
                    else addShift(1, list);
                }
                if (itemStack.isOf(ModItems.CHISEL)) {
                    if (Screen.hasShiftDown()) list.add(1, Text.translatable("tooltip.thumbandthicket.chisel"));
                    else addShift(1, list);
                }
                if (itemStack.isIn(ModItemTagProvider.CAN_APPLY_BARK)) {
                    if (Screen.hasShiftDown()) list.add(1, Text.translatable("tag.item.thumbandthicket.can_apply_bark"));
                    else addShift(1, list);
                }
                if (itemStack.isIn(ModItemTagProvider.CAN_WAX_COPPER)) {
                    if (Screen.hasShiftDown()) {
                        if (isModLoaded("waxed_workstations"))
                            list.add(1, Text.translatable("tag.item.thumbandthicket.can_wax_blocks"));
                        else list.add(1, Text.translatable("tag.item.thumbandthicket.can_wax_copper"));
                    }
                    else addShift(1, list);
                }

                if (block.getDefaultState().isIn(ModBlockTags.CRACKABLE_BLOCKS)) variants.add(Text.translatable("tag.block.thumbandthicket.crackable_blocks"));
                if (block.getDefaultState().isIn(ModBlockTags.MOSSABLE_BLOCKS)) variants.add(Text.translatable("tag.block.thumbandthicket.mossable_blocks"));
                if (block.getDefaultState().isIn(ModBlockTags.CHISELABLE_BLOCKS)) variants.add(Text.translatable("tag.block.thumbandthicket.chiselable_blocks"));

                Optional<RegistryEntry<PointOfInterestType>> optional = PointOfInterestTypes.getTypeForState(block.getDefaultState());
                if (isModLoaded("waxed_workstations") && optional.isPresent() && optional.get().isIn(PointOfInterestTypeTags.ACQUIRABLE_JOB_SITE)) variants.add(Text.translatable("tag.block.thumbandthicket.waxable_blocks"));
                if (block instanceof Oxidizable || itemStack.getItem() instanceof SignItem || itemStack.getItem() instanceof HangingSignItem) variants.add(Text.translatable("tag.block.thumbandthicket.waxable_blocks"));
                if (block instanceof Oxidizable) variants.addLast(Text.translatable("tag.block.thumbandthicket.oxidizable_blocks"));

                if (!variants.isEmpty()) {
                    if (Screen.hasShiftDown()) list.addAll(1, variants);
                    else addShift(1, list);
                }
            }
        });
    }

    public static void registerModItemTooltips() {
        ThumbAndThicket.LOGGER.info("Registering Tooltips for " + ThumbAndThicket.MOD_ID);
        register();
    }

    public static List<Text> addShift(int ordinal, List<Text> list) {
        list.add(ordinal, Text.translatable("tooltip.thumbandthicket.shift"));
        return list;
    }
}

