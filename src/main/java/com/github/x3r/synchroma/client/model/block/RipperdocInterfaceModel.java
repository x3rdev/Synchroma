package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.ripperdoc_interface.RipperdocInterfaceBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class RipperdocInterfaceModel extends DefaultedBlockGeoModel<RipperdocInterfaceBlockEntity> {

    public RipperdocInterfaceModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "ripperdoc_interface"));
    }

    @Override
    public ResourceLocation getModelResource(RipperdocInterfaceBlockEntity object) {
        return new ResourceLocation(Synchroma.MOD_ID, "geo/ripperdoc_interface.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RipperdocInterfaceBlockEntity object) {
       int count = (int) (object.getLevel().nextSubTickCount()/40 % 14);
       return new ResourceLocation(Synchroma.MOD_ID, "textures/block/ripperdoc_interface/ripperdoc_interface_" + count + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(RipperdocInterfaceBlockEntity animatable) {
        return new ResourceLocation(Synchroma.MOD_ID, "animations/ripperdoc_interface.animation.json");
    }

}
