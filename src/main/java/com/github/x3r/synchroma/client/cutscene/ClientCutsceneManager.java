package com.github.x3r.synchroma.client.cutscene;

import com.github.x3r.synchroma.Synchroma;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.awt.event.KeyEvent;

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

    private float partialTick;

    public static ClientCutsceneManager getInstance(){
        return clientCutsceneManager;
    }

    public void startCutscene() {
        LocalPlayer player = Minecraft.getInstance().player;
        Minecraft.getInstance().setScreen(null);
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
        Minecraft.getInstance().gameRenderer.setRenderHand(true);
        Minecraft.getInstance().gameRenderer.getMainCamera().detached = false;
        Minecraft.getInstance().gameRenderer.getMainCamera().reset();
    }

    public void setValues(Vec3 pos, float pitch, float yaw, float roll) {
        this.posO = this.pos;
        this.pitchO = this.pitch;
        this.yawO = this.yaw;
        this.rollO = this.roll;
        this.pos = pos;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        this.partialTick = 0;
    }

    @SubscribeEvent
    public static void cutsceneCameraEvent(ViewportEvent.ComputeCameraAngles event) {
        ClientCutsceneManager manager = ClientCutsceneManager.getInstance();
        if(manager.playerInCutscene) {
            Minecraft.getInstance().gameRenderer.setRenderHand(false);
            Minecraft.getInstance().gameRenderer.getMainCamera().detached = true;

            manager.partialTick += Minecraft.getInstance().getDeltaFrameTime();
            Vec3 lerpPos = manager.posO.lerp(manager.pos, manager.partialTick);
            event.getCamera().setPosition(lerpPos);
            event.setPitch(Mth.rotLerp(manager.partialTick, manager.pitchO, manager.pitch));
            event.setYaw(Mth.rotLerp(manager.partialTick, manager.yawO, manager.yaw));
            event.setRoll(Mth.rotLerp(manager.partialTick, manager.rollO, manager.roll));
        }
    }

//    @SubscribeEvent
//    public static void cancelCutsceneInputEvent(InputEvent.MouseButton.Pre event) {
//        if(ClientCutsceneManager.getInstance().playerInCutscene) {
//            event.setCanceled(true);
//        }
//    }
//
//    @SubscribeEvent
//    public static void cancelCutsceneInputEvent(InputEvent.MouseScrollingEvent event) {
//        if(ClientCutsceneManager.getInstance().playerInCutscene) {
//            event.setCanceled(true);
//        }
//    }
//
//    @SubscribeEvent
//    public static void cancelCutsceneInputEvent(InputEvent.Key event) {
//        if(ClientCutsceneManager.getInstance().playerInCutscene) {
//            event.setCanceled(true);
//        }
//    }
//
//    @SubscribeEvent
//    public static void cancelCutsceneInputEvent(InputEvent.InteractionKeyMappingTriggered event) {
//        if(ClientCutsceneManager.getInstance().playerInCutscene) {
//            event.setCanceled(true);
//        }
//    }
}
