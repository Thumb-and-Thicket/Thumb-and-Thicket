package net.jolene.thumbandthicket.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.item.ModItems;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.ROOT_BLOCK, drops(ModBlocks.ROOT_BLOCK));
        addDrop(ModBlocks.ROOTED_GRASS, drops(Blocks.ROOTED_DIRT));
        addDrop(ModBlocks.ROOTED_PODZOL, drops(Blocks.ROOTED_DIRT));
        addDrop(ModBlocks.WET_SAND, drops(ModBlocks.WET_SAND));
        addDrop(ModBlocks.DAMP_SAND, drops(ModBlocks.DAMP_SAND));
        addDrop(ModBlocks.WET_RED_SAND, drops(ModBlocks.WET_RED_SAND));
        addDrop(ModBlocks.DAMP_RED_SAND, drops(ModBlocks.DAMP_RED_SAND));

        addDrop(ModBlocks.HYDROTHERMAL_VENT_BLOCK, drops(ModBlocks.HYDROTHERMAL_VENT_BLOCK));
        addDrop(ModBlocks.HYDROTHERMAL_VENT, drops(ModBlocks.HYDROTHERMAL_VENT));
        addDrop(ModBlocks.CLAM_SLAB_BLOCK, drops(ModBlocks.CLAM_SLAB_BLOCK));

        addDrop(ModBlocks.PUFFED_DANDELION, drops(ModBlocks.PUFFED_DANDELION));
        addDrop(ModBlocks.LAVENDER, drops(ModBlocks.LAVENDER));
        addDrop(ModBlocks.CATTAIL, dropsWithShears(ModBlocks.CATTAIL, ItemEntry.builder(ModItems.CATTAIL_FLUFF)));
        addDrop(ModBlocks.DUCKWEED, dropsWithShears(ModBlocks.DUCKWEED));
        addDrop(ModBlocks.POISON_IVY, dropsWithShears(ModBlocks.POISON_IVY));
        addDrop(ModBlocks.ALGAE, dropsWithShears(ModBlocks.ALGAE));
        addDrop(ModBlocks.TINGED_SHORT_GRASS, shortPlantDrops(ModBlocks.TINGED_SHORT_GRASS));
        addDrop(ModBlocks.CLOVERS, flowerbedDrops(ModBlocks.CLOVERS));

        addDrop(ModBlocks.PALE_GOURD, drops(ModBlocks.PALE_GOURD));
        addDrop(ModBlocks.JACK_O_GOURD, drops(ModBlocks.JACK_O_GOURD));
        addDrop(ModBlocks.PALE_GOURD_STEM, cropStemDrops(ModBlocks.PALE_GOURD_STEM, ModItems.PALE_GOURD_SEEDS));
//        addDrop(ModBlocks.ATTACHED_PALE_GOURD_STEM, cropStemDrops(ModBlocks.ATTACHED_PALE_GOURD_STEM, ModItems.PALE_GOURD_SEEDS));
        addDrop(ModBlocks.CARVED_PALE_GOURD, drops(ModBlocks.CARVED_PALE_GOURD));

        addDrop(Blocks.BROWN_MUSHROOM, mushroomDrops(Blocks.BROWN_MUSHROOM));
        addDrop(Blocks.RED_MUSHROOM, mushroomDrops(Blocks.RED_MUSHROOM));
        addDrop(ModBlocks.PURPLE_MUSHROOM, mushroomDrops(ModBlocks.PURPLE_MUSHROOM));

//        addDrop(Blocks.MELON, melonDrops(Items.MELON_SLICE));

        addDrop(ModBlocks.CAVE_PARSNIP_CROP, cropDrops(ModBlocks.CAVE_PARSNIP_CROP, ModItems.CAVE_PARSNIP, Items.AIR, BlockStatePropertyLootCondition.builder(ModBlocks.CAVE_PARSNIP_CROP).properties(StatePredicate.Builder.create().exactMatch(CropBlock.AGE, 7))));

        addDrop(Blocks.COCOA, drops(Items.JUNGLE_SAPLING));
    }

    public LootTable.Builder mushroomDrops(Block mushroom) {
        return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(this.applyExplosionDecay(mushroom, ItemEntry.builder(mushroom).apply(IntStream.rangeClosed(1, 4).boxed().toList(), (mushroomAmount) -> SetCountLootFunction.builder(ConstantLootNumberProvider.create((float)mushroomAmount)).conditionally(BlockStatePropertyLootCondition.builder(mushroom).properties(StatePredicate.Builder.create().exactMatch(ModProperties.AMOUNT, mushroomAmount)))))));
    }

//    public LootTable.Builder melonDrops(Item melon) {
//        return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(this.applyExplosionDecay(melon, ItemEntry.builder(melon).apply(IntStream.rangeClosed(1, 4).boxed().toList(), (sliceAmount) -> SetCountLootFunction.builder(ConstantLootNumberProvider.create((float)sliceAmount)).conditionally(BlockStatePropertyLootCondition.builder(melon).properties(StatePredicate.Builder.create().exactMatch(ModProperties.SLICE, String.valueOf(sliceAmount))))))));
//    }
}
