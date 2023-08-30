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
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.state.BoneSnapshot;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class CentrifugeRenderer extends ControllerRenderer<CentrifugeBlockEntity> {
    public CentrifugeRenderer() {
        super(new CentrifugeModel());
    }

    @Override
    public void defaultRender(PoseStack poseStack, CentrifugeBlockEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        if(animatable.isAssembled()) {
            poseStack.pushPose();
            Vec3 offset = new Vec3(0, 0, 1).yRot((float) Math.toRadians(180 - getFacing(animatable).toYRot()));
            poseStack.translate(offset.x, offset.y, offset.z);
            super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
            renderItem(poseStack, animatable, bufferSource, packedLight);
            poseStack.popPose();
        } else {
            renderController(poseStack, animatable, bufferSource, packedLight);
        }
    }

    private void renderItem(PoseStack poseStack, CentrifugeBlockEntity animatable, MultiBufferSource bufferSource, int packedLight) {
        ItemStack stack = animatable.getItem(0);
        if(!stack.isEmpty()) {
            float rot = animatable.boneSnapshots.get("rotating_part").getRotY();
            Vec3 v = new Vec3(0.75 * animatable.getSpeedRatio(), 1, 0).yRot(rot).add(new Vec3(0.5, 0, 0.5).yRot((float) Math.toRadians(180-animatable.getBlockState().getValue(ControllerBlock.FACING).toYRot())));
            animatable.getBlockPos();
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            poseStack.pushPose();
            poseStack.translate(v.x, v.y, v.z);
            itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, animatable.getLevel(), animatable.hashCode());
            poseStack.popPose();
        }
    }
}
