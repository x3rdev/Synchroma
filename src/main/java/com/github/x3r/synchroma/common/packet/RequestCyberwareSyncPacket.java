package com.github.x3r.synchroma.common.packet;

import com.github.x3r.synchroma.common.capability.CyberwareCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestCyberwareSyncPacket {

    public RequestCyberwareSyncPacket() {
    }

    public void encode(FriendlyByteBuf buffer) {

    }

    public static RequestCyberwareSyncPacket decode(FriendlyByteBuf buffer) {
        return new RequestCyberwareSyncPacket();
    }

    public void receivePacket(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            player.getCapability(CyberwareCapability.INSTANCE).ifPresent(cyberwareCapability -> {
                SynchromaPacketHandler.sendToClient(new SyncCyberwarePacket(cyberwareCapability), player);
            });
        });
    }
}
