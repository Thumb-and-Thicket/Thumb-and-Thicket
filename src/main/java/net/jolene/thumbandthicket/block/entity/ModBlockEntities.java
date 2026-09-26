package net.jolene.thumbandthicket.block.entity;

import net.jolene.thumbandthicket.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModBlockEntities {
    public static BlockEntityType<RootBlockEntity> ROOT_BLOCK_ENTITY;
    public static BlockEntityType<FlowerCropBlockEntity> FLOWER_CROP_BLOCK;
    public static BlockEntityType<ClamSlabBlockEntity> CLAM_SLAB_BLOCK;
    public static BlockEntityType<BrushableBlockEntity> BRUSHABLE_BLOCK_ENTITY;

    public static void registerModBlockEntities() {
        ROOT_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MOD_ID, "root_block"),
                BlockEntityType.Builder.create(RootBlockEntity::new, ModBlocks.ROOT_BLOCK).build()
        );
        FLOWER_CROP_BLOCK = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MOD_ID, "flower_crop_block"),
                BlockEntityType.Builder.create(FlowerCropBlockEntity::new, ModBlocks.FLOWER_CROP).build()
        );
        CLAM_SLAB_BLOCK = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MOD_ID, "clam_slab_block"),
                BlockEntityType.Builder.create(ClamSlabBlockEntity::new, ModBlocks.CLAM_SLAB_BLOCK).build()
        );
        BRUSHABLE_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MOD_ID, "clam_slab_block"),
                BlockEntityType.Builder.create(BrushableBlockEntity::new, ModBlocks.CLAM_SLAB_BLOCK).build()
        );
    }
}
