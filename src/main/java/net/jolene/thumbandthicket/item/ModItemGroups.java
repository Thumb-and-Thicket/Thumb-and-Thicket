package net.jolene.thumbandthicket.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
        public static final ItemGroup THUMB_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
                Identifier.of(ThumbAndThicket.MOD_ID, "thumb_and_thicket"),
                FabricItemGroup.builder().icon(() -> new ItemStack(Items.OAK_SAPLING))
                        .displayName(Text.translatable("itemgroup.thumbandthicket.thumbandthicket"))
                        .entries((displayContext, entries) -> {

                            // NINE PER SECTION

                            entries.add(Items.OAK_SAPLING);
                            entries.add(Items.SPRUCE_SAPLING);
                            entries.add(Items.BIRCH_SAPLING);
                            entries.add(Items.JUNGLE_SAPLING);
                            entries.add(Items.ACACIA_SAPLING);
                            entries.add(Items.DARK_OAK_SAPLING);
                            entries.add(Items.MANGROVE_PROPAGULE);
                            entries.add(Items.CHERRY_SAPLING);
                            entries.add(com.blackgear.vanillabackport.common.registries.ModBlocks.PALE_OAK_SAPLING.get());
                            entries.add(ModItems.BARK);
                            entries.add(ModItems.LEAF_LITTER);
                            entries.add(ModBlocks.CLOVERS);
                            entries.add(ModItems.LUCKY_CLOVER);
                            entries.add(Items.DANDELION);
                            entries.add(ModBlocks.PUFFED_DANDELION);
                            entries.add(ModBlocks.ROSE);
                            entries.add(ModBlocks.BLUE_ROSE);
                            entries.add(ModBlocks.LAVENDER);
                            entries.add(ModBlocks.MILKWEED);
                            entries.add(ModBlocks.CATTAIL);
                            entries.add(ModBlocks.BEACH_GRASS);
                            entries.add(ModBlocks.POISON_IVY);
                            entries.add(Blocks.LILY_PAD);
                            entries.add(ModBlocks.DUCKWEED);
                            entries.add(ModBlocks.ALGAE);
                            entries.add(ModBlocks.WET_SAND);

                            entries.add(ModBlocks.ROOTED_GRASS);
                            entries.add(ModBlocks.ROOTED_PODZOL);
                            entries.add(Blocks.ROOTED_DIRT);
                            entries.add(ModBlocks.ROOT_BLOCK);
                            entries.add(Blocks.HANGING_ROOTS);
                            //entries.add(ModBlocks.LONG_HANGING_ROOTS);
                            //entries.add(ModItems.FERTILIZER);
                            //entries.add(ModItems.SUNFLOWER_SEEDS);
                            entries.add(ModItems.FLOWER_SEEDS);

                            entries.add(Items.APPLE);
                            entries.add(Items.GOLDEN_APPLE);
                            entries.add(ModItems.ROTTEN_APPLE);
                            entries.add(ModItems.PEAR);
                            //entries.add(ModItems.ROTTEN_PEAR);
                            entries.add(ModItems.ORANGE);
                            entries.add(ModItems.CAVE_PARSNIP);
                           //entries.add(ModItems.ROTTEN_ORANGE);
                            entries.add(ModItems.CHERRY);
                           //entries.add(ModItems.ROTTEN_CHERRY);
                            entries.add(ModItems.PRICKLY_PEAR);
                            //entries.add(ModItems.SHRIVELED_PRICKLY_PEAR);
                            entries.add(ModItems.GOLDEN_PRICKLY_PEAR);

                            entries.add(Items.RED_MUSHROOM);
                            entries.add(Items.BROWN_MUSHROOM);
                            entries.add(ModBlocks.PURPLE_MUSHROOM);
                            entries.add(ModBlocks.PURPLE_MUSHROOM_BLOCK);
                            entries.add(Blocks.PUMPKIN);
                            entries.add(Blocks.MELON);
                            entries.add(Items.WHEAT_SEEDS);
                            entries.add(Items.WHEAT);
                            entries.add(ModItems.PALE_GOURD_SEEDS);
                            entries.add(ModBlocks.PALE_GOURD);
                            entries.add(ModBlocks.CARVED_PALE_GOURD);
                            entries.add(ModBlocks.JACK_O_GOURD);

                            entries.add(ModBlocks.TINGED_SHORT_GRASS);
                            entries.add(ModBlocks.SNOWY_SHORT_GRASS);
                            //entries.add(ModBlocks.TINGED_TALL_GRASS);
                            entries.add(ModBlocks.SNOWY_TALL_GRASS);
                            entries.add(ModBlocks.SNOWY_SHORT_FERN);
                            entries.add(ModBlocks.SNOWY_LARGE_FERN);
                            entries.add(ModBlocks.SNOWY_BUSH);
                            //entries.add(ModBlocks.SNOWY_SWEET_BERRY_BUSH);

                            entries.add(ModItems.DEER_SPAWN_EGG);
                            entries.add(ModItems.BEAVER_SPAWN_EGG);
                            entries.add(ModItems.BROWN_BEAR_SPAWN_EGG);
                            entries.add(ModItems.MOOSE_SPAWN_EGG);
                            entries.add(ModItems.RAW_VENISON);
                            entries.add(ModItems.COOKED_VENISON);
                            entries.add(ModItems.FUR);
                            entries.add(Items.SHEARS);
                            entries.add(ModItems.WAXED_BRUSH);
                            entries.add(ModItems.CHISEL);
                            entries.add(ModItems.PATINA);

                            entries.add(ModItems.DEW_BOTTLE);
                            entries.add(ModItems.BRINE_BUCKET);

                            entries.add(ModBlocks.HYDROTHERMAL_VENT_BLOCK);
                            entries.add(ModBlocks.HYDROTHERMAL_VENT);
                            entries.add(ModBlocks.CLAM_SLAB_BLOCK);
                        }).build());



    public static void registerItemGroups() {
        ThumbAndThicket.LOGGER.info("Registering Item Group for " + ThumbAndThicket.MOD_ID);
    }
}
