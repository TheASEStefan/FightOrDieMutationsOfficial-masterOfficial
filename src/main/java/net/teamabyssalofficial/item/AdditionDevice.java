package net.teamabyssalofficial.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.item.categories.Device;
import net.teamabyssalofficial.item.categories.Utility;
import net.teamabyssalofficial.registry.WorldDataRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AdditionDevice extends Item implements Device, Utility {
    public AdditionDevice(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide && pUsedHand == InteractionHand.MAIN_HAND && pLevel instanceof ServerLevel world) {
            WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(world);
            int currentScore = worldDataRegistry.getScore();
            worldDataRegistry.setScore(currentScore + FightOrDieMutationsConfig.DATAGEN.devices_points.get());
            pPlayer.sendSystemMessage(Component.literal("+" + FightOrDieMutationsConfig.DATAGEN.devices_points.get()));
            pPlayer.getCooldowns().addCooldown(this, 20);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.fight_or_die.addition_device_text").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY)));
    }

}
