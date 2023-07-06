package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.frame.FrameBlock;
import com.github.x3r.synchroma.common.block.frame.FrameBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class FrameModel extends DefaultedBlockGeoModel<FrameBlockEntity> {

    public FrameModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "frame"));
    }

    @Override
    public RenderType getRenderType(FrameBlockEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
