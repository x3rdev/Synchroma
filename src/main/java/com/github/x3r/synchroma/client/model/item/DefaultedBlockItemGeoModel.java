package com.github.x3r.synchroma.client.model.item;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class DefaultedBlockItemGeoModel<T extends GeoAnimatable> extends DefaultedItemGeoModel<T> {

    public DefaultedBlockItemGeoModel(ResourceLocation assetSubpath) {
        super(assetSubpath);
    }

    @Override
    protected String subtype() {
        return "block";
    }
}
