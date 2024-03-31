package net.teamabyssalofficial.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.teamabyssalofficial.item.categories.BaseItem;
import net.teamabyssalofficial.item.categories.Drop;
import net.teamabyssalofficial.item.categories.Utility;
import net.teamabyssalofficial.registry.EntityRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MutatedEnderPearl extends BaseItem implements Utility, Drop {
    public MutatedEnderPearl(Properties pProperties) {
        super(pProperties.stacksTo(16));
    }


    private boolean teleport(double pX, double pY, double pZ, LivingEntity entity, Level level) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(pX, pY, pZ);

        while(blockpos$mutableblockpos.getY() > level.getMinBuildHeight() && !level.getBlockState(blockpos$mutableblockpos).blocksMotion()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }

        BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
        boolean flag = blockstate.blocksMotion();
        boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
        if (flag && !flag1) {
            net.minecraftforge.event.entity.EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(entity, pX, pY, pZ);
            if (event.isCanceled()) return false;
            Vec3 vec3 = entity.position();
            boolean flag2 = entity.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2) {
                level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(entity));
                if (!entity.isSilent()) {
                    level.playSound((Player)null, entity.xo, entity.yo, entity.zo, SoundEvents.ENDERMAN_TELEPORT, entity.getSoundSource(), 1.0F, 1.0F);
                    entity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }
            }

            return flag2;
        } else {
            return false;
        }
    }

    protected boolean randTeleport(Level level, LivingEntity entity) {
        if (!level.isClientSide && entity.isAlive()) {
            double d0 = entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * 32.0D;
            double d1 = entity.getY() + (double)(entity.getRandom().nextInt(32) - 16);
            double d2 = entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * 32.0D;
            return this.teleport(d0, d1, d2, entity, level);
        } else {
            return false;
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.fight_or_die.mutated_ender_pearl_text").setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
            AABB boundingBox = pPlayer.getBoundingBox().inflate(20);
            List<Entity> entities = pLevel.getEntities(pPlayer, boundingBox);
            pPlayer.getCooldowns().addCooldown(this, 20);
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity livingEntity && EntityRegistry.PARASITES.contains(entity)) {
                    this.randTeleport(pLevel, livingEntity);
                    livingEntity.level().playSound((Player) null, livingEntity.blockPosition(), SoundEvents.ENDER_PEARL_THROW, SoundSource.HOSTILE, 1.2F, 1.0F);


                }
            }
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
            itemstack.shrink(1);
        return InteractionResultHolder.consume(itemstack);
    }


}
