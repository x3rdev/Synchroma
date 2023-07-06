package com.github.x3r.synchroma.client.renderer.item;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.model.item.TestGunModel;
import com.github.x3r.synchroma.client.renderer.DynamicGeoItemRenderer;
import com.github.x3r.synchroma.common.item.guns.TestGun;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;

public class TestGunRenderer extends DynamicGeoItemRenderer<TestGun> {

    private static final ResourceLocation DEFAULT_MODE_TEXTURE = new ResourceLocation(Synchroma.MOD_ID, "textures/item/test_gun/default_mode.png");
    private static final ResourceLocation BASE_TEXTURE = new ResourceLocation(Synchroma.MOD_ID, "textures/item/test_gun/base.png");
    private static final ResourceLocation MAG_TEXTURE = new ResourceLocation(Synchroma.MOD_ID, "textures/item/test_gun/default_mag.png");
    private static final ResourceLocation BOLT_TEXTURE = new ResourceLocation(Synchroma.MOD_ID, "textures/item/test_gun/bolt.png");
    private static final ResourceLocation SCOPE1_TEXTURE = new ResourceLocation(Synchroma.MOD_ID, "textures/item/test_gun/scope1.png");

    public TestGunRenderer() {
        super(new TestGunModel());
    }
    @Nullable
    @Override
    protected ResourceLocation getTextureOverrideForBone(GeoBone bone, TestGun animatable, float partialTick) {
        if(bone.getName().equals("default_mode")) {
            return DEFAULT_MODE_TEXTURE;
        }
        if(bone.getName().equals("base_metal") || bone.getName().equals("base_colored")) {
            return BASE_TEXTURE;
        }
        if(bone.getName().equals("mag")) {
            return MAG_TEXTURE;
        }
        if(bone.getName().equals("bolt")) {
            return BOLT_TEXTURE;
        }
        if(bone.getName().equals("scope1")) {
            return SCOPE1_TEXTURE;
        }
//        System.out.println(bone.getName());
        return null;
    }
}
