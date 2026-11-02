package net.jolene.thumbandthicket.mixin.snowy;

import com.blackgear.vanillabackport.common.level.blocks.LeafLitterBlock;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BucketItem.class)
public class BucketItemMixin {

    @WrapMethod(method = "placeFluid")
    private boolean thumbandthicket$dontPlaceIfSpecificBlock(PlayerEntity player, World world, BlockPos pos, BlockHitResult hitResult, Operation<Boolean> original) {
        BlockState state = world.getBlockState(pos);
//        if (state.getBlock() instanceof Snowable && state.get(ModProperties.LAYERS) > 0) return false;
        if (state.getBlock() instanceof LeafLitterBlock) return false;
        return original.call(player, world, pos, hitResult);
    }
}
