package net.jolene.thumbandthicket.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.fluid.ModFluids;
import net.jolene.thumbandthicket.util.*;
import net.jolene.thumbandthicket.world.gen.ModConfiguredFeatures;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block PUFFED_DANDELION = register(new PuffedDandelionBlock(AbstractBlock.Settings.copy(Blocks.DANDELION)), "puffed_dandelion", true);
    public static final Block ROSE = register(new FlowerBlock(StatusEffects.SATURATION, 10,AbstractBlock.Settings.copy(Blocks.POPPY)), "rose", true);
    public static final Block BLUE_ROSE = register(new FlowerBlock(StatusEffects.POISON, 10,AbstractBlock.Settings.copy(Blocks.POPPY)), "blue_rose", true);
    public static final Block LAVENDER = register(new LavenderFlowerBlock(StatusEffects.SATURATION, 10,AbstractBlock.Settings.copy(Blocks.POPPY)), "lavender", true);
    public static final Block SHORT_LILAC = register(new FlowerBlock(StatusEffects.POISON, 10,AbstractBlock.Settings.copy(Blocks.LILAC)), "short_lilac", true);
    public static final Block MILKWEED = register(new WaterloggedTallPlantBlock(AbstractBlock.Settings.copy(Blocks.LARGE_FERN)), "milkweed", true);
    public static final Block CATTAIL = register(new WaterloggedTallPlantBlock(AbstractBlock.Settings.copy(Blocks.LARGE_FERN)), "cattail", true);
    public static final Block POISON_IVY = register(new VineBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).replaceable()), "poison_ivy", true);
    public static final Block CLOVERS = register(new CloverBlock(AbstractBlock.Settings.copy(Blocks.PINK_PETALS).replaceable()), "clovers", true);
    public static final Block ALGAE = register(new CloverBlock(AbstractBlock.Settings.copy(Blocks.PINK_PETALS)), "algae", true);
    public static final Block DUCKWEED = register(new DuckweedBlock(AbstractBlock.Settings.copy(Blocks.LILY_PAD).mapColor(MapColor.DARK_GREEN).noCollision()), "duckweed", false);

    public static final Block FRUIT_LEAVES = register(new FruitLeavesBlock(AbstractBlock.Settings.create()), "fruit_leaves", true);
    public static final Block FRUIT_LOG = register(new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)), "fruit_log", true);
    public static final Block FRUIT_WOOD = register(new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD)), "fruit_wood", true);
    public static final Block STRIPPED_FRUIT_LOG = register(new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG)), "stripped_fruit_log", true);
    public static final Block STRIPPED_FRUIT_WOOD = register(new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD)), "stripped_fruit_wood", true);
    public static final Block FRUIT_PLANKS = register(new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)), "fruit_planks", true);
    public static final Block FRUIT_STAIRS = register(new StairsBlock(FRUIT_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(Blocks.OAK_STAIRS)), "fruit_stairs", true);
    public static final Block FRUIT_SLAB = register(new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB)), "fruit_slab", true);
    public static final Block FRUIT_FENCE = register(new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)), "fruit_fence", true);
    public static final Block FRUIT_FENCE_GATE = register(new FenceGateBlock(ModWoodType.FRUIT, AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE)), "fruit_fence_gate", true);
    public static final Block FRUIT_DOOR = register(new DoorBlock(ModBlockSetType.FRUIT, AbstractBlock.Settings.copy(Blocks.OAK_DOOR)), "fruit_door", true);
    public static final Block FRUIT_TRAPDOOR = register(new TrapdoorBlock(ModBlockSetType.FRUIT, AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR)), "fruit_trapdoor", true);
    public static final Block FRUIT_PRESSURE_PLATE = register(new PressurePlateBlock(ModBlockSetType.FRUIT, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE)), "fruit_pressure_plate", true);
    public static final Block FRUIT_BUTTON = register(new ButtonBlock(ModBlockSetType.FRUIT,40, AbstractBlock.Settings.copy(Blocks.OAK_BUTTON)), "fruit_button", true);
    public static final Block FRUIT_SIGN = register(new SignBlock(ModWoodType.FRUIT, AbstractBlock.Settings.copy(Blocks.OAK_SIGN)), "fruit_sign", true);
    public static final Block FRUIT_HANGING_SIGN = register(new HangingSignBlock(ModWoodType.FRUIT, AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN)), "fruit_hanging_sign", true);

    public static final Block AZALEA_LOG = register(new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)), "azalea_log", true);
    public static final Block AZALEA_WOOD = register(new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD)), "azalea_wood", true);
    public static final Block STRIPPED_AZALEA_LOG = register(new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG)), "stripped_azalea_log", true);
    public static final Block STRIPPED_AZALEA_WOOD = register(new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD)), "stripped_azalea_wood", true);
    public static final Block AZALEA_PLANKS = register(new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)), "azalea_planks", true);
    public static final Block AZALEA_STAIRS = register(new StairsBlock(AZALEA_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(Blocks.OAK_STAIRS)), "azalea_stairs", true);
    public static final Block AZALEA_SLAB = register(new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB)), "azalea_slab", true);
    public static final Block AZALEA_FENCE = register(new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)), "azalea_fence", true);
    public static final Block AZALEA_FENCE_GATE = register(new FenceGateBlock(ModWoodType.AZALEA, AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE)), "azalea_fence_gate", true);
    public static final Block AZALEA_DOOR = register(new DoorBlock(ModBlockSetType.AZALEA, AbstractBlock.Settings.copy(Blocks.OAK_DOOR)), "azalea_door", true);
    public static final Block AZALEA_TRAPDOOR = register(new TrapdoorBlock(ModBlockSetType.AZALEA, AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR)), "azalea_trapdoor", true);
    public static final Block AZALEA_PRESSURE_PLATE = register(new PressurePlateBlock(ModBlockSetType.AZALEA, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE)), "azalea_pressure_plate", true);
    public static final Block AZALEA_BUTTON = register(new ButtonBlock(ModBlockSetType.AZALEA,40, AbstractBlock.Settings.copy(Blocks.OAK_BUTTON)), "azalea_button", true);
    public static final Block AZALEA_SIGN = register(new SignBlock(ModWoodType.AZALEA, AbstractBlock.Settings.copy(Blocks.OAK_SIGN)), "azalea_sign", true);
    public static final Block AZALEA_HANGING_SIGN = register(new HangingSignBlock(ModWoodType.AZALEA, AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN)), "azalea_hanging_sign", true);

    public static final Block SNOWY_BUSH = register(new ShortSnowyPlantBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).replaceable().noCollision().nonOpaque().blockVision((state, world, pos) -> state.get(ModProperties.LAYERS) >= 8).hardness(0.1f).sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY).dynamicBounds()), "snowy_bush", true);
    public static final Block SNOWY_SHORT_GRASS = register(new ShortSnowyPlantBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).replaceable().noCollision().nonOpaque().blockVision((state, world, pos) -> state.get(ModProperties.LAYERS) >= 8).hardness(0.1f).sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY).dynamicBounds()), "snowy_short_grass", true);
    public static final Block SNOWY_TALL_GRASS = register(new TallSnowyPlantBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).replaceable().noCollision().nonOpaque().blockVision((state, world, pos) -> state.get(ModProperties.LAYERS) >= 8).hardness(0.1f).sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY).dynamicBounds()), "snowy_tall_grass", true);
    public static final Block SNOWY_SHORT_FERN = register(new ShortSnowyPlantBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).replaceable().noCollision().nonOpaque().blockVision((state, world, pos) -> state.get(ModProperties.LAYERS) >= 8).hardness(0.1f).sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY).dynamicBounds()), "snowy_fern", true);
    public static final Block SNOWY_LARGE_FERN = register(new TallSnowyPlantBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).replaceable().noCollision().nonOpaque().blockVision((state, world, pos) -> state.get(ModProperties.LAYERS) >= 8).hardness(0.1f).sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY).dynamicBounds()), "snowy_large_fern", true);
    public static final Block TINGED_SHORT_GRASS = register(new ShortPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).dynamicBounds()), "tinged_short_grass", true);
    public static final Block BEACH_GRASS = register(new TallPlantBlock(AbstractBlock.Settings.copy(Blocks.LARGE_FERN)), "beach_grass", true);

    public static final Block MYCELIAL_SPROUTS = register(new SproutsBlock(AbstractBlock.Settings.create().noCollision().nonOpaque().sounds(BlockSoundGroup.FUNGUS).replaceable().mapColor(MapColor.MAGENTA).pistonBehavior(PistonBehavior.DESTROY).breakInstantly().offset(AbstractBlock.OffsetType.XZ)), "mycelial_sprouts", true);
    public static final Block PURPLE_MUSHROOM = register(new MushroomPlantBlock(ModConfiguredFeatures.HUGE_PURPLE_MUSHROOM_KEY, AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM)), "purple_mushroom", true);
    public static final Block PURPLE_MUSHROOM_BLOCK = register(new MushroomBlock(AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM_BLOCK)), "purple_mushroom_block", true);

    public static final Block WILTED_CROP = register(new WiltedCropBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.CROP).noCollision().replaceable()), "wilted_crop", false);
    public static final Block FLOWER_CROP = register(new FlowerCropBlock(AbstractBlock.Settings.copy(Blocks.CARROTS).ticksRandomly()), "flower_crop");
    public static final Block CAVE_PARSNIP_CROP = register(new CaveCropBlock(AbstractBlock.Settings.copy(Blocks.CARROTS).ticksRandomly()), "cave_parsnips");

    public static final Block DEW_DROP = register(new DewDropPlantBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.CROP).noCollision()), "dew_drop", true);
    public static final Block DEW_CAULDRON = register(new DewCauldronBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON), ModCauldronBehavior.DEW_CAULDRON_BEHAVIOR), "dew_cauldron", false);
    public static final Block DEW_DROP_CROP_CAULDRON = register(new DewDropCropBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON).ticksRandomly()), "dew_drop_crop_cauldron", false);

    public static final Block PALE_GOURD = register(new PaleGourdBlock(AbstractBlock.Settings.copy(Blocks.PUMPKIN).mapColor(DyeColor.WHITE)), "pale_gourd", true);
    public static final Block JACK_O_GOURD = register(new CarvedPumpkinBlock(AbstractBlock.Settings.copy(Blocks.JACK_O_LANTERN).mapColor(DyeColor.WHITE)), "jack_o_gourd", true);
    public static final Block CARVED_PALE_GOURD = register(new WearableCarvedPumpkinBlock(AbstractBlock.Settings.copy(Blocks.CARVED_PUMPKIN).mapColor(DyeColor.WHITE)), "carved_pale_gourd", true);
    public static final Block ATTACHED_PALE_GOURD_STEM = register(new AttachedStemBlock(ModKeys.PALE_GOURD, ModKeys.PALE_GOURD_STEM, ModKeys.PALE_GOURD_SEEDS,AbstractBlock.Settings.copy(Blocks.ATTACHED_PUMPKIN_STEM)), "attached_pale_gourd_stem", false);
    public static final Block PALE_GOURD_STEM = register(new StemBlock(ModKeys.PALE_GOURD, ModKeys.ATTACHED_PALE_GOURD_STEM, ModKeys.PALE_GOURD_SEEDS,AbstractBlock.Settings.copy(Blocks.PUMPKIN_STEM)), "pale_gourd_stem", false);

    public static final Block WET_SAND = register(new WetSandBlock(AbstractBlock.Settings.copy(Blocks.SAND).sounds(BlockSoundGroup.SAND).requiresTool()), "wet_sand", true);
    public static final Block DAMP_SAND = register(new WetSandBlock(AbstractBlock.Settings.copy(Blocks.SAND).sounds(BlockSoundGroup.SAND).requiresTool()), "damp_sand", false);
    public static final Block WET_RED_SAND = register(new WetSandBlock(AbstractBlock.Settings.copy(Blocks.SAND).sounds(BlockSoundGroup.SAND).requiresTool()), "wet_red_sand", true);
    public static final Block DAMP_RED_SAND = register(new WetSandBlock(AbstractBlock.Settings.copy(Blocks.SAND).sounds(BlockSoundGroup.SAND).requiresTool()), "damp_red_sand", false);
    public static final Block WET_SUSPICIOUS_SAND = register(new WetSandBlock(AbstractBlock.Settings.copy(Blocks.SAND).sounds(BlockSoundGroup.SAND).requiresTool()), "wet_suspicious_sand", true);
    public static final Block DAMP_SUSPICIOUS_SAND = register(new WetSandBlock(AbstractBlock.Settings.copy(Blocks.SAND).sounds(BlockSoundGroup.SAND).requiresTool()), "damp_suspicious_sand", false);
    public static final Block ROOT_BLOCK = register(new RootBlock(AbstractBlock.Settings.copy(Blocks.ROOTED_DIRT).sounds(BlockSoundGroup.HANGING_ROOTS).ticksRandomly().requiresTool()), "root_block", true);
    public static final Block ROOTED_GRASS = register(new RootedGrassBlock(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.HANGING_ROOTS).ticksRandomly().requiresTool()), "rooted_grass", true);
    public static final Block ROOTED_PODZOL = register(new RootedPodzolBlock(AbstractBlock.Settings.copy(Blocks.PODZOL).sounds(BlockSoundGroup.HANGING_ROOTS).requiresTool()), "rooted_podzol", true);
    public static final Block PERMAFROST = register(new Block(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK).mapColor(MapColor.BROWN).requiresTool().strength(2.0F, 3.0F)), "permafrost", true);
    public static final Block MUDDY_GRASS = register(new Block(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK).mapColor(MapColor.BROWN).requiresTool()), "muddy_grass", true);

    public static final Block BRINE_BLOCK = register(new FluidBlock(ModFluids.BRINE_SOURCE, AbstractBlock.Settings.create().mapColor(MapColor.TEAL).noCollision().nonOpaque().replaceable()), "brine_block", true);
    public static final Block HYDROTHERMAL_VENT_BLOCK = register(new HydrothermalVentBlockBlock(AbstractBlock.Settings.create().hardness(1.5f).mapColor(MapColor.BROWN).requiresTool()), "hydrothermal_vent_block", true);
    public static final Block HYDROTHERMAL_VENT = register(new HydrothermalVentBlock(AbstractBlock.Settings.create().hardness(1.5f).mapColor(MapColor.BROWN).requiresTool()), "hydrothermal_vent", true);

    public static final Block CLAM_SLAB_BLOCK = register(new ClamSlabBlock(AbstractBlock.Settings.create().strength(3.0f, 6.0F).mapColor(MapColor.BROWN).requiresTool()), "clam_slab", true);
    public static final Block BARNACLES = register(new Block(AbstractBlock.Settings.create().strength(3.0f, 6.0F).mapColor(MapColor.BROWN).requiresTool()), "barnacles", true);

    public static final Block TEMPERATE_CHICKEN_EGG_BLOCK = register(new EggBlock(AbstractBlock.Settings.copy(Blocks.TURTLE_EGG), EntityType.CHICKEN), "chicken_egg", true);
    public static final Block COLD_CHICKEN_EGG_BLOCK = register(new EggBlock(AbstractBlock.Settings.copy(Blocks.TURTLE_EGG), EntityType.CHICKEN), "chicken_egg", true);
    public static final Block WARM_CHICKEN_EGG_BLOCK = register(new EggBlock(AbstractBlock.Settings.copy(Blocks.TURTLE_EGG), EntityType.CHICKEN), "chicken_egg", true);

    private static Block register(Block block, String name, boolean hasItem) {
        Identifier id = ThumbAndThicket.id(name);
        if (hasItem) {
            Item item = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, item);
        }
        return Registry.register(Registries.BLOCK, id, block);

    }

    public static Block register(Block block, String name) {
        Identifier id = ThumbAndThicket.id(name);
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void registerModBlocks() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addBefore(Items.HANGING_ROOTS, ROOT_BLOCK);
            entries.addBefore(Blocks.SAND, WET_SAND);
            entries.addAfter(Items.ROOTED_DIRT, ROOTED_GRASS, ROOTED_PODZOL);
            entries.addAfter(Items.PINK_PETALS, CLOVERS);
            entries.addAfter(Items.SHORT_GRASS, SNOWY_SHORT_GRASS);
            entries.addAfter(Items.TALL_GRASS, SNOWY_TALL_GRASS);
            entries.addAfter(Items.FERN, SNOWY_SHORT_FERN);
            entries.addAfter(Items.LARGE_FERN, SNOWY_LARGE_FERN);
            entries.addAfter(Items.DANDELION, PUFFED_DANDELION);
            entries.addAfter(Items.POPPY, ROSE);
            entries.addAfter(ModBlocks.ROSE, BLUE_ROSE);
            entries.addAfter(Items.NETHER_SPROUTS, MYCELIAL_SPROUTS);
            entries.addAfter(com.blackgear.vanillabackport.common.registries.ModBlocks.BUSH.get(), SNOWY_BUSH);
        });
    }


    public static void initialize() {
    }
}

