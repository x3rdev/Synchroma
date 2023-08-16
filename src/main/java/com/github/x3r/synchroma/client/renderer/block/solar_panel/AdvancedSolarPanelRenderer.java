package com.github.x3r.synchroma.client.renderer.block.solar_panel;

import com.github.x3r.synchroma.client.model.block.solar_panel.AdvancedSolarPanelModel;
import com.github.x3r.synchroma.client.model.block.solar_panel.EnhancedSolarPanelModel;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlock;
import com.github.x3r.synchroma.common.block.solar_panel.AdvancedSolarPanelBlockEntity;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class AdvancedSolarPanelRenderer extends GeoBlockRenderer<AdvancedSolarPanelBlockEntity> {

    public AdvancedSolarPanelRenderer() {
        super(new AdvancedSolarPanelModel());
    }

    @Override
    public void actuallyRender(PoseStack poseStack, AdvancedSolarPanelBlockEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
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
