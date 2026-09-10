package net.jolene.thumbandthicket;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.block.entity.ModBlockEntities;
import net.jolene.thumbandthicket.block.entity.renderer.ClamSlabBlockEntityRenderer;
import net.jolene.thumbandthicket.effect.ModEffects;
import net.jolene.thumbandthicket.entity.ModEntities;
import net.jolene.thumbandthicket.entity.ModEntitySpawns;
import net.jolene.thumbandthicket.entity.client.*;
import net.jolene.thumbandthicket.fluid.ModFluids;
import net.jolene.thumbandthicket.item.ModItemTooltips;
import net.jolene.thumbandthicket.item.ModItems;
import net.jolene.thumbandthicket.particle.ModParticles;
import net.jolene.thumbandthicket.sound.ModSounds;
import net.jolene.thumbandthicket.util.ModEntityModelLayers;
import net.jolene.thumbandthicket.util.foam.FoamShapeUtil;
import net.jolene.thumbandthicket.util.ModColors;
import net.jolene.thumbandthicket.util.foam.FoamManager;
import net.jolene.thumbandthicket.util.foam.FoamRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ThumbAndThicketClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModColors.registerBlockColors();
        ModColors.registerItemColors();
        ModItemTooltips.registerModItemTooltips();
        ModParticles.registerModParticles();
        ModEffects.registerEffects();
        ModSounds.registerModSounds();
        ModEntitySpawns.addSpawns();
        FoamManager.renderFoam();
        FoamRenderer.init();
        ModEntityModelLayers.registerModelLayers();

        EntityModelLayerRegistry.registerModelLayer(MooseModel.MOOSE, MooseModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.MOOSE, MooseRenderer::new);


        EntityModelLayerRegistry.registerModelLayer(BrownBearModel.BROWN_BEAR, BrownBearModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.BROWN_BEAR, BrownBearRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(BeaverModel.BEAVER, BeaverModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.BEAVER, BeaverRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(DeerModel.DEER,DeerModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.DEER, DeerRenderer::new);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> -1,
                ModItems.MOOSE_SPAWN_EGG);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> -1,
                ModItems.BROWN_BEAR_SPAWN_EGG);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> -1,
                ModItems.BEAVER_SPAWN_EGG);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> -1,
                ModItems.DEER_SPAWN_EGG);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world != null && pos != null) {
                return BiomeColors.getGrassColor(world, pos);
            }
            return 0xFFFFFF;
        }, ModBlocks.CLOVERS);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world != null && pos != null) {
                return BiomeColors.getGrassColor(world, pos);
            }
            return 0xFFFFFF;
        }, ModBlocks.TINGED_SHORT_GRASS);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> 0xFFFFFF, Blocks.LILY_PAD);

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> client.execute(() -> {
            SpriteAtlasTexture atlas = MinecraftClient.getInstance().getBakedModelManager().getAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
            FoamShapeUtil.populateSpriteSetArrays(atlas);

            for (RegistryEntry<Block> entry : Registries.BLOCK.iterateEntries(BlockTags.LOGS)) {
                Block block = entry.value();
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
            }
        }));

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(
                new SimpleSynchronousResourceReloadListener() {
                    @Override
                    public Identifier getFabricId() {
                        return Identifier.of(MOD_ID, "log_reload_listener");
                    }

                    @Override
                    public void reload(ResourceManager manager) {
                        SpriteAtlasTexture atlas = MinecraftClient.getInstance().getBakedModelManager().getAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
                        FoamShapeUtil.populateSpriteSetArrays(atlas);

                        for (RegistryEntry<Block> entry : Registries.BLOCK.iterateEntries(BlockTags.LOGS)) {
                            Block block = entry.value();
                            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
                        }
                    }
                }
        );
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DUCKWEED, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROOT_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROOTED_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROOTED_PODZOL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLOVERS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CATTAIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BEACH_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PUFFED_DANDELION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROSE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_ROSE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LAVENDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SNOWY_SHORT_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TINGED_SHORT_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WILTED_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MYCELIAL_SPROUTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEW_DROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLAM_SLAB_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HYDROTHERMAL_VENT, RenderLayer.getCutout());

        BlockEntityRendererFactories.register(ModBlockEntities.CLAM_SLAB_BLOCK, ClamSlabBlockEntityRenderer::new);

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.BRINE_SOURCE, ModFluids.FLOWING_BRINE, new SimpleFluidRenderHandler(Identifier.of(MOD_ID, "block/brine_still"), Identifier.of(MOD_ID, "block/brine_flow"), Identifier.of(MOD_ID, "block/brine_overlay")));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.BRINE_SOURCE, ModFluids.FLOWING_BRINE);

    }

}