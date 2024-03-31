package net.teamabyssalofficial.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.entity.ai.CustomMeleeAttackGoal;
import net.teamabyssalofficial.entity.categories.AdvancedMutated;
import net.teamabyssalofficial.entity.categories.Mutated;
import net.teamabyssalofficial.registry.EntityRegistry;
import net.teamabyssalofficial.registry.ItemRegistry;
import net.teamabyssalofficial.registry.SoundRegistry;
import net.teamabyssalofficial.registry.WorldDataRegistry;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Random;


public class AssimilatedEndermanEntity extends AdvancedMutated implements GeoEntity {


    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public AssimilatedEndermanEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setMaxUpStep(1.0F);
        if (FightOrDieMutationsConfig.SERVER.mutated_enderman_sensible_to_water.get())
            this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }

    public boolean isSensitiveToWater() {
        return FightOrDieMutationsConfig.SERVER.mutated_enderman_sensible_to_water.get();
    }

    IronGolem ironGolem;

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F) {
            @Override
            public boolean canUse() {
                return super.canUse() && FightOrDieMutationsConfig.SERVER.mutated_enderman_sensible_to_water.get();
            }
        });
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
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.3D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.MAX_HEALTH, FightOrDieMutationsConfig.SERVER.mutated_enderman_health.get())
                .add(Attributes.ATTACK_DAMAGE, FightOrDieMutationsConfig.SERVER.mutated_enderman_damage.get())
                .add(Attributes.ARMOR, 4D);

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerR) {
        controllerR.add(
                new AnimationController<>(this, "controllerR", 7, event -> {
                    if (event.isMoving() && !this.isAggressive()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_enderman_walk"));
                    }
                    if (event.isMoving() && this.isAggressive()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_enderman_target"));
                    }
                    if (this.isDeadOrDying()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("assimilated_enderman_death"));
                    }
                    return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_enderman_idle"));
                }));

    }

    public void aiStep() {
        if (this.level().isClientSide) {
            for(int i = 0; i < 3; ++i) {
                this.level().addParticle(ParticleTypes.PORTAL, this.getRandomX(0.75D), this.getRandomY() + 0.25D, this.getRandomZ(0.75D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
            }
        }
        super.aiStep();
    }

    @Override
    public void tick() {
        if(this.getTarget() != null) {
            if (this.getRandom().nextInt(200) == 0 && FightOrDieMutationsConfig.SERVER.mutated_enderman_teleportation.get()) {
                this.shortTp();
                this.setTarget(this.getTarget());
            }

            if (this.getRandom().nextInt(25) == 0 && FightOrDieMutationsConfig.SERVER.mutated_enderman_teleportation.get()) {
                this.teleportToTarget();
                this.setTarget(this.getTarget());
                if (FightOrDieMutationsConfig.SERVER.mutated_enderman_reinforcements.get() && Math.random() <= FightOrDieMutationsConfig.SERVER.mutated_enderman_reinforcement_rate.get()) {
                    this.teleportAlly();
                    this.setTarget(this.getTarget());
                }
            }
        }
        super.tick();
    }

    protected boolean teleport() {
        if (!this.level().isClientSide() && this.isAlive()) {
            double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 64.0D;
            double d1 = this.getY() + (double)(this.random.nextInt(64) - 32);
            double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 64.0D;
            return this.teleport(d0, d1, d2);
        } else {
            return false;
        }
    }

    protected boolean teleportToTarget() {
        if (!this.level().isClientSide() && this.isAlive() && this.getTarget() != null) {
            double d0 = this.getTarget().getX() + (this.random.nextDouble() - 0.5D) * 6.0D;
            double d1 = this.getTarget().getY() + (double)(this.random.nextInt(24) - 12);
            double d2 = this.getTarget().getZ() + (this.random.nextDouble() - 0.5D) * 6.0D;
            return this.teleport(d0, d1, d2);
        } else {
            return false;
        }
    }



    public void teleportAlly() {
        AABB boundingBox = this.getBoundingBox().inflate(100);
        List<Entity> entities = this.level().getEntities(this, boundingBox);
        Random random = new Random();
        if(!entities.isEmpty()) {
          for (int i = 0; i < 1; ++i) {
            int randomIndex = random.nextInt(entities.size());
                Entity entity = entities.get(randomIndex);
                if (entity instanceof LivingEntity livingEntity && livingEntity instanceof Mutated) {
                    double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 2.0D;
                    double d1 = this.getY() + Mth.absMax(0D, 0.5D);
                    double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 2.0D;
                    livingEntity.teleportTo(d0, d1, d2);
                    if (!this.isSilent()) {
                        livingEntity.level().playSound((Player)null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
                        livingEntity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                    }

                }
            }

        }
    }

    protected boolean shortTp() {
        if (!this.level().isClientSide() && this.isAlive()) {
            double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 24.0D;
            double d1 = this.getY() + (double)(this.random.nextInt(60) - 12);
            double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 24.0D;
            return this.teleport(d0, d1, d2);
        } else {
            return false;
        }
    }

    boolean teleportTowards(Entity pTarget) {
        Vec3 vec3 = new Vec3(this.getX() - pTarget.getX(), this.getY(0.5D) - pTarget.getEyeY(), this.getZ() - pTarget.getZ());
        vec3 = vec3.normalize();
        double d0 = 16.0D;
        double d1 = this.getX() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3.x * 16.0D;
        double d2 = this.getY() + (double)(this.random.nextInt(16) - 8) - vec3.y * 16.0D;
        double d3 = this.getZ() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3.z * 16.0D;
        return this.teleport(d1, d2, d3);
    }

    private boolean teleport(double pX, double pY, double pZ) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(pX, pY, pZ);

        while(blockpos$mutableblockpos.getY() > this.level().getMinBuildHeight() && !this.level().getBlockState(blockpos$mutableblockpos).blocksMotion()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }

        BlockState blockstate = this.level().getBlockState(blockpos$mutableblockpos);
        boolean flag = blockstate.blocksMotion();
        boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
        if (flag && !flag1) {
            net.minecraftforge.event.entity.EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(this, pX, pY, pZ);
            if (event.isCanceled()) return false;
            Vec3 vec3 = this.position();
            boolean flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2) {
                this.level().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(this));
                if (!this.isSilent()) {
                    this.level().playSound((Player)null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
                    this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }
            }

            return flag2;
        } else {
            return false;
        }
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() != null && Math.random() <= 0.9 && FightOrDieMutationsConfig.SERVER.mutated_enderman_teleportation.get()) {
            this.shortTp();
        }
        if (this.isInvulnerableTo(pSource)) {
            return false;
        } else {
            boolean flag = pSource.getDirectEntity() instanceof ThrownPotion;
            if (pSource.is(DamageTypeTags.IS_PROJECTILE) && FightOrDieMutationsConfig.SERVER.mutated_enderman_teleportation.get()) {
                this.shortTp();
            }
            if (!pSource.is(DamageTypeTags.IS_PROJECTILE) && !flag) {
                boolean flag2 = super.hurt(pSource, pAmount);
                if (!this.level().isClientSide() && !(pSource.getEntity() instanceof LivingEntity) && this.random.nextInt(10) != 0 && FightOrDieMutationsConfig.SERVER.mutated_enderman_teleportation.get()) {
                    this.teleport();
                }

                return flag2;
            } else {
                boolean flag1 = flag && this.hurtWithCleanWater(pSource, (ThrownPotion)pSource.getDirectEntity(), pAmount);

                for(int i = 0; i < 64; ++i) {
                    if (this.teleport() && FightOrDieMutationsConfig.SERVER.mutated_enderman_teleportation.get()) {
                        return true;
                    }
                }

                return flag1;
            }
        }
    }

    private boolean hurtWithCleanWater(DamageSource pSource, ThrownPotion pPotion, float pAmount) {
        ItemStack itemstack = pPotion.getItem();
        Potion potion = PotionUtils.getPotion(itemstack);
        List<MobEffectInstance> list = PotionUtils.getMobEffects(itemstack);
        boolean flag = potion == Potions.WATER && list.isEmpty();
        return flag ? super.hurt(pSource, pAmount) : false;
    }




    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.ENTITY_ASSIMILATED_ENDERMAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundRegistry.ENTITY_ASSIMILATED_ENDERMAN_HURT.get();
    }


    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.ENTITY_ASSIMILATED_ENDERMAN_DEATH.get();
    }

    public int getSubtractionPoints() {
        return 50;
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

        if(Math.random() <= 0.25F) {
            this.DropEndermanHead(this);
        }

        super.die(source);
    }

    private void DropEndermanHead(Entity entity) {
        AssimilatedEndermanHeadEntity assimilatedEndermanHeadEntity = new AssimilatedEndermanHeadEntity(EntityRegistry.ASSIMILATED_ENDERMAN_HEAD.get(), entity.level());
        assimilatedEndermanHeadEntity.moveTo(entity.getX(),entity.getY(),entity.getZ());
        entity.level().addFreshEntity(assimilatedEndermanHeadEntity);
    }

    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        Entity entity = pSource.getEntity();
        if (Math.random() <= 0.25F) {
            this.spawnAtLocation(ItemRegistry.MUTATED_ENDER_PEARL.get());
        }

    }
}
