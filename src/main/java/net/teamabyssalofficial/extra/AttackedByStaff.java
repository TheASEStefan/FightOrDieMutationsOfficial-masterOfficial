package net.teamabyssalofficial.extra;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.entity.custom.*;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import net.teamabyssalofficial.registry.EntityRegistry;
import net.teamabyssalofficial.registry.ItemRegistry;

@Mod.EventBusSubscriber(modid = FightOrDieMutations.MODID)
public class AttackedByStaff {
    @SubscribeEvent
    public static void OnAttackEvent(LivingAttackEvent event) {
        if (event != null && event.getEntity() != null) {
            Entity entity = event.getEntity();
            Level world = entity.level();
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();
            if (event.getSource().getEntity() instanceof Player player && entity != null && !world.isClientSide) {
                if (player.getMainHandItem() != ItemStack.EMPTY && player.getMainHandItem().getItem() == ItemRegistry.ASSIMILATION_STAFF.get()) {

                    if (entity instanceof Zombie zombie && !world.isClientSide && FightOrDieMutationsConfig.SERVER.mutated_human_mutation.get()) {
                        AssimilatedHumanEntity assimilatedHumanEntity = EntityRegistry.ASSIMILATED_HUMAN.get().create(world);
                        assert assimilatedHumanEntity != null;
                        assimilatedHumanEntity.moveTo(x, y, z);
                        world.addFreshEntity(assimilatedHumanEntity);
                        zombie.level().playSound((Player) null, zombie.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.2F, 1.0F);
                        zombie.discard();
                        if (zombie.level() instanceof ServerLevel server) {
                            server.sendParticles(ParticleTypes.EXPLOSION, zombie.getX(), zombie.getY() + 1, zombie.getZ(), 3, 0.4, 1.0, 0.4, 0);
                        }
                    }
                    else if (entity instanceof Creeper creeper && !world.isClientSide && FightOrDieMutationsConfig.SERVER.mutated_creeper_mutation.get()) {
                        AssimilatedCreeperEntity assimilatedCreeperEntity = EntityRegistry.ASSIMILATED_CREEPER.get().create(world);
                        assert assimilatedCreeperEntity != null;
                        assimilatedCreeperEntity.moveTo(x, y, z);
                        world.addFreshEntity(assimilatedCreeperEntity);
                        creeper.level().playSound((Player) null, creeper.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.2F, 1.0F);
                        creeper.discard();
                        if (creeper.level() instanceof ServerLevel server) {
                            server.sendParticles(ParticleTypes.EXPLOSION, creeper.getX(), creeper.getY() + 1, creeper.getZ(), 3, 0.4, 1.0, 0.4, 0);
                        }
                    }
                    else if (entity instanceof Villager villager && !world.isClientSide && FightOrDieMutationsConfig.SERVER.mutated_villager_mutation.get()) {
                        AssimilatedVillagerEntity assimilatedVillagerEntity = EntityRegistry.ASSIMILATED_VILLAGER.get().create(world);
                        assert assimilatedVillagerEntity != null;
                        assimilatedVillagerEntity.moveTo(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                        world.addFreshEntity(assimilatedVillagerEntity);
                        villager.level().playSound((Player) null, villager.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.2F, 1.0F);
                        villager.discard();
                        if (villager.level() instanceof ServerLevel server) {
                            server.sendParticles(ParticleTypes.EXPLOSION, villager.getX(), villager.getY() + 1, villager.getZ(), 3, 0.4, 1.0, 0.4, 0);
                        }
                    }
                    else if (entity instanceof Cow cow && !world.isClientSide && FightOrDieMutationsConfig.SERVER.mutated_cow_mutation.get()) {
                        AssimilatedCowEntity assimilatedCowEntity = EntityRegistry.ASSIMILATED_COW.get().create(world);
                        assert assimilatedCowEntity != null;
                        assimilatedCowEntity.moveTo(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                        world.addFreshEntity(assimilatedCowEntity);
                        cow.level().playSound((Player) null, cow.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.2F, 1.0F);
                        cow.discard();
                        if (cow.level() instanceof ServerLevel server) {
                            server.sendParticles(ParticleTypes.EXPLOSION, cow.getX(), cow.getY() + 1, cow.getZ(), 3, 0.4, 1.0, 0.4, 0);
                        }
                    }
                    else if (entity instanceof Sheep sheep && !world.isClientSide && FightOrDieMutationsConfig.SERVER.mutated_sheep_mutation.get()) {
                        AssimilatedSheepEntity assimilatedSheepEntity = EntityRegistry.ASSIMILATED_SHEEP.get().create(world);
                        assert assimilatedSheepEntity != null;
                        assimilatedSheepEntity.moveTo(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                        world.addFreshEntity(assimilatedSheepEntity);
                        sheep.level().playSound((Player) null, sheep.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.2F, 1.0F);
                        sheep.discard();
                        if (sheep.level() instanceof ServerLevel server) {
                            server.sendParticles(ParticleTypes.EXPLOSION, sheep.getX(), sheep.getY() + 1, sheep.getZ(), 3, 0.4, 1.0, 0.4, 0);
                        }
                    }
                    else if (entity instanceof Pig pig && !world.isClientSide && FightOrDieMutationsConfig.SERVER.mutated_pig_mutation.get()) {
                        AssimilatedPigEntity assimilatedPigEntity = EntityRegistry.ASSIMILATED_PIG.get().create(world);
                        assert assimilatedPigEntity != null;
                        assimilatedPigEntity.moveTo(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                        world.addFreshEntity(assimilatedPigEntity);
                        pig.level().playSound((Player) null, pig.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.2F, 1.0F);
                        pig.discard();
                        if (pig.level() instanceof ServerLevel server) {
                            server.sendParticles(ParticleTypes.EXPLOSION, pig.getX(), pig.getY() + 1, pig.getZ(), 3, 0.4, 1.0, 0.4, 0);
                        }
                    }
                    else if (entity instanceof Fox fox && !world.isClientSide && FightOrDieMutationsConfig.SERVER.mutated_fox_mutation.get()) {
                        AssimilatedFoxEntity assimilatedFoxEntity = EntityRegistry.ASSIMILATED_FOX.get().create(world);
                        assert assimilatedFoxEntity != null;
                        assimilatedFoxEntity.moveTo(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                        world.addFreshEntity(assimilatedFoxEntity);
                        fox.level().playSound((Player) null, fox.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.2F, 1.0F);
                        fox.discard();
                        if (fox.level() instanceof ServerLevel server) {
                            server.sendParticles(ParticleTypes.EXPLOSION, fox.getX(), fox.getY() + 1, fox.getZ(), 3, 0.4, 1.0, 0.4, 0);
                        }
                    }
                    else if (entity instanceof EnderMan enderMan && !world.isClientSide && FightOrDieMutationsConfig.SERVER.mutated_enderman_mutation.get()) {
                        AssimilatedEndermanEntity assimilatedEndermanEntity = EntityRegistry.ASSIMILATED_ENDERMAN.get().create(world);
                        assert assimilatedEndermanEntity != null;
                        assimilatedEndermanEntity.moveTo(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                        world.addFreshEntity(assimilatedEndermanEntity);
                        enderMan.level().playSound((Player) null, enderMan.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.2F, 1.0F);
                        enderMan.discard();
                        if (enderMan.level() instanceof ServerLevel server) {
                            server.sendParticles(ParticleTypes.EXPLOSION, enderMan.getX(), enderMan.getY() + 1, enderMan.getZ(), 3, 0.4, 1.0, 0.4, 0);
                        }
                    }

                }
            }
        }
    }
    
}
