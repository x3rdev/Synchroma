package com.github.x3r.synchroma.common.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SwitchSurgeonPagePacket {

    public SwitchSurgeonPagePacket() {

    }


    public void encode(FriendlyByteBuf buffer) {

    }

    public static SwitchSurgeonPagePacket decode(FriendlyByteBuf buffer) {
        return new SwitchSurgeonPagePacket();
    }

    public void receivePacket(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {

        });
    }
}
