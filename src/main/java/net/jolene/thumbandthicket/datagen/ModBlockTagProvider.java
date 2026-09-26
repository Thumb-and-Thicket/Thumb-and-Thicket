package net.jolene.thumbandthicket.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jolene.thumbandthicket.block.ModBlockTags;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

import static net.jolene.thumbandthicket.block.ModBlockTags.*;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModBlockTags.ROOTY_BLOCKS).add(Blocks.ROOTED_DIRT, ModBlocks.ROOT_BLOCK, ModBlocks.ROOTED_GRASS);
        getOrCreateTagBuilder(ModBlockTags.STACKABLE_MUSHROOMS).add(Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM, ModBlocks.PURPLE_MUSHROOM);
        getOrCreateTagBuilder(ModBlockTags.SNIPPABLE).add(Blocks.VINE, Blocks.SUGAR_CANE, Blocks.CACTUS);
        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.SNOWY_SHORT_GRASS)
                .add(ModBlocks.ROOTED_GRASS)
                .add(ModBlocks.ROOTED_PODZOL)
                .add(ModBlocks.PERMAFROST);
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(ModBlocks.ROOT_BLOCK);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.HYDROTHERMAL_VENT)
                .add(ModBlocks.HYDROTHERMAL_VENT_BLOCK)
                .add(ModBlocks.CLAM_SLAB_BLOCK);
        getOrCreateTagBuilder(ModBlockTags.GRASS_BLOCKS).add(Blocks.GRASS_BLOCK);
        getOrCreateTagBuilder(ModBlockTags.TALL_CROPS).add(Blocks.WHEAT);
        getOrCreateTagBuilder(BlockTags.DIRT).add(ModBlocks.ROOTED_GRASS, ModBlocks.ROOTED_PODZOL, ModBlocks.PERMAFROST);
        getOrCreateTagBuilder(BlockTags.MUSHROOM_GROW_BLOCK).add(ModBlocks.ROOTED_PODZOL);
        getOrCreateTagBuilder(CHISELABLE_BLOCKS)
                .add(Blocks.BOOKSHELF)
                .add(Blocks.STONE_BRICKS)
                .add(Blocks.COBBLED_DEEPSLATE)
                .add(Blocks.TUFF)
                .add(Blocks.TUFF_BRICKS)
                .add(Blocks.SANDSTONE)
                .add(Blocks.RED_SANDSTONE)
                .add(Blocks.NETHER_BRICKS)
                .add(Blocks.POLISHED_BLACKSTONE_BRICKS)
                .add(Blocks.QUARTZ_BLOCK)
                .add(Blocks.COPPER_BLOCK)
                .add(Blocks.EXPOSED_COPPER)
                .add(Blocks.WEATHERED_COPPER)
                .add(Blocks.OXIDIZED_COPPER)
                .add(Blocks.WAXED_COPPER_BLOCK)
                .add(Blocks.WAXED_EXPOSED_COPPER)
                .add(Blocks.WAXED_WEATHERED_COPPER)
                .add(Blocks.WAXED_OXIDIZED_COPPER)
                .add(com.blackgear.vanillabackport.common.registries.ModBlocks.CINNABAR.get())
                .add(com.blackgear.vanillabackport.common.registries.ModBlocks.SULFUR.get())
                .add(com.blackgear.vanillabackport.common.registries.ModBlocks.RESIN_BRICKS.get());

        getOrCreateTagBuilder(CHISELED_BLOCKS)
                .add(Blocks.CHISELED_STONE_BRICKS)
                .add(Blocks.CHISELED_DEEPSLATE)
                .add(Blocks.CHISELED_TUFF)
                .add(Blocks.CHISELED_TUFF_BRICKS)
                .add(Blocks.CHISELED_SANDSTONE)
                .add(Blocks.CHISELED_RED_SANDSTONE)
                .add(Blocks.CHISELED_NETHER_BRICKS)
                .add(Blocks.CHISELED_POLISHED_BLACKSTONE)
                .add(Blocks.CHISELED_QUARTZ_BLOCK)
                .add(Blocks.CHISELED_COPPER)
                .add(Blocks.EXPOSED_CHISELED_COPPER)
                .add(Blocks.WEATHERED_CHISELED_COPPER)
                .add(Blocks.OXIDIZED_CHISELED_COPPER)
                .add(Blocks.WAXED_CHISELED_COPPER)
                .add(Blocks.WAXED_EXPOSED_CHISELED_COPPER)
                .add(Blocks.WAXED_WEATHERED_CHISELED_COPPER)
                .add(Blocks.WAXED_OXIDIZED_CHISELED_COPPER)
                .add(com.blackgear.vanillabackport.common.registries.ModBlocks.CHISELED_CINNABAR.get())
                .add(com.blackgear.vanillabackport.common.registries.ModBlocks.CHISELED_SULFUR.get())
                .add(com.blackgear.vanillabackport.common.registries.ModBlocks.CHISELED_RESIN_BRICKS.get());

        getOrCreateTagBuilder(MOSSABLE_BLOCKS)
                .add(Blocks.COBBLESTONE)
                .add(Blocks.COBBLESTONE_STAIRS)
                .add(Blocks.COBBLESTONE_SLAB)
                .add(Blocks.COBBLESTONE_WALL)
                .add(Blocks.STONE_BRICKS)
                .add(Blocks.STONE_BRICK_STAIRS)
                .add(Blocks.STONE_BRICK_SLAB)
                .add(Blocks.STONE_BRICK_WALL)
                .add(Blocks.INFESTED_STONE_BRICKS);

        getOrCreateTagBuilder(MOSSY_BLOCKS)
                .add(Blocks.MOSSY_COBBLESTONE)
                .add(Blocks.MOSSY_COBBLESTONE_STAIRS)
                .add(Blocks.MOSSY_COBBLESTONE_SLAB)
                .add(Blocks.MOSSY_COBBLESTONE_WALL)
                .add(Blocks.MOSSY_STONE_BRICKS)
                .add(Blocks.MOSSY_STONE_BRICK_STAIRS)
                .add(Blocks.MOSSY_STONE_BRICK_SLAB)
                .add(Blocks.MOSSY_STONE_BRICK_WALL)
                .add(Blocks.INFESTED_MOSSY_STONE_BRICKS);

        getOrCreateTagBuilder(CRACKABLE_BLOCKS)
                .add(Blocks.STONE_BRICKS)
                .add(Blocks.DEEPSLATE_BRICKS)
                .add(Blocks.DEEPSLATE_TILES)
                .add(Blocks.NETHER_BRICKS)
                .add(Blocks.POLISHED_BLACKSTONE_BRICKS);

        getOrCreateTagBuilder(CRACKED_BLOCKS)
                .add(Blocks.CRACKED_STONE_BRICKS)
                .add(Blocks.CRACKED_DEEPSLATE_BRICKS)
                .add(Blocks.CRACKED_DEEPSLATE_TILES)
                .add(Blocks.CRACKED_NETHER_BRICKS)
                .add(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);

        getOrCreateTagBuilder(NEST_BLOCKS)
                .add(Blocks.HAY_BLOCK);

        getOrCreateTagBuilder(BlockTags.SAND)
                .add(ModBlocks.DAMP_SAND)
                .add(ModBlocks.WET_SAND)
                .add(ModBlocks.DAMP_RED_SAND)
                .add(ModBlocks.WET_RED_SAND)
                .add(ModBlocks.DAMP_SUSPICIOUS_SAND)
                .add(ModBlocks.WET_SUSPICIOUS_SAND);
    }
}
