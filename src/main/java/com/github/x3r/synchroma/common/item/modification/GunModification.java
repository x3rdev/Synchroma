package com.github.x3r.synchroma.common.item.modification;

import com.github.x3r.synchroma.common.item.guns.SynchromaGun;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class GunModification extends Item {

    private SynchromaGun[] guns;

    public GunModification(SynchromaGun[] guns) {
        super(new Properties());
        this.guns = guns;
    }

    public SynchromaGun[] getCompatibleGuns() {
        return this.guns;
    }

    public ResourceLocation getName() {
        return ForgeRegistries.ITEMS.getKey(this);
    }

}
