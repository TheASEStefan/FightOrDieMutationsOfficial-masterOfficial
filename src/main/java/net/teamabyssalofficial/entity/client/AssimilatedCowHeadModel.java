package net.teamabyssalofficial.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.teamabyssalofficial.entity.custom.AssimilatedCowHeadEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import software.bernie.geckolib.model.GeoModel;

public class AssimilatedCowHeadModel extends GeoModel<AssimilatedCowHeadEntity> {
    @Override
    public ResourceLocation getModelResource(AssimilatedCowHeadEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "geo/assimilated_cow_head.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AssimilatedCowHeadEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "textures/entity/assimilated_cow_head.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AssimilatedCowHeadEntity animatable) {
        return new ResourceLocation(FightOrDieMutations.MODID, "animations/assimilated_cow_head.animation.json");
    }
}
