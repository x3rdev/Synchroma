package com.github.x3r.synchroma.common.item.bullets;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;

public class TestBullet extends Item implements SynchromaBullet {

    public TestBullet(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public float getDamage() {
        return 1;
    }

    @Override
    public float getSpeed() {
        return 4;
    }

    @Override
    public float getEntityPiercingModifier() {
        return 1;
    }

    @Override
    public float getBlockPiercingModifier() {
        return 1;
    }

    @Override
    public MobEffectInstance getEffect() {
        return null;
    }


}
