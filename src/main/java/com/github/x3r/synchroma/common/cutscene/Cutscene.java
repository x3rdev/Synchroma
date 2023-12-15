package com.github.x3r.synchroma.common.cutscene;

import com.github.x3r.synchroma.common.packet.SyncCutscenePacket;
import com.github.x3r.synchroma.common.packet.SynchromaPacketHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public abstract class Cutscene {

    public final ServerPlayer player;

    protected int tick = 0;

    protected Cutscene(ServerPlayer player) {
        this.player = player;
    }

    public void tick() {
        SynchromaPacketHandler.sendToClient(new SyncCutscenePacket(getPosition(tick), getPitch(tick), getYaw(tick), getRoll(tick)), player);
        System.out.println(player.level().getGameTime());
        tick++;
    }

    public abstract Vec3 getPosition(int tick);
    public abstract float getPitch(int tick);
    public abstract float getYaw(int tick);
    public abstract float getRoll(int tick);
    public abstract void onStart();

    public abstract void onStop();
}
