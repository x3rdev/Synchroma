package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.client.model.block.FabricatorModel;
import com.github.x3r.synchroma.common.block.fabricator.FabricatorBlockEntity;
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
import org.joml.Vector3d;

public class FabricatorRenderer extends ControllerRenderer<FabricatorBlockEntity> {

    public FabricatorRenderer() {
        super(new FabricatorModel());
    }

    @Override
    public void defaultRender(PoseStack poseStack, FabricatorBlockEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        if (animatable.isAssembled()) {
            poseStack.pushPose();
            Vec3 offset = new Vec3(2, -1, 0).yRot((float) Math.toRadians(180 - getFacing(animatable).toYRot()));
            poseStack.translate(offset.x, offset.y, offset.z);
            super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
            renderItems(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
            poseStack.popPose();
        } else {
            renderController(poseStack, animatable, bufferSource, packedLight);
        }
    }

    public void renderItems(PoseStack poseStack, FabricatorBlockEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {

        for (int i = 0; i < 5; i++) {
            ItemStack stack = animatable.getItem(i);
            if(!stack.isEmpty()) {
                ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                poseStack.pushPose();
                model.getBone("item"+i).ifPresent(geoBone -> {
                    Vector3d v = new Vector3d(geoBone.getPivotX()/16F, geoBone.getPivotY()/16F, geoBone.getPivotZ()/16F).rotateY((float) Math.toRadians(180 - getFacing(animatable).toYRot()));
                    Vector3d v1 = new Vector3d(-geoBone.getPosX()/16F, geoBone.getPosY()/16F, geoBone.getPosZ()/16F).rotateY((float) Math.toRadians(180 - getFacing(animatable).toYRot()));
                    poseStack.translate(v.x+v1.x+0.5, v.y+v1.y+0.125, v.z+v1.z+0.5);
                });
                poseStack.mulPose(Axis.YP.rotation((float) Math.toRadians(180 - getFacing(animatable).toYRot())));
                itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, animatable.getLevel(), animatable.hashCode());
                poseStack.popPose();
            }
        }
    }
}
