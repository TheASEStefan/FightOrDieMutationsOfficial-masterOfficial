package net.teamabyssalofficial.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.teamabyssalofficial.entity.custom.AssimilatedCowHeadEntity;
import net.teamabyssalofficial.entity.custom.AssimilatedVillagerHeadEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AssimilatedCowHeadRenderer extends GeoEntityRenderer<AssimilatedCowHeadEntity> {

    public AssimilatedCowHeadRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AssimilatedCowHeadModel());
        shadowRadius = 0.0f;
    }

    @Override
    public void render(AssimilatedCowHeadEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        stack.pushPose();
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        stack.popPose();
    }
}
