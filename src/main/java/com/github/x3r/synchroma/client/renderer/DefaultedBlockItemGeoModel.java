package com.github.x3r.synchroma.client.renderer;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class DefaultedBlockItemGeoModel extends DefaultedItemGeoModel {

    public DefaultedBlockItemGeoModel(ResourceLocation assetSubpath) {
        super(assetSubpath);
    }

    @Override
    protected String subtype() {
        return "block";
    }
}
