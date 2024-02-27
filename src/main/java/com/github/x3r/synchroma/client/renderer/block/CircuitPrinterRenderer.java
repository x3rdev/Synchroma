package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.client.model.block.CircuitPrinterModel;
import com.github.x3r.synchroma.common.block.circuit_printer.CircuitPrinterBlockEntity;
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
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class CircuitPrinterRenderer extends GeoBlockRenderer<CircuitPrinterBlockEntity> {
    public CircuitPrinterRenderer() {
        super(new CircuitPrinterModel());
    }

    @Override
    public void defaultRender(PoseStack poseStack, CircuitPrinterBlockEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
        ItemStack stack = animatable.getItem(animatable.getProcessTime() > 300 ? 7 : 0);
        if(!stack.isEmpty()) {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            poseStack.pushPose();
            poseStack.translate(0.4375, 0.225, 0.53);
            poseStack.mulPose(Axis.YP.rotationDegrees(180 + 180 - getFacing(animatable).toYRot()));
            poseStack.mulPose(Axis.ZP.rotationDegrees(90));
            poseStack.scale(2, 2, 2);
            itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, animatable.getLevel(), animatable.hashCode());
            poseStack.popPose();
        }
    }
}
