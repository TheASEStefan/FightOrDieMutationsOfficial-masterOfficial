package net.teamabyssalofficial.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.teamabyssalofficial.entity.custom.AssimilatedVillagerEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AssimilatedVillagerRenderer extends GeoEntityRenderer<AssimilatedVillagerEntity> {

    public AssimilatedVillagerRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AssimilatedVillagerModel());
        shadowRadius = 0.0f;
    }

    @Override
    public void render(AssimilatedVillagerEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        stack.pushPose();
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        stack.popPose();
    }
    @Override
    protected float getDeathMaxRotation(AssimilatedVillagerEntity entityLivingBaseIn) {
        return 0.0F;
    }
}
