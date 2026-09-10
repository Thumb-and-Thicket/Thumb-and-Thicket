package net.jolene.thumbandthicket.entity.client;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.entity.custom.DeerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DeerRenderer
        extends MobEntityRenderer<
        DeerEntity,
        DeerModel<DeerEntity>
        > {

    private static final Identifier DEER_1 =
            Identifier.of(
                    ThumbAndThicket.MOD_ID,
                    "textures/entity/deer/deer_1.png"
            );

    private static final Identifier DEER_2 =
            Identifier.of(
                    ThumbAndThicket.MOD_ID,
                    "textures/entity/deer/deer_2.png"
            );

    private static final Identifier DEER_COLD =
            Identifier.of(
                    ThumbAndThicket.MOD_ID,
                    "textures/entity/deer/deer_cold.png"
            );

    private static final Identifier DEER_WARM =
            Identifier.of(
                    ThumbAndThicket.MOD_ID,
                    "textures/entity/deer/deer_warm.png"
            );

    public DeerRenderer(
            EntityRendererFactory.Context context
    ) {
        super(
                context,
                new DeerModel<>(
                        context.getPart(
                                DeerModel.DEER
                        )
                ),
                0.25f
        );
    }

    @Override
    public Identifier getTexture(
            DeerEntity entity
    ) {

        return switch (entity.getDeerVariant()) {

            case DeerEntity.NORMAL_2 ->
                    DEER_2;

            case DeerEntity.COLD ->
                    DEER_COLD;

            case DeerEntity.WARM ->
                    DEER_WARM;

            case DeerEntity.NORMAL_1 ->
                    DEER_1;

            default ->
                    DEER_1;
        };
    }

    @Override
    public void render(
            DeerEntity entity,
            float entityYaw,
            float tickDelta,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light
    ) {

        matrices.push();

        if (entity.isBaby()) {
            matrices.scale(
                    0.5F,
                    0.5F,
                    0.5F
            );
        }

        super.render(
                entity,
                entityYaw,
                tickDelta,
                matrices,
                vertexConsumers,
                light
        );

        matrices.pop();
    }
}