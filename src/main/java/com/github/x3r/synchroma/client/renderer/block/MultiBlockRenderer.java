package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.common.block.multiblock.MultiBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MultiBlockRenderer extends GeoBlockRenderer<MultiBlockEntity> {
    public MultiBlockRenderer(GeoModel model) {
        super(model);
    }

//    public void setModel(GeoModel model) {
//        this.model = model;
//    }
}
