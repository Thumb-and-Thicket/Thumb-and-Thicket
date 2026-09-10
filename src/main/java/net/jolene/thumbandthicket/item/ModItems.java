package net.jolene.thumbandthicket.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.effect.ModEffects;
import net.jolene.thumbandthicket.entity.ModEntities;
import net.jolene.thumbandthicket.fluid.ModFluids;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class ModItems {


    // The Prickly Effect will cause nearby entities to take cactus-like damage from the player

    public static final FoodComponent PRICKLY_PEAR_FOOD_COMPONENT = new FoodComponent.Builder().alwaysEdible().snack().nutrition(4).saturationModifier(0.3f).build();
    public static final FoodComponent GOLDEN_PRICKLY_PEAR_FOOD_COMPONENT = new FoodComponent.Builder().alwaysEdible().snack().nutrition(4).saturationModifier(1.2f).statusEffect(new StatusEffectInstance(ModEffects.PRICKLY, 100, 0), 1.0F).build();
    public static final FoodComponent ROTTEN_APPLE_FOOD_COMPONENT = new FoodComponent.Builder().nutrition(2).saturationModifier(0.3F).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 100, 0), 0.6F).build();
    public static final FoodComponent PEAR_FOOD_COMPONENT = (new FoodComponent.Builder()).nutrition(4).saturationModifier(0.3F).build();
    public static final FoodComponent ORANGE_FOOD_COMPONENT = (new FoodComponent.Builder()).nutrition(3).saturationModifier(0.3F).build();
    public static final FoodComponent PARSNIP_FOOD_COMPONENT = new FoodComponent.Builder().alwaysEdible().snack().nutrition(4).saturationModifier(0.3f).build();
    public static final FoodComponent CHERRY_FOOD_COMPONENT = (new FoodComponent.Builder()).nutrition(4).saturationModifier(0.2F).build();
    public static final FoodComponent DEW_FOOD_COMPONENT = (new FoodComponent.Builder()).nutrition(5).saturationModifier(0.7F).build();
    public static final FoodComponent SEED_FOOD_COMPONENT = (new FoodComponent.Builder()).nutrition(1).snack().build();

    public static final FoodComponent RAW_VENISON_FOOD_COMPONENT = new FoodComponent.Builder().snack().nutrition(4).saturationModifier(0.4f).build();
    public static final FoodComponent COOKED_VENISON_COMPONENT = new FoodComponent.Builder().snack().nutrition(9).saturationModifier(0.9f).build();

    public static final FoodComponent LUCKY_CLOVER_FOOD_COMPONENT = new FoodComponent.Builder().alwaysEdible().snack().nutrition(4).saturationModifier(1.2f).statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 6400, 0), 1.0F).build();

    public static final Item ROTTEN_APPLE = register(new Item(new Item.Settings().maxCount(64).food(ROTTEN_APPLE_FOOD_COMPONENT)), "rotten_apple");
    public static final Item PEAR = register(new Item(new Item.Settings().maxCount(64).food(PEAR_FOOD_COMPONENT)), "pear");
    public static final Item ORANGE = register(new Item(new Item.Settings().maxCount(64).food(ORANGE_FOOD_COMPONENT)), "orange");
    public static final Item CAVE_PARSNIP = register(new AliasedBlockItem(ModBlocks.CAVE_PARSNIP_CROP, new Item.Settings().maxCount(64).food(PARSNIP_FOOD_COMPONENT)), "cave_parsnip");
    public static final Item CHERRY = register(new Item(new Item.Settings().maxCount(64).food(CHERRY_FOOD_COMPONENT).recipeRemainder(Items.CHERRY_SAPLING)), "cherry");
    public static final Item PRICKLY_PEAR = register(new Item(new Item.Settings().maxCount(64).food(PRICKLY_PEAR_FOOD_COMPONENT)), "prickly_pear");

    public static final Item RAW_VENISON = register(new Item(new Item.Settings().maxCount(64).food(RAW_VENISON_FOOD_COMPONENT)), "raw_venison");
    public static final Item COOKED_VENISON = register(new Item(new Item.Settings().maxCount(64).food(COOKED_VENISON_COMPONENT)), "cooked_venison");

    public static final Item GOLDEN_PRICKLY_PEAR = register(new Item(new Item.Settings().rarity(Rarity.RARE).maxCount(64).food(GOLDEN_PRICKLY_PEAR_FOOD_COMPONENT)), "golden_prickly_pear");
    public static final Item PALE_GOURD_SEEDS = register(new AliasedBlockItem(ModBlocks.PALE_GOURD_STEM, new Item.Settings()), "pale_gourd_seeds");
    public static final Item DEW_BOTTLE = register(new Item(new Item.Settings().rarity(Rarity.RARE).maxCount(16).food(DEW_FOOD_COMPONENT).recipeRemainder(Items.GLASS_BOTTLE)), "dew_bottle");
    public static final Item DEW_DROP_SAPLING = register(new Item(new Item.Settings().rarity(Rarity.RARE).maxCount(16)), "dew_drop_sapling");
    public static final Item FLOWER_SEEDS = register(new FlowerSeedsItem(ModBlocks.FLOWER_CROP, new Item.Settings().food(SEED_FOOD_COMPONENT)), "flower_seeds");
    public static final Item BRINE_BUCKET = register(new BucketItem(ModFluids.BRINE_SOURCE, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)), "brine_bucket");

    public static final Item LUCKY_CLOVER = register(new Item(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(64).food(LUCKY_CLOVER_FOOD_COMPONENT)), "lucky_clover");
    public static final Item PEARL = register(new Item(new Item.Settings().rarity(Rarity.RARE).maxCount(16)), "pearl");
    public static final Item DUCKWEED = register(new PlaceableOnWaterItem(ModBlocks.DUCKWEED, new Item.Settings()), "duckweed");
    public static final Item LEAF_LITTER = registerVanilla(new PlaceableOnWaterLitter(com.blackgear.vanillabackport.common.registries.ModBlocks.LEAF_LITTER.get(), new Item.Settings()), "minecraft:leaf_litter");

    public static final Item DEER_SPAWN_EGG = register(
            new SpawnEggItem(ModEntities.DEER, 0x000000, 0x000000, new Item.Settings()),
            "deer_spawn_egg");
    public static final Item MOOSE_SPAWN_EGG = register(
            new SpawnEggItem(ModEntities.MOOSE, 0x000000, 0x000000, new Item.Settings()),
            "moose_spawn_egg");
    public static final Item BROWN_BEAR_SPAWN_EGG = register(
            new SpawnEggItem(ModEntities.BROWN_BEAR, 0x000000, 0x000000, new Item.Settings()),
            "brown_bear_spawn_egg");
    public static final Item BEAVER_SPAWN_EGG = register(
            new SpawnEggItem(ModEntities.BEAVER, 0x000000, 0x000000, new Item.Settings()),
            "beaver_spawn_egg");

    public static final Item FUR = register(new Item(new Item.Settings()), "fur");

    public static final Item CHISEL = register(new ChiselItem(new Item.Settings().maxDamage(128)), "chisel");
    public static final Item MOSS_CLUMP = register(new Item(new Item.Settings()), "moss_clump");
    public static final Item BARK = register(new BarkItem(new Item.Settings()), "bark");
    public static final Item PATINA = register(new PatinaItem(new Item.Settings()), "copper_patina");
    public static final Item WAXED_BRUSH = register(new WaxedBrushItem(new Item.Settings().maxDamage(48).recipeRemainder(Items.BRUSH)), "waxed_brush");

    private static Item register(Item item, String name) {
        return Registry.register(Registries.ITEM, ThumbAndThicket.id(name), item);
    }

    public static Item registerVanilla(Item item, String id) {
        return Registry.register(Registries.ITEM, id, item);
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(group -> {
                    group.addAfter(Items.APPLE, ModItems.ROTTEN_APPLE);
                    group.addAfter(ModItems.ROTTEN_APPLE, ModItems.PEAR);
                    group.addAfter(ModItems.PEAR, ModItems.ORANGE, ModItems.CAVE_PARSNIP, ModItems.CHERRY);
                    group.addAfter(Items.ENCHANTED_GOLDEN_APPLE, ModItems.PRICKLY_PEAR);
                    group.addAfter(ModItems.PRICKLY_PEAR, ModItems.GOLDEN_PRICKLY_PEAR);
                    group.addAfter(Items.COOKED_MUTTON, ModItems.RAW_VENISON);
                    group.addAfter(ModItems.RAW_VENISON, ModItems.COOKED_VENISON);
                });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(group -> group.addAfter(
                Items.POLAR_BEAR_SPAWN_EGG,
                ModItems.BROWN_BEAR_SPAWN_EGG
        ));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(group -> group.addAfter(
                Items.DOLPHIN_SPAWN_EGG,
                ModItems.BEAVER_SPAWN_EGG
        ));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(group -> group.addAfter(
                ModBlocks.CLOVERS,
                ModItems.LUCKY_CLOVER
        ));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(PATINA));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(CHISEL);
            entries.add(WAXED_BRUSH);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(MOSS_CLUMP);
            entries.add(BARK);
        });
    }
}
