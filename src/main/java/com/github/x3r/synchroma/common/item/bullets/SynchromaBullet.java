package com.github.x3r.synchroma.common.item.bullets;

import net.minecraft.world.effect.MobEffectInstance;

public interface SynchromaBullet {

    float getDamage();

    float getSpeed();

    float getEntityPiercingModifier();

    float getBlockPiercingModifier();

    MobEffectInstance getEffect();
}
