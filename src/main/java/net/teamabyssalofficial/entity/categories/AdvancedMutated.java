package net.teamabyssalofficial.entity.categories;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.network.NetworkHooks;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.entity.ai.FloatDiveGoal;
import net.teamabyssalofficial.registry.EffectRegistry;
import net.teamabyssalofficial.registry.EntityRegistry;
import net.teamabyssalofficial.registry.WorldDataRegistry;

public class AdvancedMutated extends Monster {
    public static final EntityDataAccessor<Integer> AGGRESSION_TICKS = SynchedEntityData.defineId(AdvancedMutated.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> PERSISTENT = SynchedEntityData.defineId(AdvancedMutated.class, EntityDataSerializers.BOOLEAN);
    public AdvancedMutated(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 20.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -2.0F);
        this.xpReward = 15;
        EntityRegistry.PARASITES.add(this);
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return !this.entityData.get(PERSISTENT);
    }

    public int getMaxAirSupply() {
        return 1600;
    }
    protected int increaseAirSupply(int pCurrentAir) {
        return this.getMaxAirSupply();
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (entity instanceof LivingEntity && Math.random() <= 0.85F) {
            ((LivingEntity) entity).addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 1200, 0), entity);
        }
        return super.doHurtTarget(entity);
    }

    public void setPersistent(boolean persistent) {
        entityData.set(PERSISTENT, persistent);
    }
    public void setAggressionTicks(int ticks) {
        entityData.set(AGGRESSION_TICKS, ticks);
    }
    public int getAggressionTicks() {
        return this.entityData.get(AGGRESSION_TICKS);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("aggression_ticks",entityData.get(AGGRESSION_TICKS));
        tag.putBoolean("persistent",entityData.get(PERSISTENT));
    }


    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        entityData.set(AGGRESSION_TICKS, tag.getInt("aggression_ticks"));
        entityData.set(PERSISTENT, tag.getBoolean("persistent"));
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AGGRESSION_TICKS, 0);
        this.entityData.define(PERSISTENT,false);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getTarget() != null && this.isAlive()) {
            this.setAggressionTicks(this.getAggressionTicks() + 1);
            if (this.getAggressionTicks() == (1600 + this.getRandom().nextInt(800))) {
                this.setPersistent(true);
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new FloatDiveGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, this::targetPredicate));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Animal.class, true, this::animalPredicate));
    }
    private boolean animalPredicate(LivingEntity liv) {
        Level level = liv.level();
        if (level instanceof ServerLevel serverLevel) {
            return WorldDataRegistry.getWorldDataRegistry(serverLevel).getPhase() > 3;
        }
        return false;
    }

    private boolean targetPredicate(LivingEntity liv) {
        return !(liv instanceof Mutated || liv instanceof AdvancedMutated || liv instanceof Parasite || liv instanceof Infector || liv instanceof Head || liv instanceof Animal || liv instanceof Squid || liv instanceof ArmorStand || liv instanceof AbstractFish || liv instanceof Bat || FightOrDieMutationsConfig.SERVER.blacklist.get().contains(liv.getEncodeId()));
    }

    public static boolean checkMonsterAdvancedAssimilatedRules(EntityType<? extends AdvancedMutated> entityType, ServerLevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos pos, RandomSource source) {

        WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry((ServerLevel) levelAccessor.getLevel());
        int currentPhase = worldDataRegistry.getPhase();

        return levelAccessor.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(levelAccessor, pos, source) && checkMobSpawnRules(entityType, levelAccessor, mobSpawnType, pos, source) && currentPhase > 2;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        Entity entity = pSource.getEntity();

    }

}
