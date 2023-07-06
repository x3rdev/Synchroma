package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.client.model.block.WeaponWorkbenchModel;
import com.github.x3r.synchroma.common.block.weapon_workbench.WeaponWorkbenchBlock;
import com.github.x3r.synchroma.common.block.weapon_workbench.WeaponWorkbenchBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class WeaponWorkbenchRenderer extends GeoBlockRenderer<WeaponWorkbenchBlockEntity> {
    public WeaponWorkbenchRenderer() {
        super(new WeaponWorkbenchModel());
    }
}