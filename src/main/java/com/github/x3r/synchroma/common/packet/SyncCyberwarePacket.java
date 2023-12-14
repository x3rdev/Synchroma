package com.github.x3r.synchroma.common.packet;

import com.github.x3r.synchroma.common.capability.CyberwareCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncCyberwarePacket {

    private final CyberwareCapability cyberwareCapability;

    public SyncCyberwarePacket(CyberwareCapability cyberwareCapability) {
        this.cyberwareCapability = cyberwareCapability;
    }

    public void encode(FriendlyByteBuf buffer) {
        cyberwareCapability.saveToNetwork(buffer);
    }

    public static SyncCyberwarePacket decode(FriendlyByteBuf buffer) {
        CyberwareCapability c = new CyberwareCapability();
        c.loadFromNetwork(buffer);
        return new SyncCyberwarePacket(c);
    }

    public void receivePacket(Supplier<NetworkEvent.Context> context) {
        LocalPlayer player = Minecraft.getInstance().player;
        context.get().enqueueWork(() -> {
            player.getCapability(CyberwareCapability.INSTANCE).ifPresent(capability -> {
                capability.copyFrom(cyberwareCapability);
            });
        });
        context.get().setPacketHandled(true);
    }
}
