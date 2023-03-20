package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.ripperdoc_chair.RipperdocChairBlockEntity;
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
        return new ResourceLocation(Synchroma.MOD_ID, "textures/block/ripperdoc_interface.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RipperdocInterfaceBlockEntity animatable) {
        return new ResourceLocation(Synchroma.MOD_ID, "animations/ripperdoc_interface.animation.json");
    }
}
