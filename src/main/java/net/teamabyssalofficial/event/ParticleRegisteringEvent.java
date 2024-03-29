package net.teamabyssalofficial.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import net.teamabyssalofficial.particles.BloodPuffParticle;
import net.teamabyssalofficial.particles.KillCountParticle;
import net.teamabyssalofficial.particles.PoisonPuffParticle;
import net.teamabyssalofficial.registry.ParticleRegistry;

@Mod.EventBusSubscriber(modid = FightOrDieMutations.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleRegisteringEvent {
    @SubscribeEvent
    public static void registerParticle(RegisterParticleProvidersEvent event) {

        Minecraft.getInstance().particleEngine.register(ParticleRegistry.BLOOD_PUFF.get(),
                BloodPuffParticle.Provider::new);

        Minecraft.getInstance().particleEngine.register(ParticleRegistry.POISON_PUFF.get(),
                PoisonPuffParticle.Provider::new);

        Minecraft.getInstance().particleEngine.register(ParticleRegistry.KILL_COUNT.get(),
                KillCountParticle.Provider::new);
    }
}
