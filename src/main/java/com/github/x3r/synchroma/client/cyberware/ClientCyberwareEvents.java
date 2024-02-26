package com.github.x3r.synchroma.client.cyberware;

import com.github.x3r.synchroma.common.capability.CyberwareCapability;
import com.github.x3r.synchroma.common.item.cyberware.CyberwareItem;
import com.github.x3r.synchroma.common.item.cyberware.ImplantLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class ClientCyberwareEvents {

    @SubscribeEvent
    public static void onSwing(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = Minecraft.getInstance().player;
        player.getCapability(CyberwareCapability.INSTANCE).ifPresent(cyberwareCapability -> {
            for (ImplantLocation location : ImplantLocation.values()) {
                for (int i = 0; i < cyberwareCapability.getImplants(location).length; i++) {
                    ItemStack stack = cyberwareCapability.getImplants(location)[i];
                    if(stack.getItem() instanceof CyberwareItem cyberwareItem) {
                        cyberwareItem.onLeftClickEmpty(stack);
                    }
                }
            }
        });

    }
}
