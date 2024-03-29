package net.teamabyssalofficial.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.teamabyssalofficial.entity.custom.AssimilatedHumanHeadEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import software.bernie.geckolib.model.GeoModel;

public class AssimilatedHumanHeadModel extends GeoModel<AssimilatedHumanHeadEntity> {

    @Override
    public ResourceLocation getModelResource(AssimilatedHumanHeadEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "geo/assimilated_human_head.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AssimilatedHumanHeadEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "textures/entity/assimilated_human_head.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AssimilatedHumanHeadEntity animatable) {
        return new ResourceLocation(FightOrDieMutations.MODID, "animations/assimilated_human_head.animation.json");
    }
}
