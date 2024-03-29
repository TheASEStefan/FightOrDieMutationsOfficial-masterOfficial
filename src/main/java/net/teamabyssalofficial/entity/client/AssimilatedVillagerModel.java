package net.teamabyssalofficial.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.teamabyssalofficial.entity.custom.AssimilatedVillagerEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import software.bernie.geckolib.model.GeoModel;

public class AssimilatedVillagerModel extends GeoModel<AssimilatedVillagerEntity> {
    
    @Override
    public ResourceLocation getModelResource(AssimilatedVillagerEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "geo/assimilated_villager.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AssimilatedVillagerEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "textures/entity/assimilated_villager.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AssimilatedVillagerEntity animatable) {
        return new ResourceLocation(FightOrDieMutations.MODID, "animations/assimilated_villager.animation.json");
    }
    
}
