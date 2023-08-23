package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.client.model.block.CentrifugeModel;
import com.github.x3r.synchroma.common.block.centrifuge.CentrifugeBlockEntity;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlock;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class CentrifugeRenderer extends GeoBlockRenderer<CentrifugeBlockEntity> {
    public CentrifugeRenderer() {
        super(new CentrifugeModel());
    }

    @Override
    public void defaultRender(PoseStack poseStack, CentrifugeBlockEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        float yRot = (float) Math.toRadians(180-animatable.getBlockState().getValue(ControllerBlock.FACING).toYRot());
        if(animatable.isAssembled()) {
            poseStack.pushPose();
            Vec3 offset = new Vec3(0, 0, 1).yRot(yRot);
            poseStack.translate(offset.x, offset.y, offset.z);
            super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
            poseStack.popPose();
        } else {
            poseStack.pushPose();
            BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
            BlockState state = BlockRegistry.EMPTY_CONTROLLER.get().defaultBlockState();
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YP.rotation(yRot));
            poseStack.translate(-0.5, 0, -0.5);
            blockRenderer.getModelRenderer().tesselateBlock(animatable.getLevel(), blockRenderer.getBlockModel(state), state, animatable.getBlockPos(), poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()), false, RandomSource.create(), state.getSeed(animatable.getBlockPos()), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.cutoutMipped());
            poseStack.popPose();
        }
    }
}
