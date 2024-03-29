package net.teamabyssalofficial.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.entity.ai.FloatDiveGoal;
import net.teamabyssalofficial.entity.categories.Evolving;
import net.teamabyssalofficial.entity.categories.Parasite;
import net.teamabyssalofficial.registry.EntityRegistry;
import net.teamabyssalofficial.registry.WorldDataRegistry;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ShillerEntity extends Parasite implements GeoEntity, Evolving {
    public static final EntityDataAccessor<Integer> POINTS = SynchedEntityData.defineId(ShillerEntity.class, EntityDataSerializers.INT);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);



    public ShillerEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Nullable
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.MAX_HEALTH, FightOrDieMutationsConfig.SERVER.shiller_health.get())
                .add(Attributes.ARMOR, 2.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatDiveGoal(this));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new AvoidEntityGoal(this, LivingEntity.class, 10.0F, 0.7F, 0.7F));
    }

    public void setPoints(Integer count) {
        entityData.set(POINTS, count);
    }

    public int getPoints() {
        return entityData.get(POINTS);
    }


    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("points", entityData.get(POINTS));
    }


    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        entityData.set(POINTS, tag.getInt("points"));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(POINTS, 0);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerD) {
        controllerD.add(
                new AnimationController<>(this, "controllerRegistrar", 7, event -> {
                    event.getController().setAnimationSpeed(1.0D);
                    if (!event.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("shiller_idle"));
                    }
                    else if (event.isMoving()) {
                        event.getController().setAnimationSpeed(1.8D);
                        return event.setAndContinue(RawAnimation.begin().thenLoop("shiller_walk"));
                    }
                    return PlayState.CONTINUE;
                }));

    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            this.setPoints(this.getPoints() + 1);
        }
        if (this.isAlive() && this.getPoints() == 1200) {
            this.level().playSound((Player) null, this.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.0F, 1.0F);
            if (this.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY() + 1, this.getZ(), 5, 0.4, 1.0, 0.4, 0);
                WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(server);
                int currentScore = worldDataRegistry.getScore();
                worldDataRegistry.setScore(currentScore + 2);
            }


            this.discard();
            this.EvolveIntoMalruptor(this);

        }


        super.tick();
    }
    private void EvolveIntoMalruptor(Entity entity) {
        MalruptorEntity malruptorEntity = new MalruptorEntity(EntityRegistry.MALRUPTOR.get(), entity.level());
        malruptorEntity.moveTo(entity.getX(),entity.getY(),entity.getZ());
        entity.level().addFreshEntity(malruptorEntity);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SoundEvents.GENERIC_HURT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.GENERIC_DEATH;
    }


}
