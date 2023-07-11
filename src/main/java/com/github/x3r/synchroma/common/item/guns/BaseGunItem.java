package com.github.x3r.synchroma.common.item.guns;

import com.github.x3r.synchroma.common.item.modification.GunModification;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseGunItem extends Item {
    protected BaseGunItem(Properties pProperties) {
        super(pProperties);
    }

    public abstract int getDelay();

    public abstract String[] getDefaultBones();

    /**
     * Returns all possible modifications for this weapon
     */
    public abstract GunModification[] getModifications();
    public Map<String, ResourceLocation> getTextureMap() {
        HashMap<String, ResourceLocation> map = new HashMap<>();
        Arrays.stream(getModifications()).forEach((mod -> map.put(mod.getName().getPath(), new ResourceLocation(getName().getNamespace(),
                String.format("textures/item/%s/%s.png", ForgeRegistries.ITEMS.getKey(this).getPath(), mod.getName().getPath())))));
        Arrays.stream(getDefaultBones()).forEach(bone -> map.put(bone, new ResourceLocation(getName().getNamespace(),
                String.format("textures/item/%s/%s.png", ForgeRegistries.ITEMS.getKey(this).getPath(), bone))));
        return map;
    }

    public ResourceLocation getName() {
        return ForgeRegistries.ITEMS.getKey(this);
    }
}
