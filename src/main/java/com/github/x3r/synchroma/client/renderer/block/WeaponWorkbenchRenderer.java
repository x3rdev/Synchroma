package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.client.model.block.WeaponWorkbenchModel;
import com.github.x3r.synchroma.common.block.frame.FrameBlockEntity;
import com.github.x3r.synchroma.common.block.weapon_workbench.WeaponWorkbenchBlock;
import com.github.x3r.synchroma.common.block.weapon_workbench.WeaponWorkbenchBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class WeaponWorkbenchRenderer extends GeoBlockRenderer<WeaponWorkbenchBlockEntity> {
    public WeaponWorkbenchRenderer() {
        super(new WeaponWorkbenchModel());
    }

    @Override
    public void preRender(PoseStack poseStack, WeaponWorkbenchBlockEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.translate(0, -0.01, 0);
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}