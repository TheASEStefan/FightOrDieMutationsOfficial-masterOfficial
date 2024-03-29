package net.teamabyssalofficial.extra;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import net.teamabyssalofficial.registry.GameruleRegistry;
import net.teamabyssalofficial.registry.WorldDataRegistry;

@Mod.EventBusSubscriber(modid = FightOrDieMutations.MODID)
public class GamerulesEvents {
    @SubscribeEvent
    public static void LevelTickGameEvent(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.level instanceof ServerLevel world) {
            WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(world);
            int currentScore = worldDataRegistry.getScore();
            if (world.getLevelData().getGameRules().getBoolean(GameruleRegistry.CONSTANT_ZERO_EVOLUTION_POINTS))
                if (currentScore != 0)
                    worldDataRegistry.setScore(0);

        }
    }
}
