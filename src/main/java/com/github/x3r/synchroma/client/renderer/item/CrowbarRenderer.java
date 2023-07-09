package com.github.x3r.synchroma.client.renderer.item;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.item.CrowbarItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class CrowbarRenderer extends GeoItemRenderer<CrowbarItem> {

    public CrowbarRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(Synchroma.MOD_ID, "crowbar")));
    }
}
