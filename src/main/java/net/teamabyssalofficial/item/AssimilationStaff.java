package net.teamabyssalofficial.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.teamabyssalofficial.item.categories.Staff;
import net.teamabyssalofficial.item.categories.Utility;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AssimilationStaff extends Item implements Utility, Staff {
    public AssimilationStaff(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.fight_or_die.mutation_staff_text").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_AQUA)));
    }
}
