package net.teamabyssalofficial.extra;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.teamabyssalofficial.entity.categories.*;
import net.teamabyssalofficial.entity.custom.*;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import net.teamabyssalofficial.registry.EffectRegistry;
import net.teamabyssalofficial.registry.EntityRegistry;
import net.teamabyssalofficial.registry.WorldDataRegistry;

@Mod.EventBusSubscriber(modid = FightOrDieMutations.MODID)
public class LivingSpawnedModifications {
    @SubscribeEvent()
    public static void addSpawn(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            Level world = entity.level();
            if (!world.isClientSide && world instanceof ServerLevel serverLevel && !EntityRegistry.PARASITES.contains(entity)) {
                WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(serverLevel);
                int currentPhase = worldDataRegistry.getPhase();
                if (currentPhase == 4) {
                    entity.addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 3600, 0), entity);
                } else if (currentPhase == 5) {
                    entity.addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 4800, 0), entity);
                } else if (currentPhase > 5) {
                    entity.addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 6000, 0), entity);
                }
            }
        }
        if (event.getEntity() instanceof Villager) {
            Villager abstractVillager = (Villager) event.getEntity();
            abstractVillager.goalSelector.addGoal(1, new AvoidEntityGoal(abstractVillager, Infector.class, 16.0F, 0.7F, 0.75F));
            abstractVillager.goalSelector.addGoal(1, new AvoidEntityGoal(abstractVillager, Parasite.class, 16.0F, 0.7F, 0.75F));
            abstractVillager.goalSelector.addGoal(1, new AvoidEntityGoal(abstractVillager, Mutated.class, 16.0F, 0.7F, 0.75F));
            abstractVillager.goalSelector.addGoal(1, new AvoidEntityGoal(abstractVillager, Head.class, 16.0F, 0.7F, 0.75F));
            abstractVillager.goalSelector.addGoal(1, new AvoidEntityGoal(abstractVillager, AdvancedMutated.class, 16.0F, 0.7F, 0.75F));
        }
        else if (event.getEntity() instanceof WanderingTrader) {
            WanderingTrader wanderingTraderEntity = (WanderingTrader) event.getEntity();
            wanderingTraderEntity.goalSelector.addGoal(1, new AvoidEntityGoal(wanderingTraderEntity, Infector.class, 16.0F, 0.7F, 0.75F));
            wanderingTraderEntity.goalSelector.addGoal(1, new AvoidEntityGoal(wanderingTraderEntity, Parasite.class, 16.0F, 0.7F, 0.75F));
            wanderingTraderEntity.goalSelector.addGoal(1, new AvoidEntityGoal(wanderingTraderEntity, Mutated.class, 16.0F, 0.7F, 0.75F));
            wanderingTraderEntity.goalSelector.addGoal(1, new AvoidEntityGoal(wanderingTraderEntity, Head.class, 16.0F, 0.7F, 0.75F));
            wanderingTraderEntity.goalSelector.addGoal(1, new AvoidEntityGoal(wanderingTraderEntity, AdvancedMutated.class, 16.0F, 0.7F, 0.75F));
        }
        else if (event.getEntity() instanceof Zombie) {
            Zombie zombie = (Zombie) event.getEntity();
            zombie.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(zombie, Infector.class, true));
            zombie.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(zombie, Parasite.class, true));
            zombie.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(zombie, Mutated.class, true));
            zombie.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(zombie, Head.class, true));
            zombie.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(zombie, AdvancedMutated.class, true));
        }
        else if (event.getEntity() instanceof IronGolem) {
            IronGolem ironGolem = (IronGolem) event.getEntity();
            ironGolem.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(ironGolem, Infector.class, true));
            ironGolem.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(ironGolem, Parasite.class, true));
            ironGolem.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(ironGolem, Mutated.class, true));
            ironGolem.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(ironGolem, Head.class, true));
            ironGolem.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(ironGolem, AdvancedMutated.class, true));
        }
        else if (event.getEntity() instanceof AssimilatedCowHeadEntity) {
            AssimilatedCowHeadEntity assimilatedCowHeadEntity = (AssimilatedCowHeadEntity) event.getEntity();
            Level world = assimilatedCowHeadEntity.level();
            if (!world.isClientSide) {
                if (Math.random() <= 0.55F) {
                    assimilatedCowHeadEntity.setKills(assimilatedCowHeadEntity.getKills() + assimilatedCowHeadEntity.getRandom().nextInt(3));
                }
            }
        }
        else if (event.getEntity() instanceof AssimilatedHumanHeadEntity) {
            AssimilatedHumanHeadEntity assimilatedHumanHeadEntity = (AssimilatedHumanHeadEntity) event.getEntity();
            Level world = assimilatedHumanHeadEntity.level();
            if (!world.isClientSide) {
                if (Math.random() <= 0.55F) {
                    assimilatedHumanHeadEntity.setKills(assimilatedHumanHeadEntity.getKills() + assimilatedHumanHeadEntity.getRandom().nextInt(3));
                }
            }
        }
        else if (event.getEntity() instanceof AssimilatedVillagerHeadEntity) {
            AssimilatedVillagerHeadEntity assimilatedVillagerHeadEntity = (AssimilatedVillagerHeadEntity) event.getEntity();
            Level world = assimilatedVillagerHeadEntity.level();
            if (!world.isClientSide) {
                if (Math.random() <= 0.55F) {
                    assimilatedVillagerHeadEntity.setKills(assimilatedVillagerHeadEntity.getKills() + assimilatedVillagerHeadEntity.getRandom().nextInt(3));
                }
            }
        }
        else if (event.getEntity() instanceof AssimilatedAdventurerHeadEntity) {
            AssimilatedAdventurerHeadEntity assimilatedAdventurerHeadEntity = (AssimilatedAdventurerHeadEntity) event.getEntity();
            Level world = assimilatedAdventurerHeadEntity.level();
            if (!world.isClientSide) {
                if (Math.random() <= 0.55F) {
                    assimilatedAdventurerHeadEntity.setKills(assimilatedAdventurerHeadEntity.getKills() + assimilatedAdventurerHeadEntity.getRandom().nextInt(3));
                }
            }
        }
        else if (event.getEntity() instanceof AssimilatedEndermanHeadEntity) {
            AssimilatedEndermanHeadEntity assimilatedEndermanHeadEntity = (AssimilatedEndermanHeadEntity) event.getEntity();
            Level world = assimilatedEndermanHeadEntity.level();
            if (!world.isClientSide) {
                if (Math.random() <= 0.55F) {
                    assimilatedEndermanHeadEntity.setKills(assimilatedEndermanHeadEntity.getKills() + assimilatedEndermanHeadEntity.getRandom().nextInt(3));
                }
            }
        }
        else if (event.getEntity() instanceof AssimilatedCreeperHeadEntity) {
            AssimilatedCreeperHeadEntity assimilatedCreeperHeadEntity = (AssimilatedCreeperHeadEntity) event.getEntity();
            Level world = assimilatedCreeperHeadEntity.level();
            if (!world.isClientSide) {
                if (Math.random() <= 0.55F) {
                    assimilatedCreeperHeadEntity.setKills(assimilatedCreeperHeadEntity.getKills() + assimilatedCreeperHeadEntity.getRandom().nextInt(3));
                }
            }
        }





    }
}
