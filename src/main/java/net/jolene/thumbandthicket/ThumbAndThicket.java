package net.jolene.thumbandthicket;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.jolene.thumbandthicket.block.entity.ModBlockEntities;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.component.ModDataComponentTypes;
import net.jolene.thumbandthicket.entity.ModEntities;
import net.jolene.thumbandthicket.entity.custom.*;
import net.jolene.thumbandthicket.item.ModItemGroups;
import net.jolene.thumbandthicket.item.ModItems;
import net.jolene.thumbandthicket.util.*;
import net.jolene.thumbandthicket.world.gen.ModFeatureReplacements;
import net.jolene.thumbandthicket.world.gen.feature.ModFeatures;
import net.jolene.thumbandthicket.world.gen.placementmodifier.ModPlacementModifierType;
import net.jolene.thumbandthicket.world.gen.treedecorator.ModTreeDecorators;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static net.jolene.thumbandthicket.util.ModProperties.ROOTY;
import static net.jolene.thumbandthicket.util.ModProperties.SLICE;
import static net.minecraft.block.PillarBlock.AXIS;

public class ThumbAndThicket implements ModInitializer {
	public static final String MOD_ID = "thumbandthicket";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModBlocks.registerModBlocks();
        ModBlocks.initialize();
        ModItems.initialize();
        ModBlockEntities.registerModBlockEntities();
        ModFeatures.registerModFeatures();
        ModFeatureReplacements.replaceFeatures();
        ModFeatureReplacements.addFeatures();
        thumbandthicket$registerResourcePacks();
        ModCauldronBehavior.registerCauldronBehavior();
        ModLootTableModifications.modifyLootTables();
        ModDataComponentTypes.registerDataComponentTypes();
        ModTreeDecorators.initialize();

        ModPlacementModifierType.SNOWY_BELOW.codec();
        // Mob Attributes
        FabricDefaultAttributeRegistry.register(ModEntities.BEAVER, BeaverEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BROWN_BEAR, BrownBearEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MOOSE, MooseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DEER, DeerEntity.createAttributes());
        RecipeUtil.registerRecipeDisabler();
        LOGGER.info("Muddy!");
	}


	public static Identifier id(String name) {
		return Identifier.of(MOD_ID, name);
	}

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static Direction thumbandthicket$determineRootBlockDirection(BlockState state, BlockPos pos, WorldAccess world, Block block) {
        Direction.Axis axis = state.get(AXIS);

        if (axis.isVertical()) {
            BlockState below = world.getBlockState(pos.down());
            BlockState above = world.getBlockState(pos.up());
            if (below.getBlock() == block) return Direction.DOWN;
            if (above.getBlock() == block) return Direction.UP;
        } else if (axis == Direction.Axis.X) {
            BlockState west = world.getBlockState(pos.west());
            BlockState east = world.getBlockState(pos.east());
            if (west.getBlock() == block) return Direction.WEST;
            if (east.getBlock() == block) return Direction.EAST;
        } else {
            BlockState north = world.getBlockState(pos.north());
            BlockState south = world.getBlockState(pos.south());
            if (north.getBlock() == block) return Direction.NORTH;
            if (south.getBlock() == block) return Direction.SOUTH;
        }
        return null;
    }

    public static BlockState thumbandthicket$determineRootSide(BlockState state, WorldAccess world, BlockPos pos) {

        Direction rootBlockDirection = thumbandthicket$determineRootBlockDirection(state, pos, world, ModBlocks.ROOT_BLOCK);
        if (!state.contains(ROOTY) || rootBlockDirection == null) return state;

        switch (rootBlockDirection) {
            case DOWN, WEST, NORTH -> {
                if (world.getBlockState(pos.offset(rootBlockDirection)).getBlock() == ModBlocks.ROOT_BLOCK) return state.with(ROOTY, Rooty.BOTTOM);
            }
            case UP, EAST, SOUTH -> {
                if (world.getBlockState(pos.offset(rootBlockDirection)).getBlock() == ModBlocks.ROOT_BLOCK) return state.with(ROOTY, Rooty.TOP);
            }
        }
        return state;
    }

    public static BlockState thumbandthicket$calculateSlice(BlockState state, WorldAccess world, BlockPos pos) {
        Direction.Axis axis = state.get(AXIS);
        Block block = state.getBlock();

        Direction[] dirs;
        if (axis == Direction.Axis.Y) dirs = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        else if (axis == Direction.Axis.X) dirs = new Direction[]{Direction.UP, Direction.SOUTH, Direction.DOWN, Direction.NORTH};
        else dirs = new Direction[]{Direction.UP, Direction.EAST, Direction.DOWN, Direction.WEST};

        boolean[] connected = new boolean[4];
        for (int i = 0; i < dirs.length; i++) {
            BlockState neighbor = world.getBlockState(pos.offset(dirs[i]));
            connected[i] = neighbor.getBlock() == block && neighbor.contains(ROOTY) && neighbor.get(ROOTY) == state.get(ROOTY) && neighbor.get(AXIS) == axis;
        }

        if (connected[0] && connected[1]) return state.with(SLICE, Slice.TWO);
        if (connected[1] && connected[2]) return state.with(SLICE, Slice.THREE);
        if (connected[2] && connected[3]) return state.with(SLICE, Slice.FOUR);
        if (connected[3] && connected[0]) return state.with(SLICE, Slice.ONE);

        return state;
    }

    public static BlockState thumbandthicket$inheritSlice(BlockState state, WorldAccess world, BlockPos pos) {

        Direction.Axis axis = state.get(AXIS);
        Block block = state.getBlock();
        Direction rootBlockDirection = thumbandthicket$determineRootBlockDirection(state, pos, world, state.getBlock());

        Direction dir1, dir2;
        if (rootBlockDirection != null) {
            if (world.getBlockState(pos.offset(rootBlockDirection)).contains(AXIS)) {
                if (axis == world.getBlockState(pos.offset(rootBlockDirection)).get(AXIS)) {
                    dir1 = rootBlockDirection.getOpposite();
                    dir2 = rootBlockDirection;
                    BlockPos neighborPos1 = pos.offset(dir1);
                    BlockPos neighborPos2 = pos.offset(dir2);

                    for (BlockPos neighborPos : new BlockPos[]{neighborPos1, neighborPos2}) {
                        BlockState neighbor = world.getBlockState(neighborPos);
                        if (neighbor.getBlock() == block && neighbor.contains(SLICE) && neighbor.get(AXIS) == axis) return state.with(SLICE, neighbor.get(SLICE));
                    }
                }
            }
        }
        return state;
    }

    public static void thumbandthicket$registerResourcePacks() {
        ModContainer modContainer = FabricLoader.getInstance()
                .getModContainer(MOD_ID)
                .orElseThrow(() -> new IllegalStateException("Missing mod modContainer"));

        ResourceManagerHelper.registerBuiltinResourcePack(
                Identifier.of(MOD_ID, "tat_overrides"), modContainer,
                Text.translatable("pack.thumbandthicket.name"),
                ResourcePackActivationType.ALWAYS_ENABLED
        );
        ResourceManagerHelper.registerBuiltinResourcePack(
                Identifier.of(MOD_ID, "tat_worldgen"), modContainer,
                Text.translatable("pack.thumbandthicket.name_worldgen"),
                ResourcePackActivationType.ALWAYS_ENABLED
        );
        FabricLoader.getInstance().getModContainer("bigwater").ifPresent(bigWaterContainer -> ResourceManagerHelper.registerBuiltinResourcePack(
                Identifier.of(MOD_ID, "tat_bigwater"),
                bigWaterContainer,
                Text.translatable("pack.thumbandthicket.name_bigwater"),
                ResourcePackActivationType.DEFAULT_ENABLED
        ));
    }

    public static Item thumbandthicket$getItemByName(String name) {
        for (Item item : Registries.ITEM) {
            Identifier id = Registries.ITEM.getId(item);
            if (id.getPath().equals(name)) {
                return item;
            }
        }
        return Items.AIR;
    }

    public static Block thumbandthicket$getBlockByName(String name) {
        for (Block block : Registries.BLOCK) {
            Identifier id = Registries.BLOCK.getId(block);
            if (id.getPath().equals(name)) {
                return block;
            }
        }
        return Blocks.AIR;
    }

    public static Random RANDOM = null;

    public static final List<BlockPos> WILTED_CROPS = BlockPos.stream(-1, 0, -1, 1, 1, 1).map(BlockPos::toImmutable).toList();

    public static <T extends Comparable<T>> BlockState thumbandthicket$copyProperty(BlockState newState, BlockState oldState, Property<T> property) {
        return newState.with(property, oldState.get(property));
    }

    public static boolean thumbandthicket$touchesWater(World world, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            if (world.getFluidState(pos.offset(dir)).isIn(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }

}