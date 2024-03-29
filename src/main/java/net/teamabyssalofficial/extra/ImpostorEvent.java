package net.teamabyssalofficial.extra;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import net.teamabyssalofficial.registry.EffectRegistry;
import net.teamabyssalofficial.registry.SoundRegistry;

@Mod.EventBusSubscriber(modid = FightOrDieMutations.MODID)
public class ImpostorEvent {
    @SubscribeEvent
    public static void ImpostorGrowlEvent(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() != null && event != null) {
            Entity entity = event.getEntity();
            if (entity instanceof Cow || entity instanceof Pig || entity instanceof Sheep || entity instanceof Fox) {
                if (((Animal) entity).hasEffect(EffectRegistry.HIVE_SICKNESS.get()) && FightOrDieMutationsConfig.SERVER.stomach_growl_detection.get()) {
                    if (((Animal) entity).getRandom().nextInt(1200) == 0) {
                        entity.level().playSound((Player) null, entity.blockPosition(), SoundRegistry.STOMACH_GROWL.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
                    }
                }
            }
        }
    }
}
