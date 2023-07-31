package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.client.model.block.MicroscopeModel;
import com.github.x3r.synchroma.common.block.microscope.MicroscopeBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MicroscopeRenderer extends GeoBlockRenderer<MicroscopeBlockEntity> {
    public MicroscopeRenderer() {
        super(new MicroscopeModel());
    }
}
