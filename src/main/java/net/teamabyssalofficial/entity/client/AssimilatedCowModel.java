package net.teamabyssalofficial.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.teamabyssalofficial.entity.custom.AssimilatedCowEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import software.bernie.geckolib.model.GeoModel;

public class AssimilatedCowModel extends GeoModel<AssimilatedCowEntity> {


    @Override
    public ResourceLocation getModelResource(AssimilatedCowEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "geo/assimilated_cow.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AssimilatedCowEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "textures/entity/assimilated_cow.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AssimilatedCowEntity animatable) {
        return new ResourceLocation(FightOrDieMutations.MODID, "animations/assimilated_cow.animation.json");
    }
}
