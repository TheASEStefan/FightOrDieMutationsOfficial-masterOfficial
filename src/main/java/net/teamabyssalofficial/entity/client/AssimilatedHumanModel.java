package net.teamabyssalofficial.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.teamabyssalofficial.entity.custom.AssimilatedHumanEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import software.bernie.geckolib.model.GeoModel;

public class AssimilatedHumanModel extends GeoModel<AssimilatedHumanEntity> {
    
    @Override
    public ResourceLocation getModelResource(AssimilatedHumanEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "geo/assimilated_human.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AssimilatedHumanEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "textures/entity/assimilated_human.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AssimilatedHumanEntity animatable) {
        return new ResourceLocation(FightOrDieMutations.MODID, "animations/assimilated_human.animation.json");
    }
}
