package net.teamabyssalofficial.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.teamabyssalofficial.constants.SoundTypeLibrary;
import net.teamabyssalofficial.entity.custom.ShillerEntity;
import net.teamabyssalofficial.registry.EffectRegistry;
import net.teamabyssalofficial.registry.EntityRegistry;
import net.teamabyssalofficial.registry.WorldDataRegistry;

public class HiveTunnelBlock extends Block {

    public HiveTunnelBlock(Properties pProperties) {
        super(pProperties.sound(SoundTypeLibrary.HIVE_SOUNDS));
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (!(EntityRegistry.PARASITES.contains(pEntity))) {
            if (pEntity instanceof LivingEntity livingEntity && !livingEntity.hasEffect(EffectRegistry.HIVE_SICKNESS.get())) {
                livingEntity.addEffect(new MobEffectInstance(EffectRegistry.HIVE_SICKNESS.get(), 1200, 0), livingEntity);
            }
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }


    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry((ServerLevel) pLevel);
        int currentScore = worldDataRegistry.getScore();
        if (Math.random() <= 0.45F) {
            Block block = pLevel.getBlockState(pPos.above()).getBlock();
            if (block == Blocks.AIR || block == Blocks.CAVE_AIR || block == Blocks.VOID_AIR) {
                ShillerEntity shillerEntity = new ShillerEntity(EntityRegistry.SHILLER.get(), pLevel);
                shillerEntity.moveTo(pPos.above(), 0, 0);
                pLevel.addFreshEntity(shillerEntity);
                if (Math.random() <= 0.25F) {
                    worldDataRegistry.setScore(currentScore + 1);
                }
            }
        }
        super.tick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 10;
    }
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }
}
