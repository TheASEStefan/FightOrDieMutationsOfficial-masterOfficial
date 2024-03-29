package net.teamabyssalofficial.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.teamabyssalofficial.entity.custom.MalruptorEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import software.bernie.geckolib.model.GeoModel;

public class MalruptorModel extends GeoModel<MalruptorEntity> {

    @Override
    public ResourceLocation getModelResource(MalruptorEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "geo/malruptor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MalruptorEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "textures/entity/malruptor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MalruptorEntity animatable) {
        return new ResourceLocation(FightOrDieMutations.MODID, "animations/malruptor.animation.json");
    }
} 
