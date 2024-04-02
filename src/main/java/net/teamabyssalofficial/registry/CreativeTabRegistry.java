package net.teamabyssalofficial.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;


public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FightOrDieMutations.MODID);



    public static final RegistryObject<CreativeModeTab> ITEM = TABS.register("items", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + FightOrDieMutations.MODID + ".item")).icon(() -> new ItemStack(ItemRegistry.SICKENED_HEART.get())).displayItems((enabledFeatures, entries) -> {

        entries.accept(ItemRegistry.SHILLER_SPAWN_EGG.get());
        entries.accept(ItemRegistry.MALRUPTOR_SPAWN_EGG.get());

        entries.accept(ItemRegistry.ASSIMILATED_COW_SPAWN_EGG.get());
        entries.accept(ItemRegistry.ASSIMILATED_SHEEP_SPAWN_EGG.get());
        entries.accept(ItemRegistry.ASSIMILATED_PIG_SPAWN_EGG.get());
        entries.accept(ItemRegistry.ASSIMILATED_FOX_SPAWN_EGG.get());

        entries.accept(ItemRegistry.ASSIMILATED_HUMAN_SPAWN_EGG.get());
        entries.accept(ItemRegistry.ASSIMILATED_ADVENTURER_SPAWN_EGG.get());
        entries.accept(ItemRegistry.ASSIMILATED_VILLAGER_SPAWN_EGG.get());

        entries.accept(ItemRegistry.ASSIMILATED_COW_HEAD_SPAWN_EGG.get());
        entries.accept(ItemRegistry.ASSIMILATED_HUMAN_HEAD_SPAWN_EGG.get());
        entries.accept(ItemRegistry.ASSIMILATED_ADVENTURER_HEAD_SPAWN_EGG.get());
        entries.accept(ItemRegistry.ASSIMILATED_VILLAGER_HEAD_SPAWN_EGG.get());
        entries.accept(ItemRegistry.ASSIMILATED_ENDERMAN_HEAD_SPAWN_EGG.get());
        entries.accept(ItemRegistry.ASSIMILATED_CREEPER_HEAD_SPAWN_EGG.get());

        entries.accept(ItemRegistry.ASSIMILATED_CREEPER_SPAWN_EGG.get());
        entries.accept(ItemRegistry.ASSIMILATED_ENDERMAN_SPAWN_EGG.get());

        entries.accept(ItemRegistry.PRIMORDIAL_TORMENTER_SPAWN_EGG.get());
        entries.accept(ItemRegistry.DEVELOPED_TORMENTER_SPAWN_EGG.get());
        entries.accept(ItemRegistry.FAILED_FORM_SPAWN_EGG.get());

        entries.accept(ItemRegistry.ADDITION_DEVICE.get());
        entries.accept(ItemRegistry.SUBTRACTION_DEVICE.get());
        entries.accept(ItemRegistry.PARASCORE.get());
        entries.accept(ItemRegistry.ASSIMILATION_STAFF.get());

        entries.accept(ItemRegistry.INFECTOR_THORAX.get());
        entries.accept(ItemRegistry.RIPPING_FLESH.get());
        entries.accept(ItemRegistry.SICKENED_HEART.get());
        entries.accept(ItemRegistry.ROTTEN_BRAIN.get());

        entries.accept(ItemRegistry.MUTATED_ENDER_PEARL.get());

        entries.accept(ItemRegistry.MELDED_FLESH.get());

        entries.accept(BlockRegistry.HIVE_BLOCK.get());
        entries.accept(BlockRegistry.HIVE_TUNNEL_BLOCK.get());
        entries.accept(BlockRegistry.HIVE_BLOOM.get());

        entries.accept(BlockRegistry.BLOOD_SPLASH1.get());
        entries.accept(BlockRegistry.BLOOD_SPLASH2.get());

    }).build());

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }

}
