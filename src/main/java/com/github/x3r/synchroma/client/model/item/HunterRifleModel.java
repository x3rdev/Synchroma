package com.github.x3r.synchroma.client.model.item;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.item.guns.HunterRifle;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class HunterRifleModel extends DefaultedItemGeoModel<HunterRifle> {

    private static final ResourceLocation HUNTER_RIFLE_RESOURCE =
            new ResourceLocation(Synchroma.MOD_ID, "textures/item/hunter_rifle/default_mode.png");

    public HunterRifleModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "hunter_rifle"));
    }

    @Override
    public ResourceLocation getTextureResource(HunterRifle animatable) {
        return HUNTER_RIFLE_RESOURCE;
    }
}
