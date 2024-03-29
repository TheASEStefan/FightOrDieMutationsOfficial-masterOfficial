package net.teamabyssalofficial.effects;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.teamabyssalofficial.entity.categories.*;
import net.teamabyssalofficial.registry.EffectRegistry;

public class Fury extends MobEffect {

    public Fury() {
        super(MobEffectCategory.BENEFICIAL, 0x000000);
    }


    public void applyEffectTick(LivingEntity entity, int intense) {
        if (entity instanceof Parasite || entity instanceof Head || entity instanceof Mutated || entity instanceof Infector || entity instanceof AdvancedMutated || entity instanceof Primordial || entity instanceof Developed) {
            if (this == EffectRegistry.FURY.get()) {
                    if (entity.level() instanceof ServerLevel world) {
                        world.sendParticles(ParticleTypes.FLAME, entity.getX(), entity.getEyeY() - 0.2, entity.getZ(), 23, 0.2, 0.4, 0.1, 0);
                        for (int index = 0; index <= 1 + entity.getRandom().nextInt(2); index++) {
                            world.sendParticles(ParticleTypes.FLAME, entity.getRandomX(0.5), entity.getRandomY() + 0.25D, entity.getRandomZ(0.5), 2, 0.1, 0.1, 0.1, (entity.getRandom().nextDouble() - 0.5D) * 0.25D);
                        }
                    }

            }
        }

    }


    public boolean isDurationEffectTick(int duration, int intensity) {
        if (this == EffectRegistry.FURY.get()) {
            int i = 80 >> intensity;
            if (i > 0) {
                return duration % i == 0;
            } else {
                return true;
            }
        }
        return false;
    }
}
