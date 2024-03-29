package net.teamabyssalofficial.entity.categories;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
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
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.entity.ai.*;
import net.teamabyssalofficial.registry.*;

import java.util.EnumSet;
import java.util.List;


public class Mutated extends Monster {

    @javax.annotation.Nullable
    private BlockPos patrolTarget;
    private boolean patrolLeader;
    private boolean patrolling;

    public Mutated(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 10.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
        this.xpReward = 8;
        EntityRegistry.PARASITES.add(this);
    }

    public void addAdditionalSaveData(CompoundTag p_33063_) {
        super.addAdditionalSaveData(p_33063_);
        if (this.patrolTarget != null) {
            p_33063_.put("PatrolTarget", NbtUtils.writeBlockPos(this.patrolTarget));
        }

        p_33063_.putBoolean("PatrolLeader", this.patrolLeader);
        p_33063_.putBoolean("Patrolling", this.patrolling);
    }

    public void readAdditionalSaveData(CompoundTag p_33055_) {
        super.readAdditionalSaveData(p_33055_);
        if (p_33055_.contains("PatrolTarget")) {
            this.patrolTarget = NbtUtils.readBlockPos(p_33055_.getCompound("PatrolTarget"));
        }

        this.patrolLeader = p_33055_.getBoolean("PatrolLeader");
        this.patrolling = p_33055_.getBoolean("Patrolling");
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

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new FloatDiveGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, this::targetPredicate));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Animal.class, true, this::animalPredicate));
        this.goalSelector.addGoal(4, new LongDistancePatrolGoal<>(this, 1.0D, 0.9D));
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

    public static boolean checkMonsterAssimilatedRules(EntityType<? extends Mutated> entityType, ServerLevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos pos, RandomSource source) {

        WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry((ServerLevel) levelAccessor.getLevel());
        int currentPhase = worldDataRegistry.getPhase();

        return levelAccessor.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(levelAccessor, pos, source) && checkMobSpawnRules(entityType, levelAccessor, mobSpawnType, pos, source) && currentPhase > 1;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        Entity entity = pSource.getEntity();
        if (Math.random() <= 0.3F) {
            this.spawnAtLocation(ItemRegistry.RIPPING_FLESH.get());
        }
        if (Math.random() <= 0.05) {
            this.spawnAtLocation(ItemRegistry.SICKENED_HEART.get());
        }
        else if (Math.random() <= 0.05) {
            this.spawnAtLocation(ItemRegistry.ROTTEN_BRAIN.get());
        }

    }


    @Override
    public void die(DamageSource source) {
        if (this.level() instanceof ServerLevel world) {
            WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(world);
            int currentScore = worldDataRegistry.getScore();
            int currentPhase = worldDataRegistry.getPhase();
            if (Math.random() <= 0.85F && currentPhase > 2) {
                worldDataRegistry.setScore(currentScore - 5);
            }
        }
        super.die(source);
    }

    public boolean canBeLeader() {
        return true;
    }


    @javax.annotation.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33049_, DifficultyInstance p_33050_, MobSpawnType p_33051_, @javax.annotation.Nullable SpawnGroupData p_33052_, @javax.annotation.Nullable CompoundTag p_33053_) {
        if (p_33051_ != MobSpawnType.PATROL && p_33051_ != MobSpawnType.EVENT && p_33051_ != MobSpawnType.STRUCTURE && this.random.nextFloat() < 0.06F && this.canBeLeader()) {
            this.patrolLeader = true;
        }

        if (p_33051_ == MobSpawnType.PATROL) {
            this.patrolling = true;
        }

        return super.finalizeSpawn(p_33049_, p_33050_, p_33051_, p_33052_, p_33053_);
    }


    public boolean removeWhenFarAway(double p_33073_) {
        return !this.patrolling || p_33073_ > 16384.0D;
    }


    public void setPatrolTarget(BlockPos p_33071_) {
        this.patrolTarget = p_33071_;
        this.patrolling = true;
    }

    public BlockPos getPatrolTarget() {
        return this.patrolTarget;
    }

    public boolean hasPatrolTarget() {
        return this.patrolTarget != null;
    }

    public void setPatrolLeader(boolean p_33076_) {
        this.patrolLeader = p_33076_;
        this.patrolling = true;
    }

    public boolean isPatrolLeader() {
        return this.patrolLeader;
    }

    public boolean canJoinPatrol() {
        return true;
    }


    public void findPatrolTarget() {
        this.patrolTarget = this.blockPosition().offset(-500 + this.random.nextInt(1000), 0, -500 + this.random.nextInt(1000));
        this.patrolling = true;
    }

    protected boolean isPatrolling() {
        return this.patrolling;
    }

    protected void setPatrolling(boolean p_33078_) {
        this.patrolling = p_33078_;
    }
    public static class LongDistancePatrolGoal<T extends Mutated> extends Goal {
        private final T mob;
        private final double speedModifier;
        private final double leaderSpeedModifier;
        private long cooldownUntil;

        public LongDistancePatrolGoal(T p_33084_, double p_33085_, double p_33086_) {
            this.mob = p_33084_;
            this.speedModifier = p_33085_;
            this.leaderSpeedModifier = p_33086_;
            this.cooldownUntil = -1L;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            boolean flag = this.mob.level().getGameTime() < this.cooldownUntil;
            return this.mob.isPatrolling() && this.mob.getTarget() == null && !this.mob.isVehicle() && this.mob.hasPatrolTarget() && !flag;
        }

        public void start() {
        }

        public void stop() {
        }

        public void tick() {
            boolean flag = this.mob.isPatrolLeader();
            PathNavigation pathnavigation = this.mob.getNavigation();
            if (pathnavigation.isDone()) {
                List<Mutated> list = this.findPatrolCompanions();
                if (this.mob.isPatrolling() && list.isEmpty()) {
                    this.mob.setPatrolling(false);
                } else if (flag && this.mob.getPatrolTarget().closerToCenterThan(this.mob.position(), 10.0D)) {
                    this.mob.findPatrolTarget();
                } else {
                    Vec3 vec3 = Vec3.atBottomCenterOf(this.mob.getPatrolTarget());
                    Vec3 vec31 = this.mob.position();
                    Vec3 vec32 = vec31.subtract(vec3);
                    vec3 = vec32.yRot(90.0F).scale(0.4D).add(vec3);
                    Vec3 vec33 = vec3.subtract(vec31).normalize().scale(10.0D).add(vec31);
                    BlockPos blockpos = BlockPos.containing(vec33);
                    blockpos = this.mob.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockpos);
                    if (!pathnavigation.moveTo((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), flag ? this.leaderSpeedModifier : this.speedModifier)) {
                        this.moveRandomly();
                        this.cooldownUntil = this.mob.level().getGameTime() + 200L;
                    } else if (flag) {
                        for(Mutated patrollingmonster : list) {
                            patrollingmonster.setPatrolTarget(blockpos);
                        }
                    }
                }
            }

        }


        private List<Mutated> findPatrolCompanions() {
            return this.mob.level().getEntitiesOfClass(Mutated.class, this.mob.getBoundingBox().inflate(16.0D), (p_33089_) -> {
                return p_33089_.canJoinPatrol() && !p_33089_.is(this.mob);
            });
        }

        private boolean moveRandomly() {
            RandomSource random = this.mob.getRandom();
            BlockPos blockpos = this.mob.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, this.mob.blockPosition().offset(-8 + random.nextInt(16), 0, -8 + random.nextInt(16)));
            return this.mob.getNavigation().moveTo((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), this.speedModifier);
        }
    }

}
