package net.teamabyssalofficial.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.teamabyssalofficial.entity.custom.AssimilatedFoxEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AssimilatedFoxRenderer extends GeoEntityRenderer<AssimilatedFoxEntity> {

    public AssimilatedFoxRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AssimilatedFoxModel());
        shadowRadius = 0.0f;
    }

    @Override
    public void render(AssimilatedFoxEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        stack.pushPose();
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        stack.popPose();
    }
    @Override
    protected float getDeathMaxRotation(AssimilatedFoxEntity entityLivingBaseIn) {
        return 0.0F;
    }
}
