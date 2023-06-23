package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.ripperdoc_chair.RipperdocChairBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class RipperdocChairModel extends DefaultedBlockGeoModel<RipperdocChairBlockEntity> {

    public RipperdocChairModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "ripperdoc_chair"));
    }

    @Override
    public ResourceLocation getModelResource(RipperdocChairBlockEntity object) {
        return new ResourceLocation(Synchroma.MOD_ID, "geo/ripperdoc_chair.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RipperdocChairBlockEntity object) {
        return new ResourceLocation(Synchroma.MOD_ID, "textures/block/ripperdoc_chair.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RipperdocChairBlockEntity animatable) {
        return new ResourceLocation(Synchroma.MOD_ID, "animations/ripperdoc_chair.animation.json");
    }
}
