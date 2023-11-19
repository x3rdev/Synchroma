package com.github.x3r.synchroma.client.model.cyberware;

import com.github.x3r.synchroma.Synchroma;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class ThermalBladeModel<T extends GeoAnimatable> extends DefaultedItemGeoModel<T> {

    public ThermalBladeModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "thermal_blade"));
    }

}
