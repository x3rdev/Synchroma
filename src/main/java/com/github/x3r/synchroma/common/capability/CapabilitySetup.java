package com.github.x3r.synchroma.common.capability;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.packet.RequestCyberwareSyncPacket;
import com.github.x3r.synchroma.common.packet.SyncCyberwarePacket;
import com.github.x3r.synchroma.common.packet.SynchromaPacketHandler;
import com.github.x3r.synchroma.common.scheduler.Scheduler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.core.jmx.Server;

public class CapabilitySetup {
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player player) {
            if(!player.getCapability(CyberwareCapability.INSTANCE).isPresent()) {
                event.addCapability(new ResourceLocation(Synchroma.MOD_ID, "cyberware"), new PlayerCyberwareProvider());
            }
            if(player instanceof ServerPlayer serverPlayer) {
                player.getCapability(CyberwareCapability.INSTANCE).ifPresent(cyberwareCapability -> {
                    SynchromaPacketHandler.sendToClient(new SyncCyberwarePacket(cyberwareCapability), serverPlayer);
                });
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

    @SubscribeEvent
    public static void playerLoggedInEvent(ClientPlayerNetworkEvent.LoggingIn event) {
        SynchromaPacketHandler.sendToServer(new RequestCyberwareSyncPacket());
    }
}
