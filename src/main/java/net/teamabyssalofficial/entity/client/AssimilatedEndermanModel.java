package net.teamabyssalofficial.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.teamabyssalofficial.entity.custom.AssimilatedEndermanEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import software.bernie.geckolib.model.GeoModel;

public class AssimilatedEndermanModel extends GeoModel<AssimilatedEndermanEntity> {
    
    @Override
    public ResourceLocation getModelResource(AssimilatedEndermanEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "geo/assimilated_enderman.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AssimilatedEndermanEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "textures/entity/assimilated_enderman.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AssimilatedEndermanEntity animatable) {
        return new ResourceLocation(FightOrDieMutations.MODID, "animations/assimilated_enderman.animation.json");
    }
}
