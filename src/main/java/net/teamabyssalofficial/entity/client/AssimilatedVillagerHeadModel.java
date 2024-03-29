package net.teamabyssalofficial.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.teamabyssalofficial.entity.custom.AssimilatedVillagerHeadEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import software.bernie.geckolib.model.GeoModel;

public class AssimilatedVillagerHeadModel extends GeoModel<AssimilatedVillagerHeadEntity> {
    @Override
    public ResourceLocation getModelResource(AssimilatedVillagerHeadEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "geo/assimilated_villager_head.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AssimilatedVillagerHeadEntity object) {
        return new ResourceLocation(FightOrDieMutations.MODID, "textures/entity/assimilated_villager_head.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AssimilatedVillagerHeadEntity animatable) {
        return new ResourceLocation(FightOrDieMutations.MODID, "animations/assimilated_villager_head.animation.json");
    }
}
