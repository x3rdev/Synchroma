package com.github.x3r.synchroma.common.packet;

import com.github.x3r.synchroma.client.cutscene.ClientCutsceneManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class SyncCutscenePacket {

    private final Vec3 pos;
    private final float pitch;
    private final float yaw;
    private final float roll;

    public SyncCutscenePacket(Vec3 pos, float pitch, float yaw, float roll) {
        this.pos = pos;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVector3f(pos.toVector3f());
        buffer.writeFloat(pitch);
        buffer.writeFloat(yaw);
        buffer.writeFloat(roll);
    }

    public static SyncCutscenePacket decode(FriendlyByteBuf buffer) {
        Vector3f v3f = buffer.readVector3f();
        Vec3 pos = new Vec3(v3f.x, v3f.y, v3f.z);
        float pitch = buffer.readFloat();
        float yaw = buffer.readFloat();
        float roll = buffer.readFloat();
        return new SyncCutscenePacket(pos, pitch, yaw, roll);
    }

    public void receivePacket(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ClientCutsceneManager.getInstance().setValues(pos, pitch, yaw, roll);
        });
        context.get().setPacketHandled(true);
    }
}
