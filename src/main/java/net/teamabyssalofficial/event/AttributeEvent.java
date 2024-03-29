package net.teamabyssalofficial.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.teamabyssalofficial.entity.custom.*;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import net.teamabyssalofficial.registry.EntityRegistry;

@Mod.EventBusSubscriber(modid = FightOrDieMutations.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeEvent {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {

        event.put(EntityRegistry.SHILLER.get(), ShillerEntity.createAttributes().build());
        event.put(EntityRegistry.MALRUPTOR.get(), MalruptorEntity.createAttributes().build());
        event.put(EntityRegistry.ASSIMILATED_HUMAN.get(), AssimilatedHumanEntity.createAttributes().build());
        event.put(EntityRegistry.ASSIMILATED_VILLAGER.get(), AssimilatedVillagerEntity.createAttributes().build());
        event.put(EntityRegistry.ASSIMILATED_COW.get(), AssimilatedCowEntity.createAttributes().build());
        event.put(EntityRegistry.ASSIMILATED_SHEEP.get(), AssimilatedSheepEntity.createAttributes().build());
        event.put(EntityRegistry.ASSIMILATED_PIG.get(), AssimilatedPigEntity.createAttributes().build());
        event.put(EntityRegistry.ASSIMILATED_FOX.get(), AssimilatedFoxEntity.createAttributes().build());
        event.put(EntityRegistry.ASSIMILATED_HUMAN_HEAD.get(), AssimilatedHumanHeadEntity.createAttributes().build());
        event.put(EntityRegistry.ASSIMILATED_VILLAGER_HEAD.get(), AssimilatedVillagerHeadEntity.createAttributes().build());
        event.put(EntityRegistry.ASSIMILATED_ADVENTURER_HEAD.get(), AssimilatedAdventurerHeadEntity.createAttributes().build());
        event.put(EntityRegistry.ASSIMILATED_ENDERMAN_HEAD.get(), AssimilatedEndermanHeadEntity.createAttributes().build());
        event.put(EntityRegistry.ASSIMILATED_CREEPER_HEAD.get(), AssimilatedCreeperHeadEntity.createAttributes().build());
        event.put(EntityRegistry.ASSIMILATED_CREEPER.get(), AssimilatedCreeperEntity.createAttributes().build());
        event.put(EntityRegistry.ASSIMILATED_ADVENTURER.get(), AssimilatedAdventurerEntity.createAttributes().build());
        event.put(EntityRegistry.ASSIMILATED_ENDERMAN.get(), AssimilatedEndermanEntity.createAttributes().build());

    }

}
