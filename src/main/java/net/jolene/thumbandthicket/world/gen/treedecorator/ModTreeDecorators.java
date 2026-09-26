package net.jolene.thumbandthicket.world.gen.treedecorator;

import com.mojang.serialization.MapCodec;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class ModTreeDecorators {
    public static final TreeDecoratorType<WallMushroomDecorator> WALL_MUSHROOM_DECORATOR_TREE_DECORATOR_TYPE = register("wall_mushroom", WallMushroomDecorator.CODEC);

    private static <P extends TreeDecorator> TreeDecoratorType<P> register(String name, MapCodec<P> codec) {
        return Registry.register(Registries.TREE_DECORATOR_TYPE, Identifier.of(ThumbAndThicket.MOD_ID, name), new TreeDecoratorType<P>(codec));
    }

    public static void initialize() {

    }
}
