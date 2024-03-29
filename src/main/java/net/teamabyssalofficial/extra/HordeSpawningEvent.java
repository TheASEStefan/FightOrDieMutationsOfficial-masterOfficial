package net.teamabyssalofficial.extra;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import net.teamabyssalofficial.registry.HordeSpawnerRegistry;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = FightOrDieMutations.MODID)
public class HordeSpawningEvent {
    private static Map<ResourceLocation, HordeSpawnerRegistry> spawners = new HashMap<>();


    @SubscribeEvent
    public static void onServerStart(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        spawners.put(Level.OVERWORLD.location(), new HordeSpawnerRegistry(server));
    }



    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        spawners.clear();
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.START)
            return;

        if (event.side != LogicalSide.SERVER)
            return;

        HordeSpawnerRegistry spawner = spawners.get(event.level.dimension().location());
        if (spawner != null) {
            spawner.tick(event.level);
        }
    }

}
