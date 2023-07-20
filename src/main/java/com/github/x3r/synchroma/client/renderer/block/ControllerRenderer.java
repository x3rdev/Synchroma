package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.client.model.block.ControllerModel;
import com.github.x3r.synchroma.client.model.block.WeaponWorkbenchModel;
import com.github.x3r.synchroma.common.block.controller.ControllerBlock;
import com.github.x3r.synchroma.common.block.controller.ControllerBlockEntity;
import com.github.x3r.synchroma.common.block.weapon_workbench.WeaponWorkbenchBlockEntity;
import com.github.x3r.synchroma.common.registry.BlockRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ControllerRenderer extends GeoBlockRenderer<ControllerBlockEntity> {

    private WeaponWorkbenchRenderer weaponWorkbenchRenderer = new WeaponWorkbenchRenderer();
    public ControllerRenderer() {
        super(new ControllerModel());
    }

    @Override
    public void actuallyRender(PoseStack poseStack, ControllerBlockEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(!animatable.isAssembled()) {
            super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
            ItemStack stack = animatable.getFirstItem();
            if (!stack.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5, 0.9335, 0.5);
                poseStack.mulPose(Axis.YP.rotationDegrees(animatable.getBlockState().getValue(ControllerBlock.FACING).toYRot()));
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, animatable.getLevel(), (int) animatable.getBlockPos().asLong());
                poseStack.popPose();
            }
        } else {
            poseStack.pushPose();
            Direction direction = animatable.getBlockState().getValue(ControllerBlock.FACING);
            if (direction.equals(Direction.NORTH)) {
                poseStack.translate(-2, -1, 0.39);
                poseStack.mulPose(Axis.YP.rotationDegrees(0));
            }
            if (direction.equals(Direction.EAST)) {
                poseStack.translate(0.61, -1, -2);
                poseStack.mulPose(Axis.YP.rotationDegrees(-90));
            }
            if (direction.equals(Direction.SOUTH)) {
                poseStack.translate(3, -1, 0.61);
                poseStack.mulPose(Axis.YP.rotationDegrees(-180));
            }
            if (direction.equals(Direction.WEST)) {
                poseStack.translate(0.39, -1, 3);
                poseStack.mulPose(Axis.YP.rotationDegrees(-270));
            }
            weaponWorkbenchRenderer.render(new WeaponWorkbenchBlockEntity(animatable.getBlockPos(), BlockRegistry.WEAPON_WORKBENCH.get().defaultBlockState()), partialTick, poseStack, bufferSource, packedLight, packedOverlay);
            poseStack.popPose();
        }
    }
}
