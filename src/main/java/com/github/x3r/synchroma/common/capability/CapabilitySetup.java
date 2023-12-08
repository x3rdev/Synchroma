package com.github.x3r.synchroma.common.capability;

import com.github.x3r.synchroma.Synchroma;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapabilitySetup {
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player player) {
            if(player.getCapability(CyberwareCapability.INSTANCE).isPresent()) {
                event.addCapability(new ResourceLocation(Synchroma.MOD_ID, "cyberware"), new PlayerCyberwareProvider());
            }
        }
    }

    @SubscribeEvent
    public static void playerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(CyberwareCapability.INSTANCE).ifPresent(oldStore -> {
                event.getOriginal().getCapability(CyberwareCapability.INSTANCE).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }
}
