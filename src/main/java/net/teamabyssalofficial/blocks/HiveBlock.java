package net.teamabyssalofficial.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.teamabyssalofficial.constants.SoundTypeLibrary;
import net.teamabyssalofficial.registry.EffectRegistry;
import net.teamabyssalofficial.registry.EntityRegistry;

public class HiveBlock extends Block {
    public HiveBlock(Properties pProperties) {
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
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }
    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 15;
    }
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 10;
    }


}
