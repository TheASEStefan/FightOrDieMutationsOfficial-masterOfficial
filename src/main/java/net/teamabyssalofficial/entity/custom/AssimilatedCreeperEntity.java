package net.teamabyssalofficial.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.constants.IMathHelper;
import net.teamabyssalofficial.entity.ai.CustomMeleeAttackGoal;
import net.teamabyssalofficial.entity.categories.*;
import net.teamabyssalofficial.extra.ScreenShakeEntity;
import net.teamabyssalofficial.registry.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class AssimilatedCreeperEntity extends AdvancedMutated implements GeoEntity {
    private final int minDamage = 2;
    private final int maxDamage = 4;
    private final byte box = 10;
    private final byte half_box = box / 2;
    private final float extraRadius = ((IMathHelper.HEX + Mth.clamp(3, IMathHelper.HEX, IMathHelper.PI) + (IMathHelper.DELTA / 3)) / 10);
    private final float explosionRadius = (float) (IMathHelper.HEX * FightOrDieMutationsConfig.SERVER.mutated_creeper_explosion_radius.get());
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public AssimilatedCreeperEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setMaxUpStep(1.0F);
    }


    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        boolean flag = super.causeFallDamage(pFallDistance, pMultiplier / ((float) (minDamage + maxDamage) / 2), pSource);
        return flag;
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
                .add(Attributes.FOLLOW_RANGE, 64D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.JUMP_STRENGTH, 0.25)
                .add(Attributes.MAX_HEALTH, FightOrDieMutationsConfig.SERVER.mutated_creeper_health.get())
                .add(Attributes.ATTACK_DAMAGE, FightOrDieMutationsConfig.SERVER.mutated_creeper_damage.get())
                .add(Attributes.ARMOR, 6D);

    }


    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.is(ItemTags.CREEPER_IGNITERS)) {
            SoundEvent soundevent = itemstack.is(Items.FIRE_CHARGE) ? SoundEvents.FIRECHARGE_USE : SoundEvents.FLINTANDSTEEL_USE;
            this.level().playSound(pPlayer, this.getX(), this.getY(), this.getZ(), soundevent, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.level().isClientSide) {
                this.explodeThis();
                if (!itemstack.isDamageableItem()) {
                    itemstack.shrink(1);
                } else {
                    itemstack.hurtAndBreak(1, pPlayer, (p_32290_) -> {
                        p_32290_.broadcastBreakEvent(pHand);
                    });
                }
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    public boolean canExplode() {
        return this.getTarget() != null && (this.getTarget() instanceof Player || this.getTarget() instanceof IronGolem);
    }

    private boolean explosionPredicate(LivingEntity liv) {
        return !(liv instanceof Mutated || liv instanceof AdvancedMutated);
    }



    @Override
    public void tick() {
        if (this.getTarget() != null && this.isAlive()) {
            Entity attackTarget = this.getTarget();
            if (this.canExplode()) {
                if (this.distanceTo(attackTarget) < 1.35 && attackTarget instanceof Player) {
                    this.explodeThis();
                }
                else if (this.distanceTo(attackTarget) < 3 && attackTarget instanceof IronGolem) {
                    this.explodeThis();
                }
            }

        }
        super.tick();
    }


    private void explodeThis() {
        if (!this.level().isClientSide && this.getTarget() != null && this.getTarget().isAlive()) {
            this.dead = true;
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), (float) this.explosionRadius * this.extraRadius, Level.ExplosionInteraction.NONE);
            this.level().playSound((Player) null, this.blockPosition(), SoundRegistry.ENTITY_EXPLOSION.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
            this.discard();
            this.spawnLingeringCloud();
            ScreenShakeEntity.ScreenShake(level(), position(), 12, 0.25f, 15, 10);

            AABB boundingBox = this.getBoundingBox().inflate(half_box + 1);
            List<Entity> entities = this.level().getEntities(this, boundingBox);
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity livingEntity && !(EntityRegistry.PARASITES.contains(entity))) {
                    if (!livingEntity.hasEffect(EffectRegistry.HIVE_SICKNESS.get())) {
                        livingEntity.addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 6000, 2), livingEntity);
                    }
                }
            }

            Level world = this.level();
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();

            boolean flag1 = !world.getEntitiesOfClass(Mutated.class, AABB.ofSize(new Vec3(x, y, z), box, box, box), e -> true).isEmpty();
            boolean flag2 = !world.getEntitiesOfClass(AdvancedMutated.class, AABB.ofSize(new Vec3(x, y, z), box, box, box), e -> true).isEmpty();

            if (flag1 || flag2) {
                if (world instanceof ServerLevel world1) {
                    WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(world1);
                    worldDataRegistry.setScore(worldDataRegistry.getScore() + this.getSubtractionPoints());
                }
            }
        }
    }

    private void spawnLingeringCloud() {
        AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
        cloud.setRadius(3.5F);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(10);
        cloud.setDuration(cloud.getDuration() / 3);
        cloud.setRadiusPerTick(-cloud.getRadius() / (float)cloud.getDuration());
        cloud.addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 6000, 1));
        cloud.addEffect(new MobEffectInstance(EffectRegistry.GERMILIS.get(), 400, 0));

        this.level().addFreshEntity(cloud);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(
                new AnimationController<>(this, "controllerOP", 7, event -> {
                    if (!event.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_creeper_idle"));
                    }
                    if (event.isMoving() && !this.isAggressive()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_creeper_walk"));
                    }
                    if (event.isMoving() && this.isAggressive()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_creeper_target"));
                    }
                    return PlayState.CONTINUE;
                }));

    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        Entity attackTarget = this.getTarget();
        if (attackTarget != null && pSource.getEntity() != null && pSource.getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity != attackTarget && this.isAlive()) {
                if (this.distanceTo(attackTarget) > 4) {
                    this.setTarget(livingEntity);
                    this.getLookControl().setLookAt(livingEntity);
                }
            }
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public int getSubtractionPoints() {
        return 25;
    }
    @Override
    public void die(DamageSource source) {
        if (this.level() instanceof ServerLevel world) {
            WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(world);
            int currentScore = worldDataRegistry.getScore();
            int currentPhase = worldDataRegistry.getPhase();
            if (currentPhase > 2) {
                worldDataRegistry.setScore(currentScore - this.getSubtractionPoints());
            }
        }
        if (Math.random() <= 0.75F) {
            if (this.getTarget() != null) {
                Entity attackTarget = this.getTarget();
                if (!(attackTarget instanceof Player || attackTarget instanceof IronGolem)) {
                    this.explodeThis();
                }
            }
        }
        else if (Math.random() <= 0.25F) {
            this.DropCreeperHead(this);
        }


        super.die(source);
    }

    private void DropCreeperHead(Entity entity) {
        AssimilatedCreeperHeadEntity assimilatedCreeperHeadEntity = new AssimilatedCreeperHeadEntity(EntityRegistry.ASSIMILATED_CREEPER_HEAD.get(), entity.level());
        assimilatedCreeperHeadEntity.moveTo(entity.getX(),entity.getY(),entity.getZ());
        entity.level().addFreshEntity(assimilatedCreeperHeadEntity);
    }


    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.ENTITY_ASSIMILATED_CREEPER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundRegistry.ENTITY_ASSIMILATED_HURT.get();
    }


    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.ENTITY_ASSIMILATED_CREEPER_DEATH.get();
    }

    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        Entity entity = pSource.getEntity();

    }

}
