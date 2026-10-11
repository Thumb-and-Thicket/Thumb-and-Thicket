package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jolene.thumbandthicket.block.*;
import net.jolene.thumbandthicket.block.FungusStemBlock;
import net.jolene.thumbandthicket.util.Soakable;
import net.minecraft.block.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ColorCode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Blocks.class)
public class BlocksMixin {

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;", ordinal = 58))
    private static Block thumbandthicket$changeMelonConstructor(AbstractBlock.Settings settings, Operation<Block> original) {
        return new MelonBlock(settings);
    }

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/util/ColorCode;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/ColoredFallingBlock;", ordinal = 0))
    private static ColoredFallingBlock thumbandthicket$changeSandConstructor(ColorCode color, AbstractBlock.Settings settings, Operation<ColoredFallingBlock> original) {
        return new WetColoredFallingBlock(color, settings, Soakable.WetnessLevel.DRY, 2);
    }

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/block/Block;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/BrushableBlock;", ordinal = 0))
    private static BrushableBlock thumbandthicket$changeSuspiciousSandConstructor(Block baseBlock, SoundEvent brushingSound, SoundEvent brushingCompleteSound, AbstractBlock.Settings settings, Operation<BrushableBlock> original) {
        return new BrushableWetBlock(baseBlock, brushingSound, brushingCompleteSound, settings, Soakable.WetnessLevel.DRY);
    }

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/util/ColorCode;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/ColoredFallingBlock;", ordinal = 1))
    private static ColoredFallingBlock thumbandthicket$changeRedSandConstructor(ColorCode color, AbstractBlock.Settings settings, Operation<ColoredFallingBlock> original) {
        return new WetColoredFallingBlock(color, settings, Soakable.WetnessLevel.DRY, 2);
    }

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/MushroomPlantBlock;"))
    private static MushroomPlantBlock thumbandthicket$changeMushroomConstructor(RegistryKey featureKey, AbstractBlock.Settings settings, Operation<MushroomPlantBlock> original) {
        return new ClusterMushroomPlantBlock(featureKey, settings);
    }

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/block/Block;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/FungusBlock;"))
    private static FungusBlock thumbandthicket$changeMushroomConstructor(RegistryKey featureKey, Block nylium, AbstractBlock.Settings settings, Operation<FungusBlock> original) {
        return new ClusterFungusPlantBlock(featureKey, nylium, settings);
    }

    @WrapOperation(method = "createLogBlock(Lnet/minecraft/block/MapColor;Lnet/minecraft/block/MapColor;)Lnet/minecraft/block/Block;", at = @At(value = "NEW", target = "(Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/PillarBlock;"))
    private static PillarBlock thumbandthicket$changeLogConstructor(AbstractBlock.Settings settings, Operation<PillarBlock> original) {
        return new LogBlock(settings);
    }

    @WrapOperation(method = "createLogBlock(Lnet/minecraft/block/MapColor;Lnet/minecraft/block/MapColor;Lnet/minecraft/sound/BlockSoundGroup;)Lnet/minecraft/block/Block;", at = @At(value = "NEW", target = "(Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/PillarBlock;"))
    private static PillarBlock thumbandthicket$changeCherryLogConstructor(AbstractBlock.Settings settings, Operation<PillarBlock> original) {
        return new LogBlock(settings);
    }

    @WrapOperation(method = "createNetherStemBlock", at = @At(value = "NEW", target = "(Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/PillarBlock;"))
    private static PillarBlock thumbandthicket$changeStemConstructor(AbstractBlock.Settings settings, Operation<PillarBlock> original) {
        return new FungusStemBlock(settings);
    }

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/block/SaplingGenerator;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/SaplingBlock;"))
    private static SaplingBlock thumbandthicket$changeSaplingConstructor(SaplingGenerator generator, AbstractBlock.Settings settings, Operation<SaplingBlock> original) {
        return new ExtendedSaplingBlock(generator, settings);
    }
}
