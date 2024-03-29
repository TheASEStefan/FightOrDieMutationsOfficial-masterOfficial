package net.teamabyssalofficial.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.teamabyssalofficial.entity.custom.AssimilatedSheepEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import software.bernie.geckolib.model.GeoModel;

public class AssimilatedSheepModel extends GeoModel<AssimilatedSheepEntity> {
    @Override
    public ResourceLocation getModelResource(AssimilatedSheepEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "geo/assimilated_sheep.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AssimilatedSheepEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "textures/entity/assimilated_sheep.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AssimilatedSheepEntity animatable) {
        return new ResourceLocation(FightOrDieMutations.MODID, "animations/assimilated_sheep.animation.json");
    }
}
