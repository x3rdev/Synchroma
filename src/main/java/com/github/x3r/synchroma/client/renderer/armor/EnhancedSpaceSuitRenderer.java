package com.github.x3r.synchroma.client.renderer.armor;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.renderer.DyeableGeoLayer;
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
        addRenderLayer(new DyeableGeoLayer<>(this) {
            @Override
            public ItemStack getCurrentStack() {
                return EnhancedSpaceSuitRenderer.this.getCurrentStack();
            }
        });
        addRenderLayer(new AutoGlowingGeoLayer<>(this) {
            @Override
            protected RenderType getRenderType(EnhancedSpaceSuitItem animatable) {
                return RenderType.eyes(new ResourceLocation(Synchroma.MOD_ID, "textures/item/armor/enhanced_space_suit_glowmask.png"));
            }
        });
    }

    @Override
    public RenderType getRenderType(EnhancedSpaceSuitItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}
