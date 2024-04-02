package net.teamabyssalofficial.extra;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import net.teamabyssalofficial.registry.WorldDataRegistry;

@Mod.EventBusSubscriber(modid = FightOrDieMutations.MODID)
public class PlayerPhaseEvent {

    @SubscribeEvent
    public static void PhaseEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player instanceof ServerPlayer player && event.player.level() instanceof ServerLevel world) {
            WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(world);
            if (worldDataRegistry.canShowPhases()) {
                WorldEventPhase.serverWorldTick(world, player);
            }
        }
    }


    @SubscribeEvent
    public static void PointIncrementEvent(TickEvent.LevelTickEvent event) {
        if(event.phase == TickEvent.Phase.END && event.level instanceof ServerLevel world) {
            WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(world);
            int currentPhase = worldDataRegistry.getPhase();
            int currentScore = worldDataRegistry.getScore();
            int main_score = 5;
            if (event.level.getDifficulty() == Difficulty.NORMAL) {
                if (Math.random() < 0.02F) {
                    switch (currentPhase) {
                        case 0 -> main_score = 1;
                        case 1 -> main_score = 2;
                        case 2 -> main_score = 4;
                        case 3 -> main_score = 7;
                        case 4 -> main_score = 9;
                        case 5 -> main_score = 22;
                    }
                }
                else if (Math.random() < 0.01F) {
                    switch (currentPhase) {
                        case 0 -> main_score = 1;
                        case 1 -> main_score = 3;
                        case 2 -> main_score = 5;
                        case 3 -> main_score = 7;
                        case 4 -> main_score = 14;
                        case 5 -> main_score = 27;
                    }
                }
            }
            else if (event.level.getDifficulty() == Difficulty.HARD) {
                if (Math.random() < 0.02F) {
                    switch (currentPhase) {
                        case 0 -> main_score = 2;
                        case 1 -> main_score = 4;
                        case 2 -> main_score = 7;
                        case 3 -> main_score = 10;
                        case 4 -> main_score = 15;
                        case 5 -> main_score = 28;
                    }
                }
                else if (Math.random() < 0.01F) {
                    switch (currentPhase) {
                        case 0 -> main_score = 2;
                        case 1 -> main_score = 5;
                        case 2 -> main_score = 8;
                        case 3 -> main_score = 12;
                        case 4 -> main_score = 21;
                        case 5 -> main_score = 45;
                    }
                }
            }

            if (Math.random() <= 0.0099F) {
                worldDataRegistry.setScore(currentScore + main_score);
            }
        }
    }
    @SubscribeEvent
    public static void SleepEvent(PlayerSleepInBedEvent event) {
        if (event.getEntity() != null && event.getEntity() instanceof ServerPlayer player) {
            WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry((ServerLevel) player.level());
            int currentPhase = worldDataRegistry.getPhase();
            int currentScore = worldDataRegistry.getScore();
            switch (currentPhase) {
                case 0 -> {
                    worldDataRegistry.setScore(currentScore + 5);
                    worldDataRegistry.setDirty();
                }
                case 1 -> {
                    worldDataRegistry.setScore(currentScore + 10);
                    worldDataRegistry.setDirty();
                }
                case 2 -> {
                    worldDataRegistry.setScore(currentScore + 20);
                    worldDataRegistry.setDirty();
                }
                case 3 -> {
                    worldDataRegistry.setScore(currentScore + 40);
                    worldDataRegistry.setDirty();
                }
                case 4 -> {
                    worldDataRegistry.setScore(currentScore + 80);
                    worldDataRegistry.setDirty();
                }
                case 5 -> {
                    worldDataRegistry.setScore(currentScore + 160);
                    worldDataRegistry.setDirty();
                }
            }
        }
    }
}
