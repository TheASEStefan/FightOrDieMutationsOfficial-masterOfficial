package net.teamabyssalofficial.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.entity.ai.CustomMeleeAttackGoal;
import net.teamabyssalofficial.entity.categories.Mutated;
import net.teamabyssalofficial.extra.ScreenShakeEntity;
import net.teamabyssalofficial.registry.EffectRegistry;
import net.teamabyssalofficial.registry.EntityRegistry;
import net.teamabyssalofficial.registry.ParticleRegistry;
import net.teamabyssalofficial.registry.SoundRegistry;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class AssimilatedPigEntity extends Mutated implements GeoEntity {


    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public AssimilatedPigEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new CustomMeleeAttackGoal(this, 1.5, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return 2.0 + entity.getBbWidth() * entity.getBbWidth();
            }
        });
    }

    @Nullable
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.FOLLOW_RANGE, 32D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.MAX_HEALTH, FightOrDieMutationsConfig.SERVER.mutated_pig_health.get())
                .add(Attributes.ATTACK_DAMAGE, FightOrDieMutationsConfig.SERVER.mutated_pig_damage.get())
                .add(Attributes.ARMOR, 2D);

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerOm) {
        controllerOm.add(
                new AnimationController<>(this, "controllerOP", 7, event -> {
                    if (event.isMoving() && !this.isAggressive()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_pig_walk"));
                    }
                    if (event.isMoving() && this.isAggressive()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_pig_target"));
                    }
                    if (this.isDeadOrDying()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("assimilated_pig_death"));
                    }
                    return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_pig_idle"));
                }));

    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private void spawnLingeringCloud() {
        AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
        cloud.setRadius(1.5F);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(6);
        cloud.setDuration(Mth.floor((((double) cloud.getDuration() / 3) * 1.2)));
        cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());
        cloud.addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 2400, 1));

        this.level().addFreshEntity(cloud);
    }

    @Override
    public void die(DamageSource source) {
        /* if (Math.random() <= 0.25F) {
            //this.DropPigHead(this);
        }
         */

        if (Math.random() <= 0.35F) {
            AABB boundingBox = this.getBoundingBox().inflate(4);
            List<Entity> entities = this.level().getEntities(this, boundingBox);
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity livingEntity && !(EntityRegistry.PARASITES.contains(entity))) {
                    if (!livingEntity.hasEffect(MobEffects.POISON)) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0), livingEntity);
                        livingEntity.addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 1200, 0), livingEntity);
                        livingEntity.level().playSound((Player) null, livingEntity.blockPosition(), SoundRegistry.ENTITY_EXPLOSION.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
                        ScreenShakeEntity.ScreenShake(level(), position(), 8, 0.1f, 3, 10);
                        this.spawnLingeringCloud();
                        if (this.level() instanceof ServerLevel server) {
                            server.sendParticles(ParticleRegistry.POISON_PUFF.get(), this.getX(), this.getY() + 1, this.getZ(), 65, 0.2, 0.8, 0.4, 0.15);
                        }
                    }
                }
            }
        }
        else if (Math.random() <= 0.15F) {
            AABB boundingBox = this.getBoundingBox().inflate(4);
            List<Entity> entities = this.level().getEntities(this, boundingBox);
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity livingEntity && !(EntityRegistry.PARASITES.contains(entity))) {
                    if (!livingEntity.hasEffect(MobEffects.POISON)) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0), livingEntity);
                        livingEntity.addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 1200, 0), livingEntity);
                        livingEntity.level().playSound((Player) null, livingEntity.blockPosition(), SoundRegistry.ENTITY_EXPLOSION.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
                        this.ShillerExplosion(this);
                        this.ShillerExplosion(this);
                        this.ShillerExplosion(this);
                        ScreenShakeEntity.ScreenShake(level(), position(), 8, 0.1f, 3, 10);
                        this.spawnLingeringCloud();
                        if (this.level() instanceof ServerLevel server) {
                            server.sendParticles(ParticleRegistry.POISON_PUFF.get(), this.getX(), this.getY() + 1, this.getZ(), 65, 0.2, 0.8, 0.4, 0.15);
                        }
                    }
                }
            }
        }
        super.die(source);
    }

    private void ShillerExplosion(Entity entity) {
        ShillerEntity shillerEntity = new ShillerEntity(EntityRegistry.SHILLER.get(), entity.level());
        shillerEntity.moveTo(entity.getX(),entity.getY(),entity.getZ());
        entity.level().addFreshEntity(shillerEntity);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.ENTITY_ASSIMILATED_PIG_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundRegistry.ENTITY_ASSIMILATED_HURT.get();
    }


    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.ASSIMILATED_ANIMAL_AMBIENT.get();
    }


    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        super.playStepSound(pos, blockIn);
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.5F, 1.0F);
    }


    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        Entity entity = pSource.getEntity();
    }
}
