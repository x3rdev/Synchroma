package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class FrameModel extends DefaultedBlockGeoModel {

    public FrameModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "frame"));
    }

    @Override
    public RenderType getRenderType(GeoAnimatable animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
