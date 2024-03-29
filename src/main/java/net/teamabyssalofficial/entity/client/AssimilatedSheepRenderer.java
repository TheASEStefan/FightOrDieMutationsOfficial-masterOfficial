package net.teamabyssalofficial.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.teamabyssalofficial.entity.custom.AssimilatedSheepEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AssimilatedSheepRenderer extends GeoEntityRenderer<AssimilatedSheepEntity> {

    public AssimilatedSheepRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AssimilatedSheepModel());
        shadowRadius = 0.0f;
    }
    @Override
    public void render(AssimilatedSheepEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        stack.pushPose();
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        stack.popPose();
    }
    @Override
    protected float getDeathMaxRotation(AssimilatedSheepEntity entityLivingBaseIn) {
        return 0.0F;
    }
}
