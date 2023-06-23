package com.github.x3r.synchroma.client.renderer.item;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.item.guns.TestGun;
import net.minecraft.resources.ResourceLocation;
import org.antlr.v4.codegen.model.Sync;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class TestGunRenderer extends GeoItemRenderer<TestGun> {
    public TestGunRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(Synchroma.MOD_ID, "test_gun")));
    }
}
