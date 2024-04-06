package net.teamabyssalofficial.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.constants.IMathHelper;
import net.teamabyssalofficial.controls.WallMovementControl;
import net.teamabyssalofficial.entity.ai.CustomMeleeAttackGoal;
import net.teamabyssalofficial.entity.ai.MalruptorInfectsGoal;
import net.teamabyssalofficial.entity.categories.*;
import net.teamabyssalofficial.registry.EffectRegistry;
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

public class MalruptorEntity extends Infector implements GeoEntity, Evolved, Hunter {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static boolean jumping = false;

    public MalruptorEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new WallMovementControl(this);
        this.navigation = new WallClimberNavigation(this, pLevel);
    }


    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }


    @Nullable
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.ATTACK_KNOCKBACK, 0.6D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, FightOrDieMutationsConfig.SERVER.springer_health.get())
                .add(Attributes.ATTACK_DAMAGE, FightOrDieMutationsConfig.SERVER.springer_damage.get())
                .add(Attributes.ARMOR, 2.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new CustomMeleeAttackGoal(this, 1.2, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return 2.0 + entity.getBbWidth() * entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(1, new MalruptorJumpGoal(this, 0.7F));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true) {

            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.level() instanceof ServerLevel world && (WorldDataRegistry.getWorldDataRegistry(world).getPhase() > 2 || this.mob.hasEffect(EffectRegistry.FURY.get()));

            }
        });
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.level() instanceof ServerLevel world && (WorldDataRegistry.getWorldDataRegistry(world).getPhase() > 1 || this.mob.hasEffect(EffectRegistry.FURY.get()));
            }
        });
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Skeleton.class, true) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.level() instanceof ServerLevel world && (WorldDataRegistry.getWorldDataRegistry(world).getPhase() > 1 || this.mob.hasEffect(EffectRegistry.FURY.get()));
            }
        });
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, EnderMan.class, true) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.level() instanceof ServerLevel world && (WorldDataRegistry.getWorldDataRegistry(world).getPhase() > 2 || this.mob.hasEffect(EffectRegistry.FURY.get()));
            }
        });
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Witch.class, true) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.level() instanceof ServerLevel world && (WorldDataRegistry.getWorldDataRegistry(world).getPhase() > 2 || this.mob.hasEffect(EffectRegistry.FURY.get()));
            }
        });
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Endermite.class, true) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.level() instanceof ServerLevel world && (WorldDataRegistry.getWorldDataRegistry(world).getPhase() > 1 || this.mob.hasEffect(EffectRegistry.FURY.get()));
            }
        });
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Silverfish.class, true) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.level() instanceof ServerLevel world && (WorldDataRegistry.getWorldDataRegistry(world).getPhase() > 1 || this.mob.hasEffect(EffectRegistry.FURY.get()));
            }
        });
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Spider.class, true) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.level() instanceof ServerLevel world && (WorldDataRegistry.getWorldDataRegistry(world).getPhase() > 1 || this.mob.hasEffect(EffectRegistry.FURY.get()));
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Zombie.class, true) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.level() instanceof ServerLevel world && (WorldDataRegistry.getWorldDataRegistry(world).getPhase() > 1 || this.mob.hasEffect(EffectRegistry.FURY.get()));
            }
        });
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Slime.class, true) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.level() instanceof ServerLevel world && (WorldDataRegistry.getWorldDataRegistry(world).getPhase() > 1 || this.mob.hasEffect(EffectRegistry.FURY.get()));
            }
        });
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, this::targetPredicate) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.level() instanceof ServerLevel world && (WorldDataRegistry.getWorldDataRegistry(world).getPhase() > 2 || this.mob.hasEffect(EffectRegistry.FURY.get()));
            }
        });
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(10, new MalruptorInfectsGoal(this, 1.3, Animal.class, this::infectPredicate) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.getTarget() == null && this.partner != null && !this.partner.hasEffect(EffectRegistry.HIVE_SICKNESS.get()) && (this.mob.level() instanceof ServerLevel world && WorldDataRegistry.getWorldDataRegistry(world).getPhase() < 5);
            }
        });
        this.goalSelector.addGoal(10, new MalruptorInfectsGoal(this, 1.3, Cow.class) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.getTarget() == null && this.partner != null && !this.partner.hasEffect(EffectRegistry.HIVE_SICKNESS.get()) && (this.mob.level() instanceof ServerLevel world && WorldDataRegistry.getWorldDataRegistry(world).getPhase() < 5);
            }
        });
        this.goalSelector.addGoal(10, new MalruptorInfectsGoal(this, 1.3, Sheep.class) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.getTarget() == null && this.partner != null && !this.partner.hasEffect(EffectRegistry.HIVE_SICKNESS.get()) && (this.mob.level() instanceof ServerLevel world && WorldDataRegistry.getWorldDataRegistry(world).getPhase() < 5);
            }
        });
        this.goalSelector.addGoal(10, new MalruptorInfectsGoal(this, 1.3, Pig.class) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.getTarget() == null && this.partner != null && !this.partner.hasEffect(EffectRegistry.HIVE_SICKNESS.get()) && (this.mob.level() instanceof ServerLevel world && WorldDataRegistry.getWorldDataRegistry(world).getPhase() < 5);
            }
        });
        this.goalSelector.addGoal(10, new MalruptorInfectsGoal(this, 1.3, Creeper.class) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.getTarget() == null && this.partner != null && !this.partner.hasEffect(EffectRegistry.HIVE_SICKNESS.get()) && (this.mob.level() instanceof ServerLevel world && WorldDataRegistry.getWorldDataRegistry(world).getPhase() > 2);
            }
        });
        this.goalSelector.addGoal(10, new MalruptorInfectsGoal(this, 1.3, Fox.class) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.getTarget() == null && this.partner != null && !this.partner.hasEffect(EffectRegistry.HIVE_SICKNESS.get()) && (this.mob.level() instanceof ServerLevel world && WorldDataRegistry.getWorldDataRegistry(world).getPhase() < 5);
            }
        });
    }

    private boolean targetPredicate(LivingEntity liv) {
        return !(liv instanceof Mutated || liv instanceof AdvancedMutated || liv instanceof Parasite || liv instanceof Infector || liv instanceof Head || liv instanceof Animal || liv instanceof Squid || liv instanceof ArmorStand || liv instanceof AbstractFish || liv instanceof Bat || FightOrDieMutationsConfig.SERVER.blacklist.get().contains(liv.getEncodeId()));
    }

    private boolean infectPredicate(LivingEntity liv) {
        return FightOrDieMutationsConfig.SERVER.springer_targeted_infection_entities.get().contains(liv.getEncodeId());
    }


    public boolean isHunting() {
        return !this.onGround() && jumping;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerV) {
        controllerV.add(
                new AnimationController<>(this, "controllerV", 7, event -> {
                    if (event.isMoving() && !this.isAggressive()) {
                        event.getController().setAnimationSpeed(1.5D);
                        return event.setAndContinue(RawAnimation.begin().thenLoop("malruptor_walk"));
                    }
                    else if (event.isMoving() && this.isAggressive() && this.onGround()) {
                        event.getController().setAnimationSpeed(1.8D);
                        return event.setAndContinue(RawAnimation.begin().thenLoop("malruptor_target"));
                    }
                    else if (!event.isMoving() && !this.isAggressive()) {
                        event.getController().setAnimationSpeed(1.2D);
                        return event.setAndContinue(RawAnimation.begin().thenLoop("malruptor_idle"));
                    }
                    return PlayState.CONTINUE;
                }));
        controllerV.add(
                new AnimationController<>(this, "controllerK", 7, event -> {
                    if (this.isHunting()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("malruptor_air"));
                    }
                    return PlayState.STOP;
                }));

    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SoundRegistry.ENTITY_MALRUPTOR_HURT.get();
    }
    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.ENTITY_MALRUPTOR_DEATH.get();
    }
    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.ENTITY_MALRUPTOR_AMBIENT.get();
    }


    public int getSubtractionPoints() {
        return 2;
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
        super.die(source);
    }

    public static class MalruptorJumpGoal extends Goal {
        private final Mob mob;
        private LivingEntity target;
        private final float yd;

        private boolean canJump = true;


        public MalruptorJumpGoal(Mob mob, float v) {
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
                        return this.mob.getRandom().nextInt(reducedTickDelay(14)) == 0 && canJump;
                    }
                } else {
                    return false;
                }
            }

        }


        @Override
        public void tick() {
            if (this.mob.getTarget() != null) {
                this.mob.getLookControl().setLookAt(this.target);
                this.mob.setXRot(this.target.getXRot());
                jumping = true;
            }
        }

        public void start() {
            if(this.mob.getRandom().nextInt(3) == 0) {
                canJump = false;
            }
            Vec3 vec3 = this.mob.getDeltaMovement();
            Vec3 vec31 = new Vec3(this.target.getX() - this.mob.getX(), 0.0D, this.target.getZ() - this.mob.getZ());
            if (vec31.lengthSqr() > 1.0E-7D) {
                vec31 = vec31.normalize().scale(2D).add(vec3.scale(1.5D));
            }

            this.mob.setDeltaMovement(vec31.x + yd * (IMathHelper.RELAT / 156), this.yd + (IMathHelper.HEX / 12), vec31.z + yd);
        }

        @Override
        public void stop() {
            super.stop();
        }
    }
}
