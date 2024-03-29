package net.teamabyssalofficial.entity.client;

import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.teamabyssalofficial.entity.custom.AssimilatedAdventurerEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;

@OnlyIn(Dist.CLIENT)
public class AssimilatedAdventurerRenderer<Type extends AssimilatedAdventurerEntity> extends MobRenderer<Type , AssimilatedAdventurerModel<Type>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(FightOrDieMutations.MODID, "textures/entity/assimilated_adventurer.png");

    public AssimilatedAdventurerRenderer(EntityRendererProvider.Context context) {
        super(context, new AssimilatedAdventurerModel<>(context.bakeLayer(AssimilatedAdventurerModel.LAYER_LOCATION)), 0.0f);

        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidArmorModel
                (context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }


    @Override
    public ResourceLocation getTextureLocation(Type entity) {
        return TEXTURE;
    }
}
