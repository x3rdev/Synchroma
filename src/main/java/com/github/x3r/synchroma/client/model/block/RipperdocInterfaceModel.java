package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.ripperdoc_interface.RipperdocInterfaceBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RipperdocInterfaceModel extends AnimatedGeoModel<RipperdocInterfaceBlockEntity> {

    @Override
    public ResourceLocation getModelLocation(RipperdocInterfaceBlockEntity object) {
        return new ResourceLocation(Synchroma.MOD_ID, "geo/ripperdoc_interface.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(RipperdocInterfaceBlockEntity object) {
       int count = (int) (object.getLevel().nextSubTickCount()/40 % 14);
       return new ResourceLocation(Synchroma.MOD_ID, "textures/block/ripperdoc_interface/ripperdoc_interface_" + count + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RipperdocInterfaceBlockEntity animatable) {
        return new ResourceLocation(Synchroma.MOD_ID, "animations/ripperdoc_interface.animation.json");
    }
}
