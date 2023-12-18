package com.github.x3r.synchroma.common.item.cyberware;

import com.github.x3r.synchroma.common.capability.CyberwareCapability;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class CyberwareEvents {

    private CyberwareEvents() {

    }

    @SubscribeEvent
    public static void cyberwareTick(TickEvent.ServerTickEvent event) {
        event.getServer().getPlayerList().getPlayers().forEach(player -> {
            player.getCapability(CyberwareCapability.INSTANCE).ifPresent(cyberwareCapability -> {
                for (ImplantLocation location : ImplantLocation.values()) {
                    for (int i = 0; i < cyberwareCapability.getImplants(location).length; i++) {
                        ItemStack stack = cyberwareCapability.getImplants(location)[i];
                        if(stack.getItem() instanceof CyberwareItem cyberwareItem) {
                            cyberwareItem.cyberwareTick(stack, player.serverLevel(), player, location, i);
                        }
                    }
                }
            });
        });
    }
}
