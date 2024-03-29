package net.teamabyssalofficial.extra;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.teamabyssalofficial.constants.PlayerVariables;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import net.teamabyssalofficial.registry.EntityRegistry;
import net.teamabyssalofficial.registry.WorldDataRegistry;


@Mod.EventBusSubscriber(modid = FightOrDieMutations.MODID)
public class AdvancementHandling {
    @SubscribeEvent
    public static void SurviveEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player instanceof ServerPlayer player && event.player.level() instanceof ServerLevel world) {
            WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(world);
            int currentPhase = worldDataRegistry.getPhase();
            if (currentPhase == 5) {
                if (event.player instanceof ServerPlayer player1) {
                    Advancement advancement = player1.server.getAdvancements().getAdvancement(new ResourceLocation("fight_or_die:expert_survivor"));
                    AdvancementProgress orStartProgress = player1.getAdvancements().getOrStartProgress(advancement);
                    if (!orStartProgress.isDone()) {
                        for (String string : orStartProgress.getRemainingCriteria())
                            player1.getAdvancements().award(advancement, string);
                    }
                }

            }
        }
    }

    @SubscribeEvent
    public static void MutationSlaying(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null && !event.getEntity().level().isClientSide) {
            if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof Player player) {
                PlayerVariables playerVariables = ((PlayerVariables) player);
                if (EntityRegistry.PARASITES.contains(event.getEntity())) {
                    playerVariables.IsetKills(playerVariables.IgetKills() + 1);

                    if (playerVariables.IgetKills() == 100) {
                        if (player instanceof ServerPlayer player1) {
                            Advancement advancement = player1.server.getAdvancements().getAdvancement(new ResourceLocation("fight_or_die:mutation_slayer1"));
                            AdvancementProgress orStartProgress = player1.getAdvancements().getOrStartProgress(advancement);
                            if (!orStartProgress.isDone()) {
                                for (String string : orStartProgress.getRemainingCriteria())
                                    player1.getAdvancements().award(advancement, string);
                            }
                        }
                    }
                    else if (playerVariables.IgetKills() == 1000) {
                        if (player instanceof ServerPlayer player1) {
                            Advancement advancement = player1.server.getAdvancements().getAdvancement(new ResourceLocation("fight_or_die:mutation_slayer2"));
                            AdvancementProgress orStartProgress = player1.getAdvancements().getOrStartProgress(advancement);
                            if (!orStartProgress.isDone()) {
                                for (String string : orStartProgress.getRemainingCriteria())
                                    player1.getAdvancements().award(advancement, string);
                            }
                        }
                    }
                    else if (playerVariables.IgetKills() == 10000) {
                        if (player instanceof ServerPlayer player1) {
                            Advancement advancement = player1.server.getAdvancements().getAdvancement(new ResourceLocation("fight_or_die:mutation_slayer3"));
                            AdvancementProgress orStartProgress = player1.getAdvancements().getOrStartProgress(advancement);
                            if (!orStartProgress.isDone()) {
                                for (String string : orStartProgress.getRemainingCriteria())
                                    player1.getAdvancements().award(advancement, string);
                            }
                        }
                    }
                }
            }
        }
    }
}
