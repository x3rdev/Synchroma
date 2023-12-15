package com.github.x3r.synchroma.client.cutscene;

import com.github.x3r.synchroma.Synchroma;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientCutsceneManager {
    static ClientCutsceneManager clientCutsceneManager = new ClientCutsceneManager();
    private boolean playerInCutscene = false;
    private Vec3 pos;
    private Vec3 posO;
    private float pitch;
    private float pitchO;
    private float yaw;
    private float yawO;
    private float roll;
    private float rollO;

    private float partialTickOffset;

    public static ClientCutsceneManager getInstance(){
        return clientCutsceneManager;
    }

    public void startCutscene() {
        LocalPlayer player = Minecraft.getInstance().player;
        posO = player.position();
        pitchO = player.getXRot();
        yawO = player.getYRot();
        rollO = 0;
        pos = posO;
        pitch = pitchO;
        yaw = yawO;
        roll = rollO;
        playerInCutscene = true;
    }

    public void stopCutscene() {
        playerInCutscene = false;
    }

    public void setValues(Vec3 pos, float pitch, float yaw, float roll) {
        partialTickOffset = 1 - Minecraft.getInstance().getPartialTick();
        this.posO = this.pos;
        this.pitchO = this.pitch;
        this.yawO = this.yaw;
        this.rollO = this.roll;
        this.pos = pos;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    @SubscribeEvent
    public static void cutsceneCameraEvent(ViewportEvent.ComputeCameraAngles event) {
        ClientCutsceneManager manager = ClientCutsceneManager.getInstance();
        if(manager.playerInCutscene) {
            float frameTime = (float) (event.getPartialTick()-getInstance().partialTickOffset);
            Vec3 lerpPos = manager.posO.lerp(manager.pos, frameTime);
            event.getCamera().setPosition(lerpPos);
            event.setPitch(Mth.rotLerp(frameTime, manager.pitchO, manager.pitch));
            event.setYaw(Mth.rotLerp(frameTime, manager.yawO, manager.yaw));
            event.setRoll(Mth.rotLerp(frameTime, manager.rollO, manager.roll));
        }
    }
}
