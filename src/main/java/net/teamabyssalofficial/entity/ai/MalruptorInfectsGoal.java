package net.teamabyssalofficial.entity.ai;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.teamabyssalofficial.registry.EffectRegistry;
import net.teamabyssalofficial.registry.ParticleRegistry;
import net.teamabyssalofficial.registry.SoundRegistry;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class MalruptorInfectsGoal extends Goal {

    private static TargetingConditions PARTNER_TARGETING;
    protected final Level level;
    protected final PathfinderMob mob;
    private final Class<? extends PathfinderMob> partnerClass;
    @Nullable
    protected PathfinderMob partner;
    private final double speedModifier;


    public MalruptorInfectsGoal(PathfinderMob mob, double speedModifier){
        this(mob , speedModifier, mob.getClass());
    }
    public MalruptorInfectsGoal(PathfinderMob mob, double speedModifier, Class<? extends PathfinderMob> partnerClass){
        this(mob,speedModifier,partnerClass , null);
    }

    public MalruptorInfectsGoal(PathfinderMob mob, double speedModifier, Class<? extends PathfinderMob> partnerClass, @Nullable Predicate<LivingEntity> en){
        this.level = mob.level();
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.partnerClass = partnerClass;
        PARTNER_TARGETING = TargetingConditions.forNonCombat().range(32).selector(en);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (mob.getTarget() != null) {
            return false;
        }
        else if (this.mob.getRandom().nextInt(15) == 0 && this.getFreePartner() == null) {
            return false;
        } else{
            if (this.mob.getRandom().nextInt(5) == 1){
                this.partner = this.getFreePartner();
            }
            return this.partnerClass != null;
        }
    }
    public boolean canContinueToUse() {
        if (this.partner != null) {
            return this.partner.isAlive() || this.getFreePartner() != null;
        }
        return false;
    }


    public void stop() {
        this.partner = null;
    }

    public void tick() {
        if (this.partner != null && this.mob.getTarget() == null) {
            this.mob.getNavigation().moveTo(this.partner, this.speedModifier);
            if (this.mob.distanceTo(this.partner) <= 1.2F && this.mob.getTarget() == null && !this.partner.hasEffect(EffectRegistry.HIVE_SICKNESS.get()) && this.mob.getRandom().nextInt(10) == 0) {
                this.spawnLingeringCloud();
                this.partner.addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 100000, 0), this.partner);
                this.partner.level().playSound((Player) null, this.partner.blockPosition(), SoundRegistry.ENTITY_MALRUPTOR_INFECT.get(), SoundSource.HOSTILE, 1.2F, 1.0F);

            }
        }
    }

    private void spawnLingeringCloud() {
        AreaEffectCloud cloud = new AreaEffectCloud(this.level, this.mob.getX(), this.mob.getY(), this.mob.getZ());
        cloud.setRadius(4.0F);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(10);
        cloud.setDuration(cloud.getDuration() / 2);
        cloud.setRadiusPerTick(-cloud.getRadius() / (float)cloud.getDuration());
        cloud.setParticle(ParticleRegistry.KILL_COUNT.get());
        cloud.addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 100000));

        this.level.addFreshEntity(cloud);
    }


    @Nullable
    private PathfinderMob getFreePartner() {
        List<? extends PathfinderMob> list = this.level.getNearbyEntities(this.partnerClass, PARTNER_TARGETING, this.mob, this.mob.getBoundingBox().inflate(this.mob.getAttributeBaseValue(Attributes.FOLLOW_RANGE) + 32));
        double d0 = Double.MAX_VALUE;
        PathfinderMob inf = null;

        for(PathfinderMob inf1 : list) {
            if ( this.mob.distanceToSqr(inf1) < d0) {
                inf = inf1;
                d0 = this.mob.distanceToSqr(inf1);
            }
        }

        return inf;
    }

}

