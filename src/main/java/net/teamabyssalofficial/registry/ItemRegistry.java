package net.teamabyssalofficial.registry;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import net.teamabyssalofficial.item.*;
import net.teamabyssalofficial.item.categories.BaseItem;

import java.util.ArrayList;
import java.util.List;


public class ItemRegistry {
    public  static  final List<Item> DROP_LOOT_ITEMS = new ArrayList<>();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FightOrDieMutations.MODID);
    public static final RegistryObject<Item> SHILLER_SPAWN_EGG = ITEMS.register("shiller_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.SHILLER, 0x000000, 0x110202, new Item.Properties()));
    public static final RegistryObject<Item> MALRUPTOR_SPAWN_EGG = ITEMS.register("springer_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.MALRUPTOR, 0x000000, 0x110202, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_HUMAN_SPAWN_EGG = ITEMS.register("mutated_human_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_HUMAN, 0x28283e, 0x345350, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_ADVENTURER_SPAWN_EGG = ITEMS.register("mutated_player_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_ADVENTURER, 0x041628, 0x0b3c6e, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_VILLAGER_SPAWN_EGG = ITEMS.register("mutated_villager_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_VILLAGER, 0x281604, 0x6e3d0a, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_ENDERMAN_SPAWN_EGG = ITEMS.register("mutated_enderman_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_ENDERMAN, 0x280428, 0x850d85, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_ENDERMAN_HEAD_SPAWN_EGG = ITEMS.register("mutated_enderman_head_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_ENDERMAN_HEAD, 0x280428, 0x850d85, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_COW_HEAD_SPAWN_EGG = ITEMS.register("mutated_cow_head_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_COW_HEAD, 0x281604, 0x3f2306, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_HUMAN_HEAD_SPAWN_EGG = ITEMS.register("mutated_human_head_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_HUMAN_HEAD, 0x28283e, 0x345350, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_VILLAGER_HEAD_SPAWN_EGG = ITEMS.register("mutated_villager_head_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_VILLAGER_HEAD, 0x281604, 0x6e3d0a, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_ADVENTURER_HEAD_SPAWN_EGG = ITEMS.register("mutated_player_head_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_ADVENTURER_HEAD, 0x041628, 0x0b3c6e, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_CREEPER_HEAD_SPAWN_EGG = ITEMS.register("mutated_creeper_head_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_CREEPER_HEAD, 0x042804, 0x10a510, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_COW_SPAWN_EGG = ITEMS.register("mutated_cow_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_COW, 0x281604, 0x3f2306, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_SHEEP_SPAWN_EGG = ITEMS.register("mutated_sheep_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_SHEEP, 0xfacbcb, 0x560808, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_PIG_SPAWN_EGG = ITEMS.register("mutated_pig_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_PIG, 0x280416, 0x9b1057, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_FOX_SPAWN_EGG = ITEMS.register("mutated_fox_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_FOX, 0x3f2306, 0x9d570e, new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATED_CREEPER_SPAWN_EGG = ITEMS.register("mutated_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.ASSIMILATED_CREEPER, 0x042804, 0x10a510, new Item.Properties()));
    public static final RegistryObject<Item> PRIMORDIAL_TORMENTER_SPAWN_EGG = ITEMS.register("primordial_tormenter_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.SHILLER, 0x000000, 0x000000, new Item.Properties()));
    public static final RegistryObject<Item> DEVELOPED_TORMENTER_SPAWN_EGG = ITEMS.register("developed_tormenter_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.SHILLER, 0x000000, 0x000000, new Item.Properties()));
    public static final RegistryObject<Item> FAILED_FORM_SPAWN_EGG = ITEMS.register("failed_form_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.SHILLER, 0x000000, 0x000000, new Item.Properties()));


    public static final RegistryObject<Item> ADDITION_DEVICE = ITEMS.register("addition_device",
            () -> new AdditionDevice(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SUBTRACTION_DEVICE = ITEMS.register("subtraction_device",
            () -> new SubtractionDevice(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PARASCORE = ITEMS.register("parascore",
            () -> new Parascore(new Item.Properties()));
    public static final RegistryObject<Item> MUTATED_ENDER_PEARL = ITEMS.register("mutated_ender_pearl",
            () -> new MutatedEnderPearl(new Item.Properties()));
    public static final RegistryObject<Item> ASSIMILATION_STAFF = ITEMS.register("mutation_staff",
            () -> new AssimilationStaff(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> INFECTOR_THORAX = ITEMS.register("springer_fragment",
            () -> new BaseItem(new Item.Properties()));
    public static final RegistryObject<Item> RIPPING_FLESH = ITEMS.register("ripping_flesh",
            () -> new BaseItem(new Item.Properties()));
    public static final RegistryObject<Item> MELDED_FLESH = ITEMS.register("melded_flesh",
            () -> new MeldedFlesh(new Item.Properties()));
    public static final RegistryObject<Item> ROTTEN_BRAIN = ITEMS.register("rotten_brain",
            () -> new BaseItem(new Item.Properties()));
    public static final RegistryObject<Item> SICKENED_HEART = ITEMS.register("sickened_heart",
            () -> new BaseItem(new Item.Properties()));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
