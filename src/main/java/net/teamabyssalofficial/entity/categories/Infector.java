package net.teamabyssalofficial.entity.categories;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.network.NetworkHooks;
import net.teamabyssalofficial.entity.ai.FloatDiveGoal;
import net.teamabyssalofficial.registry.*;

public class Infector extends Monster {



    public Infector(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 12.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.2F);
        this.xpReward = 5;
        EntityRegistry.PARASITES.add(this);
    }



    public int getMaxAirSupply() {
        return 1200;
    }
    protected int increaseAirSupply(int p_28389_) {
        return this.getMaxAirSupply();
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (entity instanceof LivingEntity && Math.random() < 0.3F) {
            ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0), entity);
        }
        if (entity instanceof LivingEntity && Math.random() <= 0.85F) {
            ((LivingEntity) entity).addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 600, 0), entity);
        }
        return super.doHurtTarget(entity);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3,new FloatDiveGoal(this));
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
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Animal.class, true, this::animalPredicate));
    }

    private boolean animalPredicate(LivingEntity liv) {
        Level level = liv.level();
        if (level instanceof ServerLevel serverLevel) {
            return WorldDataRegistry.getWorldDataRegistry(serverLevel).getPhase() > 3 || this.hasEffect(EffectRegistry.FURY.get());
        }
        return false;
    }


    public static boolean checkMonsterInfectorRules(EntityType<? extends Infector> entityType, ServerLevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos pos, RandomSource source) {

        WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry((ServerLevel) levelAccessor.getLevel());
        int currentPhase = worldDataRegistry.getPhase();

        return levelAccessor.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(levelAccessor, pos, source) && checkMobSpawnRules(entityType, levelAccessor, mobSpawnType, pos, source) && currentPhase > 0;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        Entity entity = pSource.getEntity();
        if (Math.random() <= 0.05F) {
            this.spawnAtLocation(ItemRegistry.INFECTOR_THORAX.get());
        }
    }

}
