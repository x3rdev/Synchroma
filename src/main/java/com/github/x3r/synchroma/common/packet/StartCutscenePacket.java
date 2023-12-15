package com.github.x3r.synchroma.common.packet;

import com.github.x3r.synchroma.client.cutscene.ClientCutsceneManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class StartCutscenePacket {

    public StartCutscenePacket() {

    }

    public void encode(FriendlyByteBuf buffer) {

    }

    public static StartCutscenePacket decode(FriendlyByteBuf buffer) {
        return new StartCutscenePacket();
    }

    public void receivePacket(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ClientCutsceneManager.getInstance().startCutscene();
        });
        context.get().setPacketHandled(true);
    }
}
