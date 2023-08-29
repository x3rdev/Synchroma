package com.github.x3r.synchroma.client.renderer.armor;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.item.armor.EngineerSuitItem;
import com.github.x3r.synchroma.common.item.armor.MilitarySuitItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class MilitarySuitRenderer extends GeoArmorRenderer<MilitarySuitItem> {
    public MilitarySuitRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(Synchroma.MOD_ID, "armor/military_suit")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this) {
            @Override
            protected RenderType getRenderType(MilitarySuitItem animatable) {
                return RenderType.eyes(new ResourceLocation(Synchroma.MOD_ID, "textures/item/armor/military_suit_glowmask.png"));
            }
        });
    }

    @Override
    public RenderType getRenderType(MilitarySuitItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}
