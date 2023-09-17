package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.client.model.block.CentrifugeModel;
import com.github.x3r.synchroma.client.model.block.WindTurbineModel;
import com.github.x3r.synchroma.common.block.centrifuge.CentrifugeBlockEntity;
import com.github.x3r.synchroma.common.block.wind_turbine.WindTurbineBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class WindTurbineRenderer extends ControllerRenderer<WindTurbineBlockEntity> {
    public WindTurbineRenderer() {
        super(new WindTurbineModel());
    }

    @Override
    public void defaultRender(PoseStack poseStack, WindTurbineBlockEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        if(animatable.isAssembled()) {
            poseStack.pushPose();
            Vec3 offset = new Vec3(0, -1, 0).yRot((float) Math.toRadians(180 - getFacing(animatable).toYRot()));
            poseStack.translate(offset.x, offset.y, offset.z);
            super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
            poseStack.popPose();
        } else {
            renderController(poseStack, animatable, bufferSource, packedLight);
        }
    }

}
