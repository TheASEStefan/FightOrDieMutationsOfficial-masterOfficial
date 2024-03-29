package net.teamabyssalofficial.entity.categories;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.network.NetworkHooks;
import net.teamabyssalofficial.entity.ai.FloatDiveGoal;
import net.teamabyssalofficial.registry.EffectRegistry;
import net.teamabyssalofficial.registry.EntityRegistry;
import net.teamabyssalofficial.registry.WorldDataRegistry;

public class Parasite extends Monster {


    public Parasite(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -0.8F);
        this.xpReward = 2;
        EntityRegistry.PARASITES.add(this);
    }


    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }



    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() != null && pSource.getEntity() instanceof LivingEntity entity && Math.random() <= 0.65F) {
            entity.addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 600, 0), entity);
        }
        return super.hurt(pSource, pAmount);
    }


    public int getMaxAirSupply() {
        return Math.max(600, 1200) / 2 ;
    }
    protected int increaseAirSupply(int p_28389_) {
        return this.getMaxAirSupply();
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(10, new FloatDiveGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.8) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.getTarget() == null;
            }
            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && this.mob.getTarget() == null;
            }
        });
    }


    public static boolean checkMonsterParasiteRules(EntityType<? extends Parasite> entityType, ServerLevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos pos, RandomSource source) {

        WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry((ServerLevel) levelAccessor.getLevel());
        int currentPhase = worldDataRegistry.getPhase();

        return levelAccessor.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(levelAccessor, pos, source) && checkMobSpawnRules(entityType, levelAccessor, mobSpawnType, pos, source) && currentPhase >= 0;
    }

    @Override
    public void die(DamageSource source) {
        this.level().addParticle(DustParticleOptions.REDSTONE, this.getX(), this.getY() + 0.8, this.getZ(), 0.0D, 0.0D, 0.0D);
        this.level().addParticle(DustParticleOptions.REDSTONE, this.getX(), this.getY() + 0.8, this.getZ() + 0.1, 0.0D, 0.0D, 0.0D);
        this.level().addParticle(DustParticleOptions.REDSTONE, this.getX(), this.getY() + 0.8, this.getZ() - 0.1, 0.0D, 0.0D, 0.0D);
        super.die(source);
    }

}
