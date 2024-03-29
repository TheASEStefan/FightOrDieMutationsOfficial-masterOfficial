package net.teamabyssalofficial.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.teamabyssalofficial.entity.custom.AssimilatedFoxEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import software.bernie.geckolib.model.GeoModel;

public class AssimilatedFoxModel extends GeoModel<AssimilatedFoxEntity> {
    @Override
    public ResourceLocation getModelResource(AssimilatedFoxEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "geo/assimilated_fox.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AssimilatedFoxEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "textures/entity/assimilated_fox.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AssimilatedFoxEntity animatable) {
        return new ResourceLocation(FightOrDieMutations.MODID, "animations/assimilated_fox.animation.json");
    }
}
