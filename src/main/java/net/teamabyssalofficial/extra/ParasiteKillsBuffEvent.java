package net.teamabyssalofficial.extra;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.teamabyssalofficial.entity.categories.*;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import net.teamabyssalofficial.registry.EffectRegistry;
import net.teamabyssalofficial.registry.WorldDataRegistry;

@Mod.EventBusSubscriber(modid = FightOrDieMutations.MODID)
public class ParasiteKillsBuffEvent {
    @SubscribeEvent
    public static void ParasiteBuffEvent(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null && !event.getEntity().level().isClientSide && event.getSource().getEntity() != null) {
            if(!(event.getSource().getEntity() instanceof LivingEntity)) {
                return;
            }
            
            LivingEntity entity = (LivingEntity) event.getSource().getEntity();
            Level world = entity.level();
            WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry((ServerLevel) world);
            int currentPhase = worldDataRegistry.getPhase();
                if (!world.isClientSide && (entity instanceof Parasite || entity instanceof Head || entity instanceof Mutated || entity instanceof Infector || entity instanceof AdvancedMutated || entity instanceof Primordial || entity instanceof Developed)) {
                    if (currentPhase >= 3 && Math.random() <= 0.25) {
                        entity.addEffect(new MobEffectInstance(EffectRegistry.FURY.get(), 400, 0), entity);
                        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0), entity);
                    }
                }
        }
    }
}
