package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.client.model.block.RipperdocChairModel;
import com.github.x3r.synchroma.common.block.ripperdoc_chair.RipperdocChairBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RipperdocChairRenderer extends GeoBlockRenderer<RipperdocChairBlockEntity> {

    public RipperdocChairRenderer() {
        super(new RipperdocChairModel());
    }

    @Override
    public void preRender(PoseStack poseStack, RipperdocChairBlockEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.translate(0, -0.0075, 0);
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
    @Override
    public RenderType getRenderType(RipperdocChairBlockEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
