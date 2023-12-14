package com.github.x3r.synchroma.common.packet;

import com.github.x3r.synchroma.Synchroma;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class SynchromaPacketHandler {
    private static final String PROTOCOL_VERSION = "1";

    private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Synchroma.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        int id = 0;
        INSTANCE.registerMessage(id++, SyncCyberwarePacket.class, SyncCyberwarePacket::encode, SyncCyberwarePacket::decode, SyncCyberwarePacket::receivePacket);
        INSTANCE.registerMessage(id++, RequestCyberwareSyncPacket.class, RequestCyberwareSyncPacket::encode, RequestCyberwareSyncPacket::decode, RequestCyberwareSyncPacket::receivePacket);
    }

    public static void sendToClient(Object msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static void sendToClientS(Object msg) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }
}
