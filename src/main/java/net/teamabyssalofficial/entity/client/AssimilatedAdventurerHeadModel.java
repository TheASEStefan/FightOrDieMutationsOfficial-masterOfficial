package net.teamabyssalofficial.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.teamabyssalofficial.entity.custom.AssimilatedAdventurerHeadEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import software.bernie.geckolib.model.GeoModel;

public class AssimilatedAdventurerHeadModel extends GeoModel<AssimilatedAdventurerHeadEntity> {
    @Override
    public ResourceLocation getModelResource(AssimilatedAdventurerHeadEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "geo/assimilated_adventurer_head.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AssimilatedAdventurerHeadEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "textures/entity/assimilated_adventurer_head.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AssimilatedAdventurerHeadEntity animatable) {
        return new ResourceLocation(FightOrDieMutations.MODID, "animations/assimilated_adventurer_head.animation.json");
    }
}
