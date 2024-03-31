package net.teamabyssalofficial.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FightOrDieMutations.MODID);

    public static final RegistryObject<SimpleParticleType> POISON_PUFF =
            PARTICLE_TYPES.register("poison_puff", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BLOOD_PUFF =
            PARTICLE_TYPES.register("blood_puff", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> KILL_COUNT =
            PARTICLE_TYPES.register("kill_count", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GUTS =
            PARTICLE_TYPES.register("guts", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
