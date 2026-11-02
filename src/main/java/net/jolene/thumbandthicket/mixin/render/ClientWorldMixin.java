package net.jolene.thumbandthicket.mixin.render;

import net.jolene.thumbandthicket.util.render.foam.FoamManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Inject(method = "handleBlockUpdate", at = @At("TAIL"))
    private void thumbandthicket$foamUpdate(BlockPos pos, BlockState state, int flags, CallbackInfo ci) {
        ChunkPos chunkPos = new ChunkPos(pos);
        FoamManager.markDirty(chunkPos);

        if ((pos.getX() & 15) == 0) FoamManager.markDirty(new ChunkPos(chunkPos.x - 1, chunkPos.z));
        if ((pos.getX() & 15) == 15) FoamManager.markDirty(new ChunkPos(chunkPos.x + 1, chunkPos.z));
        if ((pos.getZ() & 15) == 0) FoamManager.markDirty(new ChunkPos(chunkPos.x, chunkPos.z - 1));
        if ((pos.getZ() & 15) == 15) FoamManager.markDirty(new ChunkPos(chunkPos.x, chunkPos.z + 1));
    }
}