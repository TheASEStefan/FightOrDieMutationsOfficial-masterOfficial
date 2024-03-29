package net.teamabyssalofficial.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.teamabyssalofficial.item.categories.BaseItem;
import net.teamabyssalofficial.item.categories.Device;
import net.teamabyssalofficial.item.categories.Utility;
import net.teamabyssalofficial.registry.WorldDataRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Parascore extends BaseItem implements Device, Utility {

    public Parascore(Properties pProperties) {
        super(new Properties().stacksTo(1).durability(10));
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide && pUsedHand == InteractionHand.MAIN_HAND && pLevel instanceof ServerLevel world) {
            WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(world);
            pPlayer.displayClientMessage(Component.literal(" " + worldDataRegistry.getScore()), true);
            pPlayer.getCooldowns().addCooldown(this, 60);
        }
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.fight_or_die.parascore_text").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
    }
}
