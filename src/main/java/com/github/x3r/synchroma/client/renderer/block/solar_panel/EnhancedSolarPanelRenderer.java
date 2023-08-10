package com.github.x3r.synchroma.client.renderer.block.solar_panel;

import com.github.x3r.synchroma.client.model.block.EnhancedSolarPanelModel;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlock;
import com.github.x3r.synchroma.common.block.solar_panel.EnhancedSolarPanelBlockEntity;
import com.github.x3r.synchroma.common.registry.BlockRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.util.RenderUtils;

import java.util.HashMap;

public class EnhancedSolarPanelRenderer extends GeoBlockRenderer<EnhancedSolarPanelBlockEntity> {

    public EnhancedSolarPanelRenderer() {
        super(new EnhancedSolarPanelModel());
    }

    @Override
    public void actuallyRender(PoseStack poseStack, EnhancedSolarPanelBlockEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(animatable.isAssembled()) {
            poseStack.pushPose();
            poseStack.translate(0, -1, 0);
            super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
            poseStack.popPose();
        } else {
            poseStack.pushPose();
            BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
            BlockState state = BlockRegistry.EMPTY_CONTROLLER.get().defaultBlockState();
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(180-animatable.getBlockState().getValue(ControllerBlock.FACING).toYRot()));
            poseStack.translate(-0.5, 0, -0.5);
            blockRenderer.getModelRenderer().tesselateBlock(animatable.getLevel(), blockRenderer.getBlockModel(state), state, animatable.getBlockPos(), poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()), false, RandomSource.create(), state.getSeed(animatable.getBlockPos()), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.cutoutMipped());
            poseStack.popPose();
        }
    }
}
