package com.github.x3r.synchroma.common.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StartSurgeonPacket {

    private final BlockPos surgeonPos;
    private final byte implantPage;

    public StartSurgeonPacket(BlockPos surgeonPos, byte implantPage) {
        this.surgeonPos = surgeonPos;
        this.implantPage = implantPage;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(surgeonPos);
        buffer.writeByte(implantPage);
    }

    public static StartSurgeonPacket decode(FriendlyByteBuf buffer) {
        return new StartSurgeonPacket(buffer.readBlockPos(), buffer.readByte());
    }

    public void receivePacket(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {

        });
    }
}
