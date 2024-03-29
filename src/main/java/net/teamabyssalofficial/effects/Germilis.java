package net.teamabyssalofficial.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.teamabyssalofficial.entity.categories.*;
import net.teamabyssalofficial.registry.DamageTypeRegistry;
import net.teamabyssalofficial.registry.EffectRegistry;

import java.util.ArrayList;
import java.util.List;

public class Germilis extends MobEffect {
    public Germilis() {
        super(MobEffectCategory.HARMFUL, -16777216);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> ret = new ArrayList<>();
        ret.add(ItemStack.EMPTY);
        return ret;
    }

    public void applyEffectTick(LivingEntity entity, int intense) {
        if (!(entity instanceof Parasite || entity instanceof Head || entity instanceof Mutated || entity instanceof Infector || entity instanceof AdvancedMutated || entity instanceof Primordial || entity instanceof Developed)) {
            if (this == EffectRegistry.GERMILIS.get()) {
                entity.hurt(DamageTypeRegistry.germilis_damage(entity), 1.0F);
            }
        }

    }
    public boolean isDurationEffectTick(int duration, int intensity) {
        if (this == EffectRegistry.GERMILIS.get()) {
            int i = 60 >> intensity;
            if (i > 0) {
                return duration % i == 0;
            } else {
                return true;
            }
        }
        return false;
    }
}
