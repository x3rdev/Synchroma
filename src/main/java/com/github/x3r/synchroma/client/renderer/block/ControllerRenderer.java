package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.client.model.block.ControllerModel;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlock;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlockEntity;
import com.github.x3r.synchroma.common.item.circuit.CircuitItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ControllerRenderer extends GeoBlockRenderer<ControllerBlockEntity> {

    public ControllerRenderer() {
        super(new ControllerModel());
    }

    @Override
    public void actuallyRender(PoseStack poseStack, ControllerBlockEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
//        if(!animatable.isAssembled()) {
//            super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
//            ItemStack stack = animatable.getFirstItem();
//            if (!stack.isEmpty()) {
//                poseStack.pushPose();
//                poseStack.translate(0.5, 0.9335, 0.5);
//                poseStack.mulPose(Axis.YP.rotationDegrees(animatable.getBlockState().getValue(ControllerBlock.FACING).toYRot()));
//                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
//                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, animatable.getLevel(), (int) animatable.getBlockPos().asLong());
//                poseStack.popPose();
//            }
//        } else {
//            poseStack.pushPose();
//            double angle = Math.toRadians(animatable.getBlockState().getValue(ControllerBlock.FACING).toYRot());
//            poseStack.translate(Math.cos(angle)*(2)-Math.sin(angle)*(-0.39), -1, Math.sin(angle)*(2)+Math.cos(angle)*(-0.39));
//
//            ItemStack stack = animatable.getFirstItem();
//            if(!stack.isEmpty() && stack.getItem() instanceof CircuitItem circuit) {
//                circuit.getRenderer().render(new MultiBlockEntity(animatable.getBlockPos(), animatable.getBlockState()), partialTick, poseStack, bufferSource, packedLight, packedOverlay);
//            }
//
//            poseStack.popPose();
//        }
    }
}
