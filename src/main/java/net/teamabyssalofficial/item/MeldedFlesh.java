package net.teamabyssalofficial.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.teamabyssalofficial.item.categories.Utility;
import net.teamabyssalofficial.registry.EffectRegistry;
import net.teamabyssalofficial.registry.WorldDataRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MeldedFlesh extends Item implements Utility {
    public MeldedFlesh(Properties pProperties) {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.15f).meat().build()));

    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity.isAlive()) {
            if (Math.random() <= 0.5F) {
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
                pLivingEntity.addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 2400, 0));
            }
            else if (Math.random() <= 0.5F) {
                if (pLevel instanceof ServerLevel world) {
                    WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(world);
                    int currentscore = worldDataRegistry.getScore();
                    worldDataRegistry.setScore(currentscore - 15);
                }
            }
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @Override
    public boolean isEdible() {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("loot.fight_or_die.melded_flesh_text").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        return super.getFoodProperties(stack, entity);
    }
}
