package com.github.x3r.synchroma.client.renderer.entity;

import com.github.x3r.synchroma.common.entity.BulletEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class BulletRenderer extends ThrownItemRenderer<BulletEntity> {

    public BulletRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
}
