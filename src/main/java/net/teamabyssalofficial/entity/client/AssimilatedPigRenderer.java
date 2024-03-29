package net.teamabyssalofficial.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.teamabyssalofficial.entity.custom.AssimilatedPigEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AssimilatedPigRenderer extends GeoEntityRenderer<AssimilatedPigEntity> {
    public AssimilatedPigRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AssimilatedPigModel());
        shadowRadius = 0.0f;
    }
    @Override
    public void render(AssimilatedPigEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        stack.pushPose();
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        stack.popPose();
    }
    @Override
    protected float getDeathMaxRotation(AssimilatedPigEntity entityLivingBaseIn) {
        return 0.0F;
    }
}
