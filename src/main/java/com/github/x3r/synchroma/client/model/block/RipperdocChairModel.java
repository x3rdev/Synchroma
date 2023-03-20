package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.ripperdoc_chair.RipperdocChairBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RipperdocChairModel extends AnimatedGeoModel<RipperdocChairBlockEntity> {

    @Override
    public ResourceLocation getModelLocation(RipperdocChairBlockEntity object) {
        return new ResourceLocation(Synchroma.MOD_ID, "geo/ripperdoc_chair.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(RipperdocChairBlockEntity object) {
        return new ResourceLocation(Synchroma.MOD_ID, "textures/block/ripperdoc_chair.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RipperdocChairBlockEntity animatable) {
        return new ResourceLocation(Synchroma.MOD_ID, "animations/ripperdoc_chair.animation.json");
    }
}
