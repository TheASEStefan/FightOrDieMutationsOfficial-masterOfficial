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
            if (event.level.getDifficulty() == Difficulty.NORMAL) {
                if (Math.random() < 0.02F) {
                    if (currentPhase < 2) {
                        worldDataRegistry.setScore(currentScore + 2);
                    }
                    else if (currentPhase > 2) {
                        worldDataRegistry.setScore(currentScore - 1);
                    }
                }
                if (Math.random() < 0.015F) {
                    if (currentScore > 0) {
                        worldDataRegistry.setScore(currentScore + 5);
                    }
                }
                if (Math.random() < 0.015F) {
                    if (currentPhase > 3) {
                        worldDataRegistry.setScore(currentScore + 3);
                    }
                }
            }
            else if (event.level.getDifficulty() == Difficulty.HARD) {
                if (Math.random() < 0.02F) {
                    if (currentPhase < 2) {
                        worldDataRegistry.setScore(currentScore + 3);
                    }
                    else if (currentPhase > 2) {
                        worldDataRegistry.setScore(currentScore - 1);
                    }
                }
                if (Math.random() < 0.025F) {
                    if (currentScore > 0) {
                        worldDataRegistry.setScore(currentScore + 10);
                    }
                }
                if (Math.random() < 0.025F) {
                    if (currentPhase > 3) {
                        worldDataRegistry.setScore(currentScore + 5);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void SleepEvent(PlayerSleepInBedEvent event) {
        if (event.getEntity() != null && event.getEntity() instanceof ServerPlayer player) {
            WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry((ServerLevel) player.level());
            int currentPhase = worldDataRegistry.getPhase();
            int currentScore = worldDataRegistry.getScore();
            if (currentPhase == 0) {
                worldDataRegistry.setScore(currentScore + 5);
                worldDataRegistry.setDirty();
            }
            else if (currentPhase == 1) {
                worldDataRegistry.setScore(currentScore + 10);
                worldDataRegistry.setDirty();
            }
            else if (currentPhase == 2) {
                worldDataRegistry.setScore(currentScore + 20);
                worldDataRegistry.setDirty();
            }
            else if (currentPhase == 3) {
                worldDataRegistry.setScore(currentScore + 40);
                worldDataRegistry.setDirty();
            }
            else if (currentPhase == 4) {
                worldDataRegistry.setScore(currentScore + 80);
                worldDataRegistry.setDirty();
            }
            else if (currentPhase == 5) {
                worldDataRegistry.setScore(currentScore + 160);
                worldDataRegistry.setDirty();

            }
        }
    }
}
