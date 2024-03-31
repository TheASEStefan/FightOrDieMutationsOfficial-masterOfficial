package net.teamabyssalofficial.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeepingVinesPlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.teamabyssalofficial.blocks.*;
import net.teamabyssalofficial.constants.SoundTypeLibrary;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;

import java.util.function.Supplier;

public class BlockRegistry {



    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FightOrDieMutations.MODID);

    public static final RegistryObject<Block> HIVE_BLOCK = registerBlock("hive_block",
            () -> new HiveBlock(BlockBehaviour.Properties.of().strength(0.7f,0.7f)));
    public static final RegistryObject<Block> HIVE_BLOOM = registerBlock("hive_bloom",
            () -> new HiveBloom(BlockBehaviour.Properties.of().strength(0f,0f)));
    public static final RegistryObject<Block> HIVE_TUNNEL_BLOCK = registerBlock("hive_tunnel_block",
            () -> new HiveTunnelBlock(BlockBehaviour.Properties.of().strength(1.1f,1.1f).randomTicks()));
    public static final RegistryObject<Block> BLOOD_SPLASH1 = registerBlock("blood_splash1",
            () -> new BloodSplash1(BlockBehaviour.Properties.of().strength(0f,0f)));
    public static final RegistryObject<Block> BLOOD_SPLASH2 = registerBlock("blood_splash2",
            () -> new BloodSplash2(BlockBehaviour.Properties.of().strength(0f,0f)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
