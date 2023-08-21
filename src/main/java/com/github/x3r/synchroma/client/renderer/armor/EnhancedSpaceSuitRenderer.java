package com.github.x3r.synchroma.client.renderer.armor;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.item.armor.BasicSpaceSuitItem;
import com.github.x3r.synchroma.common.item.armor.EnhancedSpaceSuitItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class EnhancedSpaceSuitRenderer extends GeoArmorRenderer<EnhancedSpaceSuitItem> {
    public EnhancedSpaceSuitRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(Synchroma.MOD_ID, "armor/enhanced_space_suit")));

        addRenderLayer(new AutoGlowingGeoLayer<>(this));

//        addRenderLayer(new EnhancedSpaceSuitDyedLayer(this) {
//            @Override
//            public ItemStack getCurrentStack() {
//                return EnhancedSpaceSuitRenderer.this.getCurrentStack();
//            }
//        });
    }

//    @Override
//    public RenderType getRenderType(EnhancedSpaceSuitItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
//        return RenderType.entityTranslucent(texture);
//    }

    public abstract static class EnhancedSpaceSuitDyedLayer extends GeoRenderLayer<EnhancedSpaceSuitItem> {

        protected EnhancedSpaceSuitDyedLayer(GeoRenderer<EnhancedSpaceSuitItem> entityRendererIn) {
            super(entityRendererIn);
        }

        public abstract ItemStack getCurrentStack();

        @Override
        public void render(PoseStack poseStack, EnhancedSpaceSuitItem animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
            RenderType armorRenderType = RenderType.armorCutoutNoCull(new ResourceLocation(Synchroma.MOD_ID, "textures/item/armor/enhanced_space_suit_overlay.png"));

            int color = animatable.getColor(getCurrentStack());

            getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, armorRenderType,
                    bufferSource.getBuffer(armorRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                    ((color >> 16) & 0xff) / 255.0f,
                    ((color >> 8) & 0xff) / 255.0f,
                    (color & 0xff) / 255.0f, 1);
        }
    }
}
