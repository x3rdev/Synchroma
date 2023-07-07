package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.controller.ControllerBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class ControllerModel extends DefaultedBlockGeoModel<ControllerBlockEntity> {

    public ControllerModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "controller"));
    }

    @Override
    public RenderType getRenderType(ControllerBlockEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
