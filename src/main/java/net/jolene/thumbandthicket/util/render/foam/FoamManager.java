package net.jolene.thumbandthicket.util.render.foam;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.ChunkPos;

import java.util.*;

public class FoamManager {
    public static final Map<ChunkPos, FoamChunk> CHUNKS = new HashMap<>();
    private static final Queue<ChunkPos> REBUILD_QUEUE = new ArrayDeque<>();
    private static final Set<ChunkPos> QUEUED = new HashSet<>();

    public static void renderFoam() {
        ClientChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
            ChunkPos pos = chunk.getPos();
            FoamChunk foamChunk = new FoamChunk(pos);
            CHUNKS.put(pos, foamChunk);
            markNeighborsDirty(pos);
        });

        ClientChunkEvents.CHUNK_UNLOAD.register((world, chunk) -> {
            ChunkPos pos = chunk.getPos();
            CHUNKS.remove(pos);
            REBUILD_QUEUE.remove(pos);
            QUEUED.remove(pos);
        });

        ClientTickEvents.END_CLIENT_TICK.register(
                FoamManager::tick
        );
    }

    private static void tick(MinecraftClient client) {
        ClientWorld world = client.world;
        if (world == null) return;
        int rebuildsPerTick = 2;

        for (int i = 0; i < rebuildsPerTick; i++) {
            ChunkPos pos = REBUILD_QUEUE.poll();
            if (pos == null) break;

            QUEUED.remove(pos);
            FoamChunk chunk = CHUNKS.get(pos);

            if (chunk == null) continue;

            FoamMeshBuilder.rebuild(world, chunk);

            chunk.dirty = false;
        }
    }

    public static void markDirty(ChunkPos pos) {
        FoamChunk chunk = CHUNKS.get(pos);
        if (chunk == null) return;
        chunk.dirty = true;

        if (QUEUED.add(pos)) REBUILD_QUEUE.add(pos);
    }

    public static void markNeighborsDirty(ChunkPos pos) {
        markDirty(pos);
        markDirty(new ChunkPos(pos.x + 1, pos.z));
        markDirty(new ChunkPos(pos.x - 1, pos.z));
        markDirty(new ChunkPos(pos.x, pos.z + 1));
        markDirty(new ChunkPos(pos.x, pos.z - 1));
    }
}