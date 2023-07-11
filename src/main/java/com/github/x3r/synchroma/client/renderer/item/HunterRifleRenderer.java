package com.github.x3r.synchroma.client.renderer.item;

import com.github.x3r.synchroma.client.model.item.HunterRifleModel;
import com.github.x3r.synchroma.client.renderer.DynamicGeoItemRenderer;
import com.github.x3r.synchroma.common.item.guns.HunterRifle;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;

public class HunterRifleRenderer extends DynamicGeoItemRenderer<HunterRifle> {

    public HunterRifleRenderer() {
        super(new HunterRifleModel());
    }
    @Nullable
    @Override
    protected ResourceLocation getTextureOverrideForBone(GeoBone bone, HunterRifle animatable, float partialTick) {
        String name = bone.getName();
        ResourceLocation location = animatable.getTextureMap().getOrDefault(name, null);
        return location;
    }
}
