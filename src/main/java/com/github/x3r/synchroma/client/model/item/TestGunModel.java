package com.github.x3r.synchroma.client.model.item;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.item.guns.TestGun;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class TestGunModel extends DefaultedItemGeoModel<TestGun> {

    private static final ResourceLocation TEST_GUN_RESOURCE =
            new ResourceLocation(Synchroma.MOD_ID, "textures/item/missing.png");

    public TestGunModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "test_gun"));
    }

    @Override
    public ResourceLocation getTextureResource(TestGun animatable) {
        return TEST_GUN_RESOURCE;
    }
}
