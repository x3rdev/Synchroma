package com.github.x3r.synchroma.client.renderer;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.item.armor.EnhancedSpaceSuitItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public abstract class DyeableGeoLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {

    protected DyeableGeoLayer(GeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    public abstract ItemStack getCurrentStack();

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if(animatable instanceof DyeableLeatherItem item) {
            ResourceLocation location = new ResourceLocation(getTextureResource(animatable).getNamespace(), getTextureResource(animatable).getPath().replace(".png", "_dyemask.png"));
            RenderType armorRenderType = RenderType.armorCutoutNoCull(location);

            int color = item.getColor(getCurrentStack());

            getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, armorRenderType,
                    bufferSource.getBuffer(armorRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                    ((color >> 16) & 0xff) / 255.0f,
                    ((color >> 8) & 0xff) / 255.0f,
                    (color & 0xff) / 255.0f, 1);
        }
    }
}
