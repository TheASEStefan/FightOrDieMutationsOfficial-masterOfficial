package net.teamabyssalofficial.registry;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;

@EventBusSubscriber(modid = FightOrDieMutations.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FightOrDieMutations.MODID);
    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }

    private static RegistryObject<SoundEvent> soundRegistry(String id) {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FightOrDieMutations.MODID, id)));
    }

    public static final RegistryObject<SoundEvent> HIVE_SOUNDS  = soundRegistry("block.hive_sounds");
    public static final RegistryObject<SoundEvent> HORDE_SPAWNED  = soundRegistry("horde_spawned");
    public static final RegistryObject<SoundEvent> ENTITY_MALRUPTOR_AMBIENT  = soundRegistry("entity.malruptor.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_MALRUPTOR_HURT  = soundRegistry("entity.malruptor.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_MALRUPTOR_DEATH = soundRegistry("entity.malruptor.death");
    public static final RegistryObject<SoundEvent> ENTITY_MALRUPTOR_INFECT = soundRegistry("entity.malruptor.infect");
    public static final RegistryObject<SoundEvent> ENTITY_ASSIMILATED_HUMAN_AMBIENT  = soundRegistry("entity.mutated_human.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_ASSIMILATED_ADVENTURER_AMBIENT  = soundRegistry("entity.mutated_player.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_ASSIMILATED_ENDERMAN_HEAD_AMBIENT  = soundRegistry("entity.mutated_enderman_head.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_ASSIMILATED_ENDERMAN_AMBIENT  = soundRegistry("entity.mutated_enderman.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_ASSIMILATED_ENDERMAN_DEATH  = soundRegistry("entity.mutated_enderman.death");
    public static final RegistryObject<SoundEvent> ENTITY_ASSIMILATED_ENDERMAN_HURT  = soundRegistry("entity.mutated_enderman.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_ASSIMILATED_COW_AMBIENT  = soundRegistry("entity.mutated_cow.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_ASSIMILATED_SHEEP_AMBIENT  = soundRegistry("entity.mutated_sheep.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_ASSIMILATED_PIG_AMBIENT  = soundRegistry("entity.mutated_pig.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_ASSIMILATED_FOX_AMBIENT  = soundRegistry("entity.mutated_fox.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_ASSIMILATED_VILLAGER_AMBIENT  = soundRegistry("entity.mutated_villager.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_ASSIMILATED_CREEPER_AMBIENT  = soundRegistry("entity.mutated_creeper.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_ASSIMILATED_CREEPER_DEATH  = soundRegistry("entity.mutated_creeper.death");

    public static final RegistryObject<SoundEvent> ENTITY_ASSIMILATED_HURT  = soundRegistry("entity.mutated.hurt");

    public static final RegistryObject<SoundEvent> ENTITY_EXPLOSION = soundRegistry("entity.explosion");
    public static final RegistryObject<SoundEvent> ENTITY_TURN = soundRegistry("entity.turn");
    public static final RegistryObject<SoundEvent> STOMACH_GROWL = soundRegistry("stomach_growl");
    public static final RegistryObject<SoundEvent> HEAD_AMBIENT = soundRegistry("entity.head.ambient");
    public static final RegistryObject<SoundEvent> HUMANOID_DEATH = soundRegistry("humanoid.death");
    public static final RegistryObject<SoundEvent> ASSIMILATED_ANIMAL_AMBIENT = soundRegistry("mutated.animal.ambient");
    public static final RegistryObject<SoundEvent> PHASE0 = soundRegistry("player_phase0");
    public static final RegistryObject<SoundEvent> PHASE1 = soundRegistry("player_phase1");
    public static final RegistryObject<SoundEvent> PHASE2 = soundRegistry("player_phase2");
    public static final RegistryObject<SoundEvent> PHASE3 = soundRegistry("player_phase3");
    public static final RegistryObject<SoundEvent> PHASE4 = soundRegistry("player_phase4");
    public static final RegistryObject<SoundEvent> PHASE5 = soundRegistry("player_phase5");
    public static final RegistryObject<SoundEvent> JUMPSCARE_1 = soundRegistry("jumpscare_1");
    public static final RegistryObject<SoundEvent> JUMPSCARE_2 = soundRegistry("jumpscare_2");
    public static final RegistryObject<SoundEvent> JUMPSCARE_3 = soundRegistry("jumpscare_3");

}
