package com.github.x3r.synchroma.client.renderer.armor;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.renderer.DyeableGeoLayer;
import com.github.x3r.synchroma.common.item.armor.BasicSpaceSuitItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.awt.*;

public class BasicSpaceSuitRenderer extends GeoArmorRenderer<BasicSpaceSuitItem> {
    public BasicSpaceSuitRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(Synchroma.MOD_ID, "armor/basic_space_suit")));

        addRenderLayer(new DyeableGeoLayer<>(this) {
            @Override
            public ItemStack getCurrentStack() {
                return BasicSpaceSuitRenderer.this.getCurrentStack();
            }
        });
    }
}
