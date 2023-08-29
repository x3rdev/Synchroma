package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.common.block.multiblock.ControllerBlockEntity;
import com.github.x3r.synchroma.common.registry.BlockRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public abstract class ControllerRenderer<T extends ControllerBlockEntity> extends GeoBlockRenderer<T> {

    protected ControllerRenderer(GeoModel<T> model) {
        super(model);
    }

    protected void renderController(PoseStack poseStack, T animatable, MultiBufferSource bufferSource) {
        poseStack.pushPose();
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        BlockState state = BlockRegistry.EMPTY_CONTROLLER.get().defaultBlockState();
        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Axis.YP.rotation((float) Math.toRadians(180 - getFacing(animatable).toYRot())));
        poseStack.translate(-0.5, 0, -0.5);
        blockRenderer.getModelRenderer().tesselateBlock(animatable.getLevel(), blockRenderer.getBlockModel(state), state, animatable.getBlockPos(), poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()), false, RandomSource.create(), state.getSeed(animatable.getBlockPos()), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.cutoutMipped());
        poseStack.popPose();
    }

    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    protected BakedModel[][][] getGuideModels() {
        return new BakedModel[][][]{
                {},
                {},
                {}
        };
    }
}
