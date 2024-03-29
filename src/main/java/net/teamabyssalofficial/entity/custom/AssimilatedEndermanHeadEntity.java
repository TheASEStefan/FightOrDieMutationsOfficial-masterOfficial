package net.teamabyssalofficial.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
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
import net.minecraft.world.phys.Vec3;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.constants.IMathHelper;
import net.teamabyssalofficial.controls.WallMovementControl;
import net.teamabyssalofficial.entity.ai.CustomMeleeAttackGoal;
import net.teamabyssalofficial.entity.categories.Evolving;
import net.teamabyssalofficial.entity.categories.Head;
import net.teamabyssalofficial.entity.categories.Hunter;
import net.teamabyssalofficial.registry.EntityRegistry;
import net.teamabyssalofficial.registry.SoundRegistry;
import net.teamabyssalofficial.registry.WorldDataRegistry;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.List;

public class AssimilatedEndermanHeadEntity extends Head implements GeoEntity, Hunter, Evolving {

    public static final EntityDataAccessor<Integer> KILLS = SynchedEntityData.defineId(AssimilatedEndermanHeadEntity.class, EntityDataSerializers.INT);
    private static boolean hunting = false;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public AssimilatedEndermanHeadEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new WallMovementControl(this);
        this.navigation = new WallClimberNavigation(this, pLevel);
        this.setMaxUpStep(1.0F);
        if (FightOrDieMutationsConfig.SERVER.mutated_enderman_head_sensible_to_water.get())
            this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }

    public boolean isSensitiveToWater() {
        return FightOrDieMutationsConfig.SERVER.mutated_enderman_head_sensible_to_water.get();
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new HeadHuntGoal(this, 0.65F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F) {
            @Override
            public boolean canUse() {
                return super.canUse() && FightOrDieMutationsConfig.SERVER.mutated_enderman_head_sensible_to_water.get();
            }
        });
        this.goalSelector.addGoal(4, new CustomMeleeAttackGoal(this, 1.5, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return 1.5 + entity.getBbWidth() * entity.getBbWidth();
            }
        });
    }

    @Nullable
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.ATTACK_KNOCKBACK, 0.05D)
                .add(Attributes.FOLLOW_RANGE, 64D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, FightOrDieMutationsConfig.SERVER.mutated_enderman_head_health.get())
                .add(Attributes.ATTACK_DAMAGE, FightOrDieMutationsConfig.SERVER.mutated_enderman_head_damage.get())
                .add(Attributes.ARMOR, 1D);

    }

    public void aiStep() {
        if (this.level().isClientSide) {
            for(int i = 0; i < 3; ++i) {
                this.level().addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
            }
        }
        super.aiStep();
    }
    @Override
    public void tick() {
        if (this.isAlive() && this.getKills() == 10) {

            this.level().playSound((Player) null, this.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.0F, 1.0F);
            if (this.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY() + 1, this.getZ(), 5, 0.4, 1.0, 0.4, 0);
                WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(server);
                int currentScore = worldDataRegistry.getScore();
                worldDataRegistry.setScore(currentScore + 10);
            }
            this.discard();
            this.EvolveIntoSimEnderman(this);
        }

        if (this.isAlive() && this.getTarget() != null) {
            if (this.getRandom().nextInt(45) == 0 && FightOrDieMutationsConfig.SERVER.mutated_enderman_head_teleportation.get())
                this.teleportToTarget();
        }
        super.tick();
    }
    private void EvolveIntoSimEnderman(Entity entity) {
        AssimilatedEndermanEntity assimilatedEndermanEntity = new AssimilatedEndermanEntity(EntityRegistry.ASSIMILATED_ENDERMAN.get(), entity.level());
        assimilatedEndermanEntity.moveTo(entity.getX(),entity.getY(),entity.getZ());
        entity.level().addFreshEntity(assimilatedEndermanEntity);
    }

    protected boolean teleportToTarget() {
        if (!this.level().isClientSide() && this.isAlive() && this.getTarget() != null) {
            double d0 = this.getTarget().getX() + (this.random.nextDouble() - 0.5D) * 4.0D;
            double d1 = this.getTarget().getY() + (double)(this.random.nextInt(24) - 12);
            double d2 = this.getTarget().getZ() + (this.random.nextDouble() - 0.5D) * 4.0D;
            return this.teleport(d0, d1, d2);
        } else {
            return false;
        }
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

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() != null && Math.random() <= 0.75 && FightOrDieMutationsConfig.SERVER.mutated_enderman_head_teleportation.get()) {
            this.shortTp();
        }
        if (this.isInvulnerableTo(pSource)) {
            return false;
        } else {
            boolean flag = pSource.getDirectEntity() instanceof ThrownPotion;
            if (pSource.is(DamageTypeTags.IS_PROJECTILE) && FightOrDieMutationsConfig.SERVER.mutated_enderman_head_teleportation.get()) {
                this.shortTp();
            }
            if (!pSource.is(DamageTypeTags.IS_PROJECTILE) && !flag) {
                boolean flag2 = super.hurt(pSource, pAmount);
                if (!this.level().isClientSide() && !(pSource.getEntity() instanceof LivingEntity) && this.random.nextInt(10) != 0 && FightOrDieMutationsConfig.SERVER.mutated_enderman_head_teleportation.get()) {
                    this.shortTp();
                }

                return flag2;
            } else {
                boolean flag1 = flag && this.hurtWithCleanWater(pSource, (ThrownPotion)pSource.getDirectEntity(), pAmount);

                for(int i = 0; i < 64; ++i) {
                    if (this.shortTp() && FightOrDieMutationsConfig.SERVER.mutated_enderman_head_teleportation.get()) {
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



    public void setKills(Integer count) {
        entityData.set(KILLS, count);
    }

    public int getKills() {
        return entityData.get(KILLS);
    }
    public boolean isHunting() {
        return !this.onGround() && hunting;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("kills", entityData.get(KILLS));
    }


    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        entityData.set(KILLS, tag.getInt("kills"));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(KILLS, 0);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerO) {
        controllerO.add(
                new AnimationController<>(this, "controllerO", 7, event -> {
                    if (event.isMoving() && !this.isAggressive()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_enderman_head_walk"));
                    }
                    else if (event.isMoving() && this.isAggressive() && this.onGround()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_enderman_head_target"));
                    }
                    else if (!event.isMoving() && !this.isAggressive()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_enderman_head_idle"));
                    }
                    return PlayState.CONTINUE;
                }));
        controllerO.add(
                new AnimationController<>(this, "controllerN", 7, event -> {
                    if (this.isHunting()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("assimilated_enderman_head_air"));
                    }
                    return PlayState.STOP;
                }));

    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.ENTITY_ASSIMILATED_ENDERMAN_HEAD_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundRegistry.ENTITY_ASSIMILATED_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.ENTITY_ASSIMILATED_HURT.get();
    }

    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        Entity entity = pSource.getEntity();

    }
    public static class HeadHuntGoal extends Goal {
        private final Mob mob;
        private LivingEntity target;
        private final float yd;

        public HeadHuntGoal(Mob mob, float v) {
            this.mob = mob;
            this.yd = v;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean canUse() {
            this.target = this.mob.getTarget();
            if (this.target == null) {
                return false;
            } else if (this.mob.isInWater()) {
                return false;
            } else {
                double d0 = this.mob.distanceTo(this.target);
                if (d0 > 6.0D && d0 < 14.0D) {
                    if (!this.mob.onGround()) {
                        return false;
                    } else {
                        return this.mob.getRandom().nextInt(reducedTickDelay(15)) == 0;
                    }
                } else {
                    return false;
                }
            }

        }

        public boolean canContinueToUse() {
            return !this.mob.onGround();
        }


        @Override
        public void tick() {
            if (this.mob.getTarget() != null) {
                this.mob.getLookControl().setLookAt(this.target);
                hunting = true;

            }
        }

        public void start() {
            Vec3 vec3 = this.mob.getDeltaMovement();
            Vec3 vec31 = new Vec3(this.target.getX() - this.mob.getX(), 0.0D, this.target.getZ() - this.mob.getZ());
            if (vec31.lengthSqr() > 1.0E-7D) {
                vec31 = vec31.normalize().scale(2D).add(vec3.scale(1.5D));
            }

            this.mob.setDeltaMovement(vec31.x + yd * (IMathHelper.DELTA / 64), this.yd + (IMathHelper.PI / 12), vec31.z + yd);
        }

        @Override
        public void stop() {
            super.stop();
        }
    }
}
