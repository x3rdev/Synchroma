package com.github.x3r.synchroma.client.renderer.entity;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.entity.CiabattaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CiabattaRenderer extends GeoEntityRenderer<CiabattaEntity> {

    public CiabattaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(Synchroma.MOD_ID, "ciabatta")));
    }
}
