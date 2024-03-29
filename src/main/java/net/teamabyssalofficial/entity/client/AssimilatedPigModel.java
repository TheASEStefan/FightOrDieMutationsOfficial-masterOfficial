package net.teamabyssalofficial.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.teamabyssalofficial.entity.custom.AssimilatedPigEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import software.bernie.geckolib.model.GeoModel;

public class AssimilatedPigModel extends GeoModel<AssimilatedPigEntity> {

    @Override
    public ResourceLocation getModelResource(AssimilatedPigEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "geo/assimilated_pig.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AssimilatedPigEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "textures/entity/assimilated_pig.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AssimilatedPigEntity animatable) {
        return new ResourceLocation(FightOrDieMutations.MODID, "animations/assimilated_pig.animation.json");
    }
}
