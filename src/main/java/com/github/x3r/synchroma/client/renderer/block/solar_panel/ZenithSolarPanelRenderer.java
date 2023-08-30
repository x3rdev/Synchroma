package com.github.x3r.synchroma.client.renderer.block.solar_panel;

import com.github.x3r.synchroma.client.model.block.solar_panel.AdvancedSolarPanelModel;
import com.github.x3r.synchroma.client.model.block.solar_panel.ZenithSolarPanelModel;
import com.github.x3r.synchroma.client.renderer.block.ControllerRenderer;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlock;
import com.github.x3r.synchroma.common.block.solar_panel.AdvancedSolarPanelBlockEntity;
import com.github.x3r.synchroma.common.block.solar_panel.ZenithSolarPanelBlockEntity;
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

public class ZenithSolarPanelRenderer extends ControllerRenderer<ZenithSolarPanelBlockEntity> {

    public ZenithSolarPanelRenderer() {
        super(new ZenithSolarPanelModel());
    }

    @Override
    public void actuallyRender(PoseStack poseStack, ZenithSolarPanelBlockEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(animatable.isAssembled()) {
            poseStack.pushPose();
            poseStack.translate(0, -1, 0);
            super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
            poseStack.popPose();
        } else {
            renderController(poseStack, animatable, bufferSource, packedLight);
        }
    }
}
