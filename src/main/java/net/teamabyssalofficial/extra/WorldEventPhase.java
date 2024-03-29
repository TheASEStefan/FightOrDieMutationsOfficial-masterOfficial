package net.teamabyssalofficial.extra;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.teamabyssalofficial.registry.WorldDataRegistry;

public class WorldEventPhase {
    public static void serverWorldTick(ServerLevel world, ServerPlayer player) {
            WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(world);
            worldDataRegistry.phaseHandlerEvent(world, player);
    }
}
