package com.github.x3r.synchroma.client.renderer.entity;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.entity.BulletEntity;
import com.github.x3r.synchroma.common.entity.RideableEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class RideableRenderer extends EntityRenderer<RideableEntity> {

    public RideableRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(RideableEntity pEntity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
