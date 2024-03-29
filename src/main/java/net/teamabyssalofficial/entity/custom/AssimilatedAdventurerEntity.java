package net.teamabyssalofficial.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.constants.IMath;
import net.teamabyssalofficial.entity.ai.CustomMeleeAttackGoal;
import net.teamabyssalofficial.entity.ai.RareLeapGoal;
import net.teamabyssalofficial.entity.categories.Mutated;
import net.teamabyssalofficial.extra.ScreenShakeEntity;
import net.teamabyssalofficial.registry.EffectRegistry;
import net.teamabyssalofficial.registry.EntityRegistry;
import net.teamabyssalofficial.registry.ParticleRegistry;
import net.teamabyssalofficial.registry.SoundRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AssimilatedAdventurerEntity extends Mutated {

    public static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(AssimilatedAdventurerEntity.class, EntityDataSerializers.BOOLEAN);


    public AssimilatedAdventurerEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void customServerAiStep() {
        if (!this.isNoAi() && GoalUtils.hasGroundPathNavigation(this)) {
            ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        }
        super.customServerAiStep();
    }




    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new RareLeapGoal(this, 0.4F) {
            @Override
            public void tick() {
                setLeaping(true);
                super.tick();
            }
        });

        this.goalSelector.addGoal(4, new CustomMeleeAttackGoal(this, 1.5, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return 2.0 + entity.getBbWidth() * entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(5, new OpenDoorGoal(this, true) {
            @Override
            public void start() {
                this.mob.swing(InteractionHand.MAIN_HAND);
                super.start();
            }
        });



    }
    public void setLeaping(boolean leaping) {
        entityData.set(LEAPING, leaping);
    }

    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LEAPING,false);
    }


    @Nullable
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.FOLLOW_RANGE, 32D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.MAX_HEALTH, FightOrDieMutationsConfig.SERVER.mutated_player_health.get())
                .add(Attributes.ATTACK_DAMAGE, FightOrDieMutationsConfig.SERVER.mutated_player_damage.get())
                .add(Attributes.ARMOR_TOUGHNESS, 2D)
                .add(Attributes.ARMOR, 2D);

    }


    @Override
    public boolean doHurtTarget(Entity entity) {
        float k = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float p = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);

        if (entity.isAlive()) {
            if (this.getMainHandItem().getItem() != null) {
                k += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity) entity).getMobType()) / 2;
                ((LivingEntity) entity).knockback(p * IMath.PI / 2, entity.getX(), entity.getZ());
            }
            if (entity instanceof Player player) {
                this.disableShield(player, this.getMainHandItem(), player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
            }
        }

        return super.doHurtTarget(entity);
    }

    public void disableShield(Player player, ItemStack itemStack, ItemStack itemStack1) {
        if (!itemStack.isEmpty() && !itemStack1.isEmpty() && itemStack.getItem() instanceof AxeItem && itemStack1.is(Items.SHIELD)) {
            float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
            if (this.random.nextFloat() < f) {
                player.getCooldowns().addCooldown(Items.SHIELD, 100);
                this.level().broadcastEntityEvent(player, (byte) 30);
            }
        }

    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        float f = (float) this.getAttributeValue(Attributes.ARMOR);
        float v = (float) this.getAttributeValue(Attributes.ARMOR_TOUGHNESS);

        if (this.getLastDamageSource() != null) {
            f += EnchantmentHelper.getDamageProtection(this.getArmorSlots(), this.getLastDamageSource());
            v += Mth.ceil(((EnchantmentHelper.getDamageProtection(this.getArmorSlots(), this.getLastDamageSource())) * IMath.PI) / 10);
        }
        return super.hurt(pSource, pAmount);
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

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor groupData, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        RandomSource randomsource = groupData.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        this.populateDefaultEquipmentEnchantments(randomsource, pDifficulty);
        return super.finalizeSpawn(groupData, pDifficulty, pReason, spawnGroupData, compoundTag);
    }

    protected void populateDefaultEquipmentSlots(RandomSource p_219059_, DifficultyInstance p_219060_) {
        ItemStack mainG = ItemStack.EMPTY;
        List<? extends String> name = FightOrDieMutationsConfig.DATAGEN.name.get();
        Random rand = new Random();
        if (this.getCustomName() == null){
            for (int i = 0; i < 1; ++i) {
                int randomIndex = rand.nextInt(name.size());
                Component component = Component.nullToEmpty(name.get(randomIndex));
                this.setCustomName(component);
            }
        }

        for (String str : FightOrDieMutationsConfig.DATAGEN.mutated_player_items.get()){
            String[] string = str.split("\\|" );
            ItemStack main = new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(string[0]))));
            if (Math.random() < Integer.parseUnsignedInt(string[1]) / 150F) {
                mainG = main;
            }
        }

        this.setItemSlot(EquipmentSlot.MAINHAND, mainG);
    }


    public void aiStep() {

        super.aiStep();
        Entity attackTarget = this.getTarget();
        Item item = this.getMainHandItem().getItem();
        if (this.isAlive() && attackTarget != null && this.hasLineOfSight(attackTarget) && this.distanceTo(attackTarget) <= 10F && FightOrDieMutationsConfig.SERVER.mutated_player_breaks_blocks.get() && item != null && item instanceof PickaxeItem) {

            if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this)) {
                boolean flag = false;
                AABB aabb = this.getBoundingBox().inflate(0.15F);

                for(BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
                    BlockState blockstate = this.level().getBlockState(blockpos);
                    Block block = blockstate.getBlock();
                    if (this.getRandom().nextInt(80) == 0 && blockstate.getDestroySpeed(level(), blockpos) < getDestroySpeed() && blockstate.getDestroySpeed(level(), blockpos) >= 0)  {
                        this.level().playSound((Player)null, this.blockPosition(), SoundRegistry.ENTITY_ASSIMILATED_ADVENTURER_AMBIENT.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
                        flag = this.level().destroyBlock(blockpos, false, this) || flag;
                    }

                }

            }


        }
    }
    public int getDestroySpeed(){
        return 5;
    }

    @Override
    public void die(DamageSource source) {
        if (Math.random() <= 0.25F) {
            this.DropAdventurerHead(this);
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

    private void DropAdventurerHead(Entity entity) {
        AssimilatedAdventurerHeadEntity assimilatedAdventurerHeadEntity = new AssimilatedAdventurerHeadEntity(EntityRegistry.ASSIMILATED_ADVENTURER_HEAD.get(), entity.level());
        assimilatedAdventurerHeadEntity.moveTo(entity.getX(),entity.getY(),entity.getZ());
        entity.level().addFreshEntity(assimilatedAdventurerHeadEntity);
    }
    private void ShillerExplosion(Entity entity) {
        ShillerEntity shillerEntity = new ShillerEntity(EntityRegistry.SHILLER.get(), entity.level());
        shillerEntity.moveTo(entity.getX(),entity.getY(),entity.getZ());
        entity.level().addFreshEntity(shillerEntity);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.ENTITY_ASSIMILATED_ADVENTURER_AMBIENT.get();
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
