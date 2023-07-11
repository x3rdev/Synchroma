package com.github.x3r.synchroma.common.item.modification;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class GunModification extends Item {

    public GunModification() {
        super(new Properties());
    }

    public ResourceLocation getName() {
        return ForgeRegistries.ITEMS.getKey(this);
    }

}
