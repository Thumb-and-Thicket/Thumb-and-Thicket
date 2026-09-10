package net.jolene.thumbandthicket.entity.client;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.entity.custom.DeerEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class DeerModel<T extends DeerEntity> extends SinglePartEntityModel<T> {

    public static final EntityModelLayer DEER =
            new EntityModelLayer(
                    Identifier.of(
                            ThumbAndThicket.MOD_ID,
                            "deer"
                    ),
                    "main"
            );

    private final ModelPart root;

    private final ModelPart front_left_leg;
    private final ModelPart front_right_leg;
    private final ModelPart back_right_leg;
    private final ModelPart back_left_leg;

    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart head;
    private final ModelPart antlers;
    private final ModelPart left_ear;
    private final ModelPart right_ear;

    public DeerModel(ModelPart root) {

        this.root = root.getChild("root");

        this.front_left_leg =
                this.root.getChild("front_left_leg");

        this.front_right_leg =
                this.root.getChild("front_right_leg");

        this.back_right_leg =
                this.root.getChild("back_right_leg");

        this.back_left_leg =
                this.root.getChild("back_left_leg");

        this.body =
                this.root.getChild("body");

        this.tail =
                this.body.getChild("tail");

        this.head =
                this.body.getChild("head");

        this.antlers =
                this.head.getChild("antlers");

        this.left_ear =
                this.head.getChild("left_ear");

        this.right_ear =
                this.head.getChild("right_ear");
    }

    public static TexturedModelData getTexturedModelData() {

        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData root = modelPartData.addChild(
                "root",
                ModelPartBuilder.create(),
                ModelTransform.pivot(
                        0.0F,
                        24.0F,
                        0.0F
                )
        );

        root.addChild(
                "front_left_leg",
                ModelPartBuilder.create()
                        .uv(44, 41)
                        .cuboid(
                                -1.5F,
                                0.0F,
                                -1.5F,
                                3.0F,
                                12.0F,
                                3.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.pivot(
                        2.5F,
                        -12.0F,
                        5.5F
                )
        );

        root.addChild(
                "front_right_leg",
                ModelPartBuilder.create()
                        .uv(0, 44)
                        .cuboid(
                                -1.5F,
                                0.0F,
                                -1.5F,
                                3.0F,
                                12.0F,
                                3.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.pivot(
                        -2.5F,
                        -12.0F,
                        5.5F
                )
        );

        root.addChild(
                "back_right_leg",
                ModelPartBuilder.create()
                        .uv(20, 41)
                        .cuboid(
                                -1.5F,
                                0.0F,
                                -1.5F,
                                3.0F,
                                12.0F,
                                3.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.pivot(
                        -2.5F,
                        -12.0F,
                        -5.5F
                )
        );

        root.addChild(
                "back_left_leg",
                ModelPartBuilder.create()
                        .uv(32, 41)
                        .cuboid(
                                -1.5F,
                                0.0F,
                                -1.5F,
                                3.0F,
                                12.0F,
                                3.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.pivot(
                        2.5F,
                        -12.0F,
                        -5.5F
                )
        );

        ModelPartData body = root.addChild(
                "body",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(
                                -4.0F,
                                -12.0F,
                                -9.0F,
                                8.0F,
                                12.0F,
                                15.0F,
                                new Dilation(0.001F)
                        ),
                ModelTransform.pivot(
                        0.0F,
                        -12.0F,
                        0.0F
                )
        );

        body.addChild(
                "tail",
                ModelPartBuilder.create(),
                ModelTransform.pivot(
                        0.0F,
                        -12.0F,
                        7.0F
                )
        );

        ModelPartData head = body.addChild(
                "head",
                ModelPartBuilder.create()
                        .uv(0, 27)
                        .cuboid(
                                -2.5F,
                                -9.0F,
                                -3.0F,
                                5.0F,
                                12.0F,
                                5.0F,
                                new Dilation(-0.001F)
                        )
                        .uv(46, 0)
                        .cuboid(
                                -1.5F,
                                -7.0F,
                                -6.0F,
                                3.0F,
                                3.0F,
                                3.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.pivot(
                        0.0F,
                        -11.0F,
                        -8.0F
                )
        );

        ModelPartData antlers = head.addChild(
                "antlers",
                ModelPartBuilder.create(),
                ModelTransform.pivot(
                        0.0F,
                        -9.0F,
                        -1.9F
                )
        );

        antlers.addChild(
                "cube_r1",
                ModelPartBuilder.create()
                        .uv(20, 27)
                        .cuboid(
                                0.0F,
                                -6.0F,
                                -1.0F,
                                0.0F,
                                6.0F,
                                8.0F,
                                new Dilation(0.01F)
                        ),
                ModelTransform.of(
                        -2.5F,
                        0.0F,
                        -0.1F,
                        0.0F,
                        -0.3927F,
                        0.0F
                )
        );

        antlers.addChild(
                "cube_r2",
                ModelPartBuilder.create()
                        .uv(36, 27)
                        .cuboid(
                                0.0F,
                                -6.0F,
                                -1.0F,
                                0.0F,
                                6.0F,
                                8.0F,
                                new Dilation(0.01F)
                        ),
                ModelTransform.of(
                        2.5F,
                        0.0F,
                        -0.1F,
                        0.0F,
                        0.3927F,
                        0.0F
                )
        );

        head.addChild(
                "left_ear",
                ModelPartBuilder.create()
                        .uv(12, 44)
                        .cuboid(
                                0.0F,
                                -2.5F,
                                -0.5F,
                                0.0F,
                                3.0F,
                                3.0F,
                                new Dilation(0.01F)
                        ),
                ModelTransform.of(
                        2.6F,
                        -8.5F,
                        0.5F,
                        0.0F,
                        0.3927F,
                        0.0F
                )
        );

        head.addChild(
                "right_ear",
                ModelPartBuilder.create()
                        .uv(46, 6)
                        .cuboid(
                                0.0F,
                                -2.5F,
                                -0.5F,
                                0.0F,
                                3.0F,
                                3.0F,
                                new Dilation(0.01F)
                        ),
                ModelTransform.of(
                        -2.6F,
                        -8.5F,
                        0.5F,
                        0.0F,
                        -0.3927F,
                        0.0F
                )
        );

        return TexturedModelData.of(
                modelData,
                64,
                64
        );
    }

    @Override
    public void setAngles(
            T entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {

        /*
         * Reset to the model's default pose.
         */
        this.getPart()
                .traverse()
                .forEach(ModelPart::resetTransform);

        /*
         * --------------------------------------------------
         * BASE IDLE ANIMATION
         * --------------------------------------------------
         *
         * Keep idle underneath the other animations rather
         * than completely removing it.
         *
         * This prevents the harsh idle -> behavioural
         * animation snapping caused by the previous returns.
         */
        if (!entity.isPanicking()) {

            this.updateAnimation(
                    entity.idleAnimationState,
                    DeerAnimations.idle,
                    ageInTicks,
                    1.0F
            );
        }

        /*
         * --------------------------------------------------
         * WALKING
         * --------------------------------------------------
         *
         * Only play normal walking when the deer isn't
         * performing a special behavioural animation.
         */
        if (!entity.isPanicking()
                && !entity.isStartled()
                && !entity.isStaring()) {

            this.animateMovement(
                    DeerAnimations.walk,
                    limbSwing,
                    limbSwingAmount,
                    3.5F,
                    3.5F
            );
        }

        /*
         * --------------------------------------------------
         * STARTLE
         * --------------------------------------------------
         *
         * This is layered after idle rather than replacing
         * everything with an early return.
         */
        if (entity.isStartled()) {

            this.updateAnimation(
                    entity.startleAnimationState,
                    DeerAnimations.startle,
                    ageInTicks,
                    1.0F
            );
        }

        /*
         * --------------------------------------------------
         * STARE
         * --------------------------------------------------
         */
        if (entity.isStaring()) {

            this.updateAnimation(
                    entity.stareAnimationState,
                    DeerAnimations.stare,
                    ageInTicks,
                    1.0F
            );
        }

        /*
         * --------------------------------------------------
         * PANICKING / RUNNING
         * --------------------------------------------------
         *
         * The animation speed has been halved again.
         *
         * Original: 3.0F
         * Previous half: 1.5F
         * Half again: 0.75F
         */
        if (entity.isPanicking()) {

            this.animateMovement(
                    DeerAnimations.run,
                    limbSwing,
                    limbSwingAmount,
                    1.5F,
                    3.0F
            );
        }

        /*
         * Head movement is applied last so the deer can
         * still naturally look around during its animations.
         *
         * Don't apply this while startled, since the startle
         * animation should have complete control over the pose.
         */
        if (!entity.isStartled()) {

            this.setHeadAngles(
                    netHeadYaw,
                    headPitch
            );
        }
    }

    private void setHeadAngles(
            float headYaw,
            float headPitch
    ) {

        headYaw = MathHelper.clamp(
                headYaw,
                -45.0F,
                45.0F
        );

        headPitch = MathHelper.clamp(
                headPitch,
                -67.5F,
                35.0F
        );

        this.head.yaw =
                headYaw * 0.017453292F;

        this.head.pitch =
                headPitch * 0.017453292F;
    }


    public void render(MatrixStack matrices, VertexConsumer vertexConsumer,
                       int light, int overlay, float red, float green,
                       float blue, float alpha) {
        root.render(matrices, vertexConsumer, light, overlay);
    }


    @Override
    public ModelPart getPart() {
        return root;
    }
}