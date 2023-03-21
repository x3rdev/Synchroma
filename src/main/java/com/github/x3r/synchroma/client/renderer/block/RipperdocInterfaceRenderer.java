package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.client.model.block.RipperdocInterfaceModel;
import com.github.x3r.synchroma.common.block.ripperdoc_interface.RipperdocInterfaceBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RipperdocInterfaceRenderer extends GeoBlockRenderer<RipperdocInterfaceBlockEntity> {
    public RipperdocInterfaceRenderer(BlockEntityRendererProvider.Context rendererProvider) {
        super(rendererProvider, new RipperdocInterfaceModel());
    }

    @Override
    public void renderEarly(RipperdocInterfaceBlockEntity animatable, PoseStack poseStack, float partialTick, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.translate(0, -0.01, 0);
        super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public RenderType getRenderType(RipperdocInterfaceBlockEntity animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }


}
