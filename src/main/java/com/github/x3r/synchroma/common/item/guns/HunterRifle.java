package com.github.x3r.synchroma.common.item.guns;

import net.minecraft.world.item.Item;

public class HunterRifle extends Item implements SynchromaGun {

    public HunterRifle(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getDelay() {
        return 0;
    }
}
