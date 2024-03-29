package net.teamabyssalofficial.extra;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import net.teamabyssalofficial.registry.SoundRegistry;
import net.teamabyssalofficial.registry.WorldDataRegistry;

@Mod.EventBusSubscriber(modid = FightOrDieMutations.MODID)
public class CreepedAmbience {
    @SubscribeEvent
    public static void CreepAmbienceEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player instanceof ServerPlayer player && event.player.level() instanceof ServerLevel world) {
            WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(world);
            Player player1 = world.getRandomPlayer();
            int playerSize = world.players().size();
            assert player1 != null;
            long day2 = (int) (24000L * 2);
            long day3 = (int) (24000L * 3);
            long day5 = (int) (24000L * 5);
            if (playerSize > 0 && FightOrDieMutationsConfig.SERVER.random_disturbing_sounds.get()) {
                if (world.dayTime() > (day2) && Math.random() <= 0.02 && !worldDataRegistry.hadMetalEvent() && worldDataRegistry.getPhase() > 0) {
                    player1.sendSystemMessage(Component.literal("What was that?"));
                    player1.level().playSound((Player) null, player1.blockPosition(), SoundRegistry.JUMPSCARE_1.get(), SoundSource.MASTER, 1.4F, 1.0F);
                    worldDataRegistry.setScore(worldDataRegistry.getScore() + 10);
                    worldDataRegistry.setHadMetalEvent(true);
                }
                else if (world.dayTime() > (day5) && Math.random() <= 0.02 && !worldDataRegistry.hadViolinEvent() && worldDataRegistry.getPhase() > 0) {
                    player1.sendSystemMessage(Component.literal("Hello?"));
                    player1.level().playSound((Player) null, player1.blockPosition(), SoundRegistry.JUMPSCARE_2.get(), SoundSource.MASTER, 1.4F, 1.0F);
                    worldDataRegistry.setScore(worldDataRegistry.getScore() + 20);
                    worldDataRegistry.setHadViolinEvent(true);
                }
                else if (world.dayTime() > (day3) && Math.random() <= 0.02 && !worldDataRegistry.hadWoodCroakEvent() && worldDataRegistry.getPhase() > 0) {
                    player1.sendSystemMessage(Component.literal("Not safe..."));
                    player1.level().playSound((Player) null, player1.blockPosition(), SoundRegistry.JUMPSCARE_3.get(), SoundSource.MASTER, 1.4F, 1.0F);
                    worldDataRegistry.setScore(worldDataRegistry.getScore() + 50);
                    worldDataRegistry.setHadWoodCroakEvent(true);
                }
            }
        }
    }
}
