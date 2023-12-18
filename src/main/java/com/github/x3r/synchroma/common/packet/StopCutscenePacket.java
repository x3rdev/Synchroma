package com.github.x3r.synchroma.common.packet;

import com.github.x3r.synchroma.client.cutscene.ClientCutsceneManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StopCutscenePacket {

    public StopCutscenePacket() {

    }

    public void encode(FriendlyByteBuf buffer) {

    }

    public static StopCutscenePacket decode(FriendlyByteBuf buffer) {
        return new StopCutscenePacket();
    }

    public void receivePacket(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ClientCutsceneManager.getInstance().stopCutscene();
        });
        context.get().setPacketHandled(true);
    }
}
