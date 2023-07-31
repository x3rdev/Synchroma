package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.microscope.MicroscopeBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class MicroscopeModel extends DefaultedBlockGeoModel<MicroscopeBlockEntity> {

    public MicroscopeModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "microscope"));
    }
}
