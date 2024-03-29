package net.teamabyssalofficial.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.teamabyssalofficial.entity.custom.AssimilatedEndermanHeadEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AssimilatedEndermanHeadRenderer extends GeoEntityRenderer<AssimilatedEndermanHeadEntity> {

    public AssimilatedEndermanHeadRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AssimilatedEndermanHeadModel());
        shadowRadius = 0.0f;
    }
    @Override
    public void render(AssimilatedEndermanHeadEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        stack.pushPose();
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        stack.popPose();
    }
}
