package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.client.model.block.FabricatorModel;
import com.github.x3r.synchroma.common.block.fabricator.FabricatorBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class FabricatorRenderer extends ControllerRenderer<FabricatorBlockEntity>{

    public FabricatorRenderer() {
        super(new FabricatorModel());
    }

    @Override
    public void defaultRender(PoseStack poseStack, FabricatorBlockEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        if(animatable.isAssembled()) {
            poseStack.pushPose();
            Vec3 offset = new Vec3(2, -1, 0).yRot((float) Math.toRadians(180 - getFacing(animatable).toYRot()));
            poseStack.translate(offset.x, offset.y, offset.z);
            super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
            poseStack.popPose();
        } else {
            renderController(poseStack, animatable, bufferSource, packedLight);
        }
    }
}
