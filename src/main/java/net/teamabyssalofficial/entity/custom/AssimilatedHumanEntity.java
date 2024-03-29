package net.teamabyssalofficial.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
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

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class AssimilatedHumanEntity extends Mutated implements GeoEntity {

    private static final float BREAK_DOOR_CHANCE = 0.1F;
    private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (p_34284_) -> {
        return p_34284_ == Difficulty.HARD;
    };
    private final BreakDoorGoal breakDoorGoal = new BreakDoorGoal(this, DOOR_BREAKING_PREDICATE);
    private boolean canBreakDoors;

    public boolean canBreakDoors() {
        return this.canBreakDoors;
    }

    public void setCanBreakDoors(boolean pCanBreakDoors) {
        if (this.supportsBreakDoorGoal() && GoalUtils.hasGroundPathNavigation(this)) {
            if (this.canBreakDoors != pCanBreakDoors) {
                this.canBreakDoors = pCanBreakDoors;
                ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(pCanBreakDoors);
                if (pCanBreakDoors) {
                    this.goalSelector.addGoal(1, this.breakDoorGoal);
                } else {
                    this.goalSelector.removeGoal(this.breakDoorGoal);
                }
            }
        } else if (this.canBreakDoors) {
            this.goalSelector.removeGoal(this.breakDoorGoal);
            this.canBreakDoors = false;
        }

    }

    protected boolean supportsBreakDoorGoal() {
        return true;
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        float f = pDifficulty.getSpecialMultiplier() * 0.25F;
        this.setCanBreakDoors(this.supportsBreakDoorGoal() && this.getRandom().nextFloat() < f * 0.1F);
        this.handleDoorBreaking(f);

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    protected void handleDoorBreaking(float pDifficulty) {
        if (this.random.nextFloat() < pDifficulty * 0.05F)
            this.setCanBreakDoors(this.supportsBreakDoorGoal());
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public AssimilatedHumanEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
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

    Zombie zombie;


    @Nullable
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.ATTACK_KNOCKBACK, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 32D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.MAX_HEALTH, FightOrDieMutationsConfig.SERVER.mutated_human_health.get())
                .add(Attributes.ATTACK_DAMAGE, FightOrDieMutationsConfig.SERVER.mutated_human_damage.get())
                .add(Attributes.ARMOR, 4D);

    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controlleersin) {
        controlleersin.add(
                new AnimationController<>(this, "controllerOP", 7, event -> {
                    if (event.isMoving() && !this.isAggressive()) {
                        event.getController().setAnimationSpeed(1.2D);
                        return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_human_walk"));
                    }
                    if (event.isMoving() && this.isAggressive()) {
                        event.getController().setAnimationSpeed(1.3D);
                        return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_human_target"));
                    }
                    if (this.isDeadOrDying()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("assimilated_human_death"));
                    }
                    return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_human_idle"));
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
        if (Math.random() <= 0.25F) {
            this.DropHumanHead(this);
        }
        else if (Math.random() <= 0.35F) {
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

    private void DropHumanHead(Entity entity) {
        AssimilatedHumanHeadEntity assimilatedHumanHeadEntity = new AssimilatedHumanHeadEntity(EntityRegistry.ASSIMILATED_HUMAN_HEAD.get(), entity.level());
        assimilatedHumanHeadEntity.moveTo(entity.getX(),entity.getY(),entity.getZ());
        entity.level().addFreshEntity(assimilatedHumanHeadEntity);
    }
    private void ShillerExplosion(Entity entity) {
        ShillerEntity shillerEntity = new ShillerEntity(EntityRegistry.SHILLER.get(), entity.level());
        shillerEntity.moveTo(entity.getX(),entity.getY(),entity.getZ());
        entity.level().addFreshEntity(shillerEntity);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.ENTITY_ASSIMILATED_HUMAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundRegistry.ENTITY_ASSIMILATED_HURT.get();
    }


    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.HUMANOID_DEATH.get();
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
