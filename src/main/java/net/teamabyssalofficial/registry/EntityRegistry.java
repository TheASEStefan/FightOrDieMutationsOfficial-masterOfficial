package net.teamabyssalofficial.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.entity.custom.*;
import net.teamabyssalofficial.extra.ScreenShakeEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;

import java.util.ArrayList;
import java.util.List;

public class EntityRegistry {


    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FightOrDieMutations.MODID);


    public static final MobCategory PARASITE = MobCategory.create("parasite","parasite", FightOrDieMutationsConfig.SERVER.mob_cap.get(),false,false,128);

    public  static  final List<Entity> PARASITES = new ArrayList<>();

    public static final RegistryObject<EntityType<ScreenShakeEntity>> SCREEN_SHAKE = ENTITY_TYPES.register("screen_shake", () -> EntityType.Builder.<ScreenShakeEntity>of(ScreenShakeEntity::new, MobCategory.MISC)
            .noSummon()
            .sized(1.0f, 1.0f)
            .setUpdateInterval(Integer.MAX_VALUE)
            .build(FightOrDieMutations.MODID + ":screen_shake"));

    public static final RegistryObject<EntityType<ShillerEntity>> SHILLER =
            ENTITY_TYPES.register("shiller",
                    () -> EntityType.Builder.of(ShillerEntity::new, PARASITE)
                            .sized(0.6f, 0.6f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "shiller").toString()));
    public static final RegistryObject<EntityType<MalruptorEntity>> MALRUPTOR =
            ENTITY_TYPES.register("springer",
                    () -> EntityType.Builder.of(MalruptorEntity::new, PARASITE)
                            .sized(0.8f, 1.1f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "springer").toString()));
    public static final RegistryObject<EntityType<AssimilatedHumanEntity>> ASSIMILATED_HUMAN =
            ENTITY_TYPES.register("mutated_human",
                    () -> EntityType.Builder.of(AssimilatedHumanEntity::new, PARASITE)
                            .sized(0.8f, 1.9f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "mutated_human").toString()));
    public static final RegistryObject<EntityType<AssimilatedAdventurerEntity>> ASSIMILATED_ADVENTURER =
            ENTITY_TYPES.register("mutated_player",
                    () -> EntityType.Builder.of(AssimilatedAdventurerEntity::new, PARASITE)
                            .sized(0.9f, 1.9f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "mutated_player").toString()));
    public static final RegistryObject<EntityType<AssimilatedVillagerEntity>> ASSIMILATED_VILLAGER =
            ENTITY_TYPES.register("mutated_villager",
                    () -> EntityType.Builder.of(AssimilatedVillagerEntity::new, PARASITE)
                            .sized(0.8f, 2.0f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "mutated_villager").toString()));
    public static final RegistryObject<EntityType<AssimilatedEndermanEntity>> ASSIMILATED_ENDERMAN =
            ENTITY_TYPES.register("mutated_enderman",
                    () -> EntityType.Builder.of(AssimilatedEndermanEntity::new, PARASITE)
                            .sized(0.9f, 3.2f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "mutated_enderman").toString()));
    public static final RegistryObject<EntityType<AssimilatedHumanHeadEntity>> ASSIMILATED_HUMAN_HEAD =
            ENTITY_TYPES.register("mutated_human_head",
                    () -> EntityType.Builder.of(AssimilatedHumanHeadEntity::new, PARASITE)
                            .sized(0.7f, 0.8f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "mutated_human_head").toString()));
    public static final RegistryObject<EntityType<AssimilatedVillagerHeadEntity>> ASSIMILATED_VILLAGER_HEAD =
            ENTITY_TYPES.register("mutated_villager_head",
                    () -> EntityType.Builder.of(AssimilatedVillagerHeadEntity::new, PARASITE)
                            .sized(0.7f, 0.8f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "mutated_villager_head").toString()));
    public static final RegistryObject<EntityType<AssimilatedAdventurerHeadEntity>> ASSIMILATED_ADVENTURER_HEAD =
            ENTITY_TYPES.register("mutated_player_head",
                    () -> EntityType.Builder.of(AssimilatedAdventurerHeadEntity::new, PARASITE)
                            .sized(0.7f, 0.8f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "mutated_player_head").toString()));
    public static final RegistryObject<EntityType<AssimilatedEndermanHeadEntity>> ASSIMILATED_ENDERMAN_HEAD =
            ENTITY_TYPES.register("mutated_enderman_head",
                    () -> EntityType.Builder.of(AssimilatedEndermanHeadEntity::new, PARASITE)
                            .sized(0.95f, 1.35f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "mutated_enderman_head").toString()));
    public static final RegistryObject<EntityType<AssimilatedCreeperHeadEntity>> ASSIMILATED_CREEPER_HEAD =
            ENTITY_TYPES.register("mutated_creeper_head",
                    () -> EntityType.Builder.of(AssimilatedCreeperHeadEntity::new, PARASITE)
                            .sized(0.75f, 0.85f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "mutated_creeper_head").toString()));
    public static final RegistryObject<EntityType<AssimilatedCowEntity>> ASSIMILATED_COW =
            ENTITY_TYPES.register("mutated_cow",
                    () -> EntityType.Builder.of(AssimilatedCowEntity::new, PARASITE)
                            .sized(1.0f, 1.5f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "mutated_cow").toString()));
    public static final RegistryObject<EntityType<AssimilatedSheepEntity>> ASSIMILATED_SHEEP =
            ENTITY_TYPES.register("mutated_sheep",
                    () -> EntityType.Builder.of(AssimilatedSheepEntity::new, PARASITE)
                            .sized(0.9f, 1.4f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "mutated_sheep").toString()));
    public static final RegistryObject<EntityType<AssimilatedPigEntity>> ASSIMILATED_PIG =
            ENTITY_TYPES.register("mutated_pig",
                    () -> EntityType.Builder.of(AssimilatedPigEntity::new, PARASITE)
                            .sized(0.8f, 1.1f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "mutated_pig").toString()));
    public static final RegistryObject<EntityType<AssimilatedFoxEntity>> ASSIMILATED_FOX =
            ENTITY_TYPES.register("mutated_fox",
                    () -> EntityType.Builder.of(AssimilatedFoxEntity::new, PARASITE)
                            .sized(1.0f, 0.9f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "mutated_fox").toString()));
    public static final RegistryObject<EntityType<AssimilatedCreeperEntity>> ASSIMILATED_CREEPER =
            ENTITY_TYPES.register("mutated_creeper",
                    () -> EntityType.Builder.of(AssimilatedCreeperEntity::new, PARASITE)
                            .sized(0.8f, 2.0f)
                            .build(new ResourceLocation(FightOrDieMutations.MODID, "mutated_creeper").toString()));
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
