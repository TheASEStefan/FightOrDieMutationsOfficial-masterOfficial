package net.teamabyssalofficial.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.entity.categories.*;
import net.teamabyssalofficial.registry.EffectRegistry;

import java.util.ArrayList;
import java.util.List;

public class HiveSickness extends MobEffect {

    public HiveSickness() {
        super(MobEffectCategory.HARMFUL, -16777216);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> ret = new ArrayList<>();
        ret.add(ItemStack.EMPTY);
        return ret;
    }

    public void applyEffectTick(LivingEntity entity, int intense) {
        if (!(entity instanceof Parasite || entity instanceof Head || entity instanceof Mutated || entity instanceof Infector || entity instanceof AdvancedMutated || entity instanceof Primordial || entity instanceof Developed || FightOrDieMutationsConfig.SERVER.hive_sickness.get().contains(entity.getEncodeId()))) {
            if (this == EffectRegistry.HIVE_SICKNESS.get()) {
                if (!entity.getCommandSenderWorld().isClientSide && entity instanceof Player player && player.getFoodData().getFoodLevel() > 0 && intense < 1){
                    player.causeFoodExhaustion(1.0F);
                }
            }
        }

    }
    @Override
    public void initializeClient(java.util.function.Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInGui(MobEffectInstance effect) {
                return false;
            }
        });
    }


    public boolean isDurationEffectTick(int duration, int intensity) {
        if (this == EffectRegistry.HIVE_SICKNESS.get()) {
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
