package net.jolene.thumbandthicket.util.render.foam;

import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;
import java.util.List;

public class FoamChunk {

    public final ChunkPos pos;
    public boolean dirty = true;
    public final List<FoamQuad> quads = new ArrayList<>();

    public FoamChunk(ChunkPos pos) {
        this.pos = pos;
    }
}