package net.teamabyssalofficial.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.teamabyssalofficial.entity.custom.AssimilatedAdventurerHeadEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AssimilatedAdventurerHeadRenderer extends GeoEntityRenderer<AssimilatedAdventurerHeadEntity> {

    public AssimilatedAdventurerHeadRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AssimilatedAdventurerHeadModel());
        shadowRadius = 0.0f;
    }

    @Override
    public void render(AssimilatedAdventurerHeadEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        stack.pushPose();
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        stack.popPose();
    }
}
