package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jolene.thumbandthicket.block.BrushableWetBlock;
import net.jolene.thumbandthicket.block.MelonBlock;
import net.jolene.thumbandthicket.block.WetColoredFallingBlock;
import net.jolene.thumbandthicket.util.Soakable;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ColorCode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Blocks.class)
public class BlocksMixin {

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;", ordinal = 58))
    private static Block thumbandthicket$changeMelonConstructor(AbstractBlock.Settings settings, Operation<Block> original) {
        return new MelonBlock(AbstractBlock.Settings.create().mapColor(MapColor.LIME).strength(1.0f).sounds(BlockSoundGroup.WOOD).pistonBehavior(PistonBehavior.DESTROY));
    }

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/util/ColorCode;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/ColoredFallingBlock;", ordinal = 0))
    private static ColoredFallingBlock thumbandthicket$changeSandConstructor(ColorCode color, AbstractBlock.Settings settings, Operation<ColoredFallingBlock> original) {
        return new WetColoredFallingBlock(new ColorCode(14406560), AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.SNARE).strength(0.5f).sounds(BlockSoundGroup.SAND), Soakable.WetnessLevel.DRY, 2);
    }

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/block/Block;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/BrushableBlock;", ordinal = 0))
    private static BrushableBlock thumbandthicket$changeSuspiciousSandConstructor(Block baseBlock, SoundEvent brushingSound, SoundEvent brushingCompleteSound, AbstractBlock.Settings settings, Operation<BrushableBlock> original) {
        return new BrushableWetBlock(Blocks.SAND, SoundEvents.ITEM_BRUSH_BRUSHING_SAND, SoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE, AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.SNARE).strength(0.5f).sounds(BlockSoundGroup.SAND).pistonBehavior(PistonBehavior.DESTROY), Soakable.WetnessLevel.DRY);
    }

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/util/ColorCode;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/ColoredFallingBlock;", ordinal = 1))
    private static ColoredFallingBlock thumbandthicket$changeRedSandConstructor(ColorCode color, AbstractBlock.Settings settings, Operation<ColoredFallingBlock> original) {
        return new WetColoredFallingBlock(new ColorCode(11098145), AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.SNARE).strength(0.5f).sounds(BlockSoundGroup.SAND), Soakable.WetnessLevel.DRY, 2);
    }
}
