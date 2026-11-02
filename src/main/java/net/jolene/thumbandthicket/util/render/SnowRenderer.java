package net.jolene.thumbandthicket.util.render;

import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.chunk.BlockBufferAllocatorStorage;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.client.render.chunk.SectionBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.Map;

public class SnowRenderer {
    public static void applySnow(BlockState state, BlockPos pos, Random random, SectionBuilder builder, BlockRenderManager renderManager, Map<RenderLayer, BufferBuilder> renderMap, BlockBufferAllocatorStorage storage, ChunkRendererRegion region, MatrixStack stack) {
        if (state.getBlock() instanceof Snowable && state.get(ModProperties.LAYERS) > 0) {
            BlockState snowState = Blocks.SNOW.getDefaultState().with(Properties.LAYERS, state.get(ModProperties.LAYERS));
            BufferBuilder buffer = builder.beginBufferBuilding(renderMap, storage, RenderLayers.getBlockLayer(snowState));
            renderManager.renderBlock(snowState, pos, region, stack, buffer, true, random);
        }
    }
}