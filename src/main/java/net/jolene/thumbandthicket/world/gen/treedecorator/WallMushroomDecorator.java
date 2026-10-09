package net.jolene.thumbandthicket.world.gen.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class WallMushroomDecorator extends TreeDecorator {
    public static final MapCodec<WallMushroomDecorator> CODEC = RecordCodecBuilder.mapCodec(wallMushroomDecoratorInstance -> wallMushroomDecoratorInstance.group(Codec.FLOAT.fieldOf("probability").forGetter(mod -> mod.probability), BlockStateProvider.TYPE_CODEC.fieldOf("state_provider").forGetter(mod -> mod.stateProvider)).apply(wallMushroomDecoratorInstance, WallMushroomDecorator::new));
    private final float probability;
    private final BlockStateProvider stateProvider;

    public WallMushroomDecorator (float probability, BlockStateProvider stateProvider) {
        this.probability = probability;
        this.stateProvider = stateProvider;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return ModTreeDecorators.WALL_MUSHROOM_DECORATOR_TREE_DECORATOR_TYPE;
    }

    @Override
    public void generate(Generator generator) {
        Random random = generator.getRandom();
        generator.getLogPositions().forEach(pos -> {
            if (random.nextFloat() <= probability) {
                Direction direction = Direction.Type.HORIZONTAL.random(random);
                BlockPos targetPos = pos.offset(direction);
                if (generator.isAir(targetPos)) {
                    int amount = random.nextBetween(1, 4);
                    BlockState state = stateProvider.get(random, targetPos).with(ModProperties.AMOUNT, amount).with(Properties.FACING, direction.getOpposite()).with(Properties.BLOCK_FACE, BlockFace.WALL);
                    generator.replace(targetPos, state);
                }
            }
        });
    }
}
