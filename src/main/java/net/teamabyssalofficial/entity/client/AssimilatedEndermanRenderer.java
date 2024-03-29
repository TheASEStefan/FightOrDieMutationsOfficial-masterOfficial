package net.teamabyssalofficial.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.teamabyssalofficial.entity.custom.AssimilatedEndermanEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AssimilatedEndermanRenderer extends GeoEntityRenderer<AssimilatedEndermanEntity> {

    public AssimilatedEndermanRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AssimilatedEndermanModel());
        shadowRadius = 0.0f;
    }
    @Override
    public void render(AssimilatedEndermanEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        stack.pushPose();
        stack.scale(0.8F, 0.75F, 0.8F);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        stack.popPose();
    }
}
