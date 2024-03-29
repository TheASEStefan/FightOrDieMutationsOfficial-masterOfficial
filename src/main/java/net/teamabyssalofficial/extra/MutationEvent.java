package net.teamabyssalofficial.extra;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.entity.custom.*;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import net.teamabyssalofficial.registry.*;

@Mod.EventBusSubscriber(modid = FightOrDieMutations.MODID)
public class MutationEvent {

    @SubscribeEvent
    public static void MutationEventFDM(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null && !event.getEntity().level().isClientSide) {
            Level world = event.getEntity().level();
            double x = event.getEntity().getX();
            double y = event.getEntity().getY();
            double z = event.getEntity().getZ();
            Entity entity = event.getEntity();
            WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry((ServerLevel) world);
            int currentPhase = worldDataRegistry.getPhase();
            int currentScore = worldDataRegistry.getScore();

            if (entity instanceof Zombie zombie && zombie.hasEffect(EffectRegistry.HIVE_SICKNESS.get()) && !world.isClientSide && FightOrDieMutationsConfig.SERVER.mutated_human_mutation.get() && Math.random() <= 0.95F && event.getSource().getEntity() != null && EntityRegistry.PARASITES.contains(event.getSource().getEntity())) {
                AssimilatedHumanEntity assimilatedHumanEntity = EntityRegistry.ASSIMILATED_HUMAN.get().create(world);
                assert assimilatedHumanEntity != null;
                assimilatedHumanEntity.moveTo(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                world.addFreshEntity(assimilatedHumanEntity);
                zombie.level().playSound((Player) null, zombie.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.2F, 1.0F);
                if (zombie.level() instanceof ServerLevel server) {
                    server.sendParticles(ParticleTypes.EXPLOSION, zombie.getX(), zombie.getY() + 1, zombie.getZ(), 3, 0.4, 1.0, 0.4, 0);
                    if (currentPhase < 3) {
                        worldDataRegistry.setScore(currentScore + 5);
                    }
                    else if (currentPhase >= 3) {
                        worldDataRegistry.setScore(currentScore + 10);
                    }
                }
            }
            else if (entity instanceof Creeper creeper && creeper.hasEffect(EffectRegistry.HIVE_SICKNESS.get()) && !world.isClientSide && FightOrDieMutationsConfig.SERVER.mutated_creeper_mutation.get() && Math.random() <= 0.90F) {
                AssimilatedCreeperEntity assimilatedCreeperEntity = EntityRegistry.ASSIMILATED_CREEPER.get().create(world);
                assert assimilatedCreeperEntity != null;
                assimilatedCreeperEntity.moveTo(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                world.addFreshEntity(assimilatedCreeperEntity);
                creeper.level().playSound((Player) null, creeper.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.2F, 1.0F);
                if (creeper.level() instanceof ServerLevel server) {
                    server.sendParticles(ParticleTypes.EXPLOSION, creeper.getX(), creeper.getY() + 1, creeper.getZ(), 3, 0.4, 1.0, 0.4, 0);
                    if (currentPhase < 3) {
                        worldDataRegistry.setScore(currentScore + 10);
                    }
                    else if (currentPhase >= 3) {
                        worldDataRegistry.setScore(currentScore + 20);
                    }
                }
            }
            else if (entity instanceof Villager villager && villager.hasEffect(EffectRegistry.HIVE_SICKNESS.get()) && !world.isClientSide && FightOrDieMutationsConfig.SERVER.mutated_villager_mutation.get() && Math.random() <= 0.85F && event.getSource().getEntity() != null && EntityRegistry.PARASITES.contains(event.getSource().getEntity())) {
                AssimilatedVillagerEntity assimilatedVillagerEntity = EntityRegistry.ASSIMILATED_VILLAGER.get().create(world);
                assert assimilatedVillagerEntity != null;
                assimilatedVillagerEntity.moveTo(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                world.addFreshEntity(assimilatedVillagerEntity);
                villager.level().playSound((Player) null, villager.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.2F, 1.0F);
                if (villager.level() instanceof ServerLevel server) {
                    server.sendParticles(ParticleTypes.EXPLOSION, villager.getX(), villager.getY() + 1, villager.getZ(), 3, 0.4, 1.0, 0.4, 0);
                    if (currentPhase < 3) {
                        worldDataRegistry.setScore(currentScore + 5);
                    }
                    else if (currentPhase >= 3) {
                        worldDataRegistry.setScore(currentScore + 10);
                    }
                }
            }
            else if (entity instanceof EnderMan enderMan && enderMan.hasEffect(EffectRegistry.HIVE_SICKNESS.get()) && !world.isClientSide && FightOrDieMutationsConfig.SERVER.mutated_enderman_mutation.get() && Math.random() <= 0.75F && event.getSource().getEntity() != null && EntityRegistry.PARASITES.contains(event.getSource().getEntity()) && worldDataRegistry.getPhase() > 2) {
                AssimilatedEndermanEntity assimilatedEndermanEntity = EntityRegistry.ASSIMILATED_ENDERMAN.get().create(world);
                assert assimilatedEndermanEntity != null;
                assimilatedEndermanEntity.moveTo(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                world.addFreshEntity(assimilatedEndermanEntity);
                enderMan.level().playSound((Player) null, enderMan.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.2F, 1.0F);
                if (enderMan.level() instanceof ServerLevel server) {
                    server.sendParticles(ParticleTypes.EXPLOSION, enderMan.getX(), enderMan.getY() + 1, enderMan.getZ(), 3, 0.4, 1.0, 0.4, 0);
                    if (currentPhase < 3) {
                        worldDataRegistry.setScore(currentScore + 20);
                    }
                    else if (currentPhase >= 3) {
                        worldDataRegistry.setScore(currentScore + 40);
                    }
                }
            }
            else if (entity instanceof Player player && player.hasEffect(EffectRegistry.HIVE_SICKNESS.get()) && !world.isClientSide && FightOrDieMutationsConfig.SERVER.mutated_player_mutation.get() && Math.random() <= 0.85F && event.getSource().getEntity() != null && EntityRegistry.PARASITES.contains(event.getSource().getEntity())) {
                Component name = player.getName();
                AssimilatedAdventurerEntity assimilatedAdventurerEntity = EntityRegistry.ASSIMILATED_ADVENTURER.get().create(world);
                ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
                ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
                ItemStack feet = player.getItemBySlot(EquipmentSlot.FEET);
                ItemStack mainHand = player.getItemBySlot(EquipmentSlot.MAINHAND);
                assert assimilatedAdventurerEntity != null;
                assimilatedAdventurerEntity.setItemSlot(EquipmentSlot.HEAD , head);
                assimilatedAdventurerEntity.setItemSlot(EquipmentSlot.LEGS , legs);
                assimilatedAdventurerEntity.setItemSlot(EquipmentSlot.FEET , feet);
                Item item = player.getMainHandItem().getItem();
                if (item instanceof AxeItem || item instanceof SwordItem || item instanceof PickaxeItem) {
                    assimilatedAdventurerEntity.setItemSlot(EquipmentSlot.MAINHAND, mainHand);
                }
                assimilatedAdventurerEntity.moveTo(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                assimilatedAdventurerEntity.setCustomName(name);
                assimilatedAdventurerEntity.setDropChance(EquipmentSlot.HEAD , 0);
                assimilatedAdventurerEntity.setDropChance(EquipmentSlot.LEGS , 0);
                assimilatedAdventurerEntity.setDropChance(EquipmentSlot.FEET , 0);
                assimilatedAdventurerEntity.setDropChance(EquipmentSlot.MAINHAND , 0);
                world.addFreshEntity(assimilatedAdventurerEntity);
                player.level().playSound((Player) null, player.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.2F, 1.0F);
                if (player.level() instanceof ServerLevel server) {
                    server.sendParticles(ParticleTypes.EXPLOSION, player.getX(), player.getY() + 1, player.getZ(), 3, 0.4, 1.0, 0.4, 0);
                    if (currentPhase < 3) {
                        worldDataRegistry.setScore(currentScore + 5);
                    }
                    else if (currentPhase >= 3) {
                        worldDataRegistry.setScore(currentScore + 10);
                    }
                }
            }

        }
    }

}

