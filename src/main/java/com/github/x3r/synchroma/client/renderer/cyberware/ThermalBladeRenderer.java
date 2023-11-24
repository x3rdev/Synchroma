package com.github.x3r.synchroma.client.renderer.cyberware;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.model.cyberware.ThermalBladeModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class ThermalBladeRenderer<T extends Item & GeoAnimatable> extends GeoItemRenderer<T> {

    public ThermalBladeRenderer() {
        super(new ThermalBladeModel<>());

        addRenderLayer(new AutoGlowingGeoLayer<>(this) {
            @Override
            protected RenderType getRenderType(T animatable) {
                return RenderType.eyes(new ResourceLocation(Synchroma.MOD_ID, "textures/item/thermal_blade_glowmask.png"));
            }

            @Override
            public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
                super.render(poseStack, animatable, bakedModel, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
            }
        });
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}
