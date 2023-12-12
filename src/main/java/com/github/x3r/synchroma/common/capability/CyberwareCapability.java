package com.github.x3r.synchroma.common.capability;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.item.cyberware.CyberwareItem;
import com.github.x3r.synchroma.common.item.cyberware.ImplantLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

import java.util.HashMap;

@AutoRegisterCapability
public class CyberwareCapability {

    public static Capability<CyberwareCapability> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    private HashMap<ImplantLocation, ItemStack[]> implants;

    public CyberwareCapability() {
        this.implants = createEmptyImplants();
    }

    private static HashMap<ImplantLocation, ItemStack[]> createEmptyImplants() {
        HashMap<ImplantLocation, ItemStack[]> map = new HashMap<>();
        for(ImplantLocation implantLocation : ImplantLocation.values()) {
            map.put(implantLocation, new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY});
        }
        return map;
    }
    public ItemStack[] getImplants(ImplantLocation location) {
        return implants.get(location);
    }

    public void addImplant(Player player, ItemStack stack, ImplantLocation location, int slot) {
        if(!stack.isEmpty()) {
            if (stack.getItem() instanceof CyberwareItem item) {
                item.onInstall(player, stack, location, slot);
                implants.get(location)[slot] = stack;
            } else {
                Synchroma.LOGGER.error("Attempted to install a non-cyberware item");
            }
        }
    }

    public void removeImplant(Player player, ImplantLocation location, int slot) {
        ItemStack stack = implants.get(location)[slot];
        if(!stack.isEmpty()) {
            if (stack.getItem() instanceof CyberwareItem item) {
                item.onRemove(player, stack, location, slot);
                implants.get(location)[slot] = ItemStack.EMPTY;
            } else {
                Synchroma.LOGGER.error("Attempted to remove a non-cyberware item");
            }
        }
    }

    public void copyFrom(ServerPlayer player) {
        this.implants = player.getCapability(INSTANCE).orElse(new CyberwareCapability()).implants;
    }

    public void copyFrom(CyberwareCapability capability) {
        this.implants = capability.implants;
    }

    public void saveToNBT(CompoundTag tag) {
        for (ImplantLocation location : ImplantLocation.values()) {
            ListTag listTag = new ListTag();
            for (int i = 0; i < implants.get(location).length; i++) {
                CompoundTag itemTag = new CompoundTag();
                implants.get(location)[i].save(itemTag);
                listTag.add(itemTag);
            }
            tag.put(location.getName(), listTag);
        }
        System.out.println(tag);
    }

    public void loadFromNBT(CompoundTag tag) {
        for (ImplantLocation location : ImplantLocation.values()) {
            ListTag listTag = tag.getList(location.getName(), 10);
            for (int i = 0; i < implants.get(location).length; i++) {
                implants.get(location)[i] = ItemStack.of(listTag.getCompound(i));
            }
        }
    }
}
