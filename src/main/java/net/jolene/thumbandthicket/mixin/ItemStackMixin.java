package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.datagen.ModItemTagProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import static net.jolene.thumbandthicket.util.ModProperties.FERTILIZED;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @WrapMethod(method = "useOnBlock")
    private ActionResult progressionrespun$fertilizeFarmland(ItemUsageContext context, Operation<ActionResult> original) {
        ItemStack stack = (ItemStack) (Object) this;
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();

        if (stack.isIn(ModItemTagProvider.CAN_FERTILIZE_FARMLAND) && state.getBlock() instanceof FarmlandBlock && !state.get(FERTILIZED)) {
            if (!world.isClient && player != null) {
                world.setBlockState(pos, state.with(FERTILIZED, true));
                stack.decrementUnlessCreative(1, player);
                player.playSound(SoundEvents.BLOCK_ROOTED_DIRT_HIT, 0.8f, 0.8f + world.getRandom().nextFloat() * 0.4f);
            }
            return ActionResult.SUCCESS;
        }
        return original.call(context);
    }
}
