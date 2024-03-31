package net.teamabyssalofficial.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.teamabyssalofficial.constants.SoundTypeLibrary;

public class BloodSplash1 extends Block {

    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 14, 1, 14);
    public BloodSplash1(Properties pProperties) {
        super(pProperties.noCollission().noOcclusion().sound(SoundType.GRASS));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }


}
