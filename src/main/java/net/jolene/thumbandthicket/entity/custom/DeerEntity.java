package net.jolene.thumbandthicket.entity.custom;

import net.jolene.thumbandthicket.entity.ModEntities;
import net.jolene.thumbandthicket.entity.custom.goals.DeerLeadGoal;
import net.jolene.thumbandthicket.entity.custom.goals.DeerStartleGoal;
import net.jolene.thumbandthicket.entity.custom.goals.DeerTransfixedGoal;
import net.jolene.thumbandthicket.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DeerEntity extends AnimalEntity {

    public static final int NORMAL_1 = 0;
    public static final int NORMAL_2 = 1;
    public static final int COLD = 2;
    public static final int WARM = 3;

    private static final int VARIANT_UNSET = -1;

    private static final TrackedData<Boolean> PANICKING =
            DataTracker.registerData(
                    DeerEntity.class,
                    TrackedDataHandlerRegistry.BOOLEAN
            );

    private static final TrackedData<Boolean> STARTLED =
            DataTracker.registerData(
                    DeerEntity.class,
                    TrackedDataHandlerRegistry.BOOLEAN
            );

    private static final TrackedData<Boolean> STARING =
            DataTracker.registerData(
                    DeerEntity.class,
                    TrackedDataHandlerRegistry.BOOLEAN
            );

    private static final TrackedData<Boolean> TRANSFIXED =
            DataTracker.registerData(
                    DeerEntity.class,
                    TrackedDataHandlerRegistry.BOOLEAN
            );

    private static final TrackedData<Boolean> STARTLE_REQUESTED =
            DataTracker.registerData(
                    DeerEntity.class,
                    TrackedDataHandlerRegistry.BOOLEAN
            );

    private static final TrackedData<Integer> DEER_VARIANT =
            DataTracker.registerData(
                    DeerEntity.class,
                    TrackedDataHandlerRegistry.INTEGER
            );

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState startleAnimationState = new AnimationState();
    public final AnimationState stareAnimationState = new AnimationState();

    private int lightCooldown;

    public DeerEntity(
            EntityType<? extends AnimalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker(
            DataTracker.Builder builder
    ) {
        super.initDataTracker(builder);

        builder.add(PANICKING, false);
        builder.add(STARTLED, false);
        builder.add(STARING, false);
        builder.add(TRANSFIXED, false);
        builder.add(STARTLE_REQUESTED, false);
        builder.add(DEER_VARIANT, VARIANT_UNSET);
    }

    @Override
    protected void initGoals() {

        this.goalSelector.add(
                0,
                new SwimGoal(this)
        );

        this.goalSelector.add(
                1,
                new DeerLeadGoal(this)
        );

        this.goalSelector.add(
                2,
                new DeerTransfixedGoal(this)
        );

        this.goalSelector.add(
                3,
                new DeerStartleGoal(this)
        );

        this.goalSelector.add(
                4,
                new WanderAroundFarGoal(
                        this,
                        0.5D
                )
        );

        this.goalSelector.add(
                5,
                new LookAtEntityGoal(
                        this,
                        PlayerEntity.class,
                        8.0F
                )
        );

        this.goalSelector.add(
                6,
                new LookAroundGoal(this)
        );
    }

    public static DefaultAttributeContainer.Builder createAttributes() {

        return MobEntity.createMobAttributes()
                .add(
                        EntityAttributes.GENERIC_MAX_HEALTH,
                        10.0D
                )
                .add(
                        EntityAttributes.GENERIC_MOVEMENT_SPEED,
                        0.3D
                )
                .add(
                        EntityAttributes.GENERIC_FOLLOW_RANGE,
                        24.0D
                );
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.DEER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(
            DamageSource source
    ) {
        return ModSounds.DEER_STARTLE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DEER_DEATH;
    }

    @Override
    protected void playStepSound(
            BlockPos pos,
            BlockState state
    ) {
        this.playSound(
                ModSounds.DEER_STEP,
                0.15F,
                1.0F
        );
    }

    @Override
    public boolean isBreedingItem(
            ItemStack stack
    ) {
        return stack.isOf(Items.SWEET_BERRIES);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(
            ServerWorld world,
            PassiveEntity entity
    ) {
        return ModEntities.DEER.create(world);
    }

    @Override
    public void tick() {

        super.tick();

        if (!this.getWorld().isClient()) {

            if (this.lightCooldown > 0) {
                this.lightCooldown--;
            }

            if (this.getDeerVariant() == VARIANT_UNSET) {
                this.chooseDeerVariant();
            }

            // PARTICLES ONLY EXIST DURING THE ACTUAL TRANCE.
            if (this.isTransfixed()
                    && this.age % 5 == 0
                    && this.getWorld() instanceof ServerWorld world) {

                world.spawnParticles(
                        ParticleTypes.END_ROD,
                        this.getX(),
                        this.getY() + this.getHeight() * 0.75D,
                        this.getZ(),
                        2,
                        0.3D,
                        0.35D,
                        0.3D,
                        0.01D
                );
            }

        } else {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {

        if (this.isStartled()) {
            this.startleAnimationState.startIfNotRunning(this.age);
        } else {
            this.startleAnimationState.stop();
        }

        if (this.isStaring()) {
            this.stareAnimationState.startIfNotRunning(this.age);
        } else {
            this.stareAnimationState.stop();
        }

        if (!this.isPanicking()
                && !this.isStartled()
                && !this.isStaring()) {

            this.idleAnimationState.startIfNotRunning(this.age);

        } else {
            this.idleAnimationState.stop();
        }
    }

    @Override
    public boolean damage(
            DamageSource source,
            float amount
    ) {

        boolean damaged = super.damage(
                source,
                amount
        );

        if (damaged) {

            this.setTransfixed(false);
            this.setStaring(false);
            this.setStartleRequested(true);
        }

        return damaged;
    }

    private void chooseDeerVariant() {

        if (this.getWorld().isClient()) {
            return;
        }

        BlockPos pos = this.getBlockPos();

        var biome = this.getWorld()
                .getBiome(pos)
                .value();

        if (biome.isCold(pos)) {
            this.setDeerVariant(COLD);
            return;
        }

        if (biome.getTemperature() >= 1.0F) {
            this.setDeerVariant(WARM);
            return;
        }

        this.setDeerVariant(
                this.random.nextBoolean()
                        ? NORMAL_1
                        : NORMAL_2
        );
    }

    @Override
    public void writeCustomDataToNbt(
            NbtCompound nbt
    ) {
        super.writeCustomDataToNbt(nbt);

        if (this.getDeerVariant() != VARIANT_UNSET) {
            nbt.putInt(
                    "DeerVariant",
                    this.getDeerVariant()
            );
        }

        if (this.lightCooldown > 0) {
            nbt.putInt(
                    "LightCooldown",
                    this.lightCooldown
            );
        }
    }

    @Override
    public void readCustomDataFromNbt(
            NbtCompound nbt
    ) {
        super.readCustomDataFromNbt(nbt);

        this.setDeerVariant(
                nbt.contains(
                        "DeerVariant",
                        NbtElement.INT_TYPE
                )
                        ? nbt.getInt("DeerVariant")
                        : VARIANT_UNSET
        );

        this.lightCooldown =
                nbt.contains(
                        "LightCooldown",
                        NbtElement.INT_TYPE
                )
                        ? nbt.getInt("LightCooldown")
                        : 0;
    }

    public int getDeerVariant() {
        return this.dataTracker.get(DEER_VARIANT);
    }

    public void setDeerVariant(int variant) {
        this.dataTracker.set(
                DEER_VARIANT,
                variant
        );
    }

    public boolean isPanicking() {
        return this.dataTracker.get(PANICKING);
    }

    public void setPanicking(boolean value) {
        this.dataTracker.set(
                PANICKING,
                value
        );
    }

    public boolean isStartled() {
        return this.dataTracker.get(STARTLED);
    }

    public void setStartled(boolean value) {
        this.dataTracker.set(
                STARTLED,
                value
        );
    }

    public boolean isStaring() {
        return this.dataTracker.get(STARING);
    }

    public void setStaring(boolean value) {
        this.dataTracker.set(
                STARING,
                value
        );
    }

    public boolean isTransfixed() {
        return this.dataTracker.get(TRANSFIXED);
    }

    public void setTransfixed(boolean value) {
        this.dataTracker.set(
                TRANSFIXED,
                value
        );
    }

    public boolean hasStartleRequest() {
        return this.dataTracker.get(STARTLE_REQUESTED);
    }

    public void setStartleRequested(boolean value) {
        this.dataTracker.set(
                STARTLE_REQUESTED,
                value
        );
    }

    public boolean isLightOnCooldown() {
        return this.lightCooldown > 0;
    }

    public void startLightCooldown() {
        this.lightCooldown = 1200;
    }

    public boolean isPlayerHoldingFood(
            PlayerEntity player
    ) {
        return this.isBreedingItem(
                player.getMainHandStack()
        ) || this.isBreedingItem(
                player.getOffHandStack()
        );
    }
}