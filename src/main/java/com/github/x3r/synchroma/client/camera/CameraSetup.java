package com.github.x3r.synchroma.client.camera;

import com.github.x3r.synchroma.client.screen.SurgeonScreen;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class CameraSetup {

    private CameraSetup(){}

    @SubscribeEvent
    public static void cameraSetupEvent(ViewportEvent.ComputeCameraAngles event) {
        if(Minecraft.getInstance().screen instanceof SurgeonScreen surgeonScreen) {
            Minecraft.getInstance().gameRenderer.getMainCamera().detached = true;
            double yRot = 180 - surgeonScreen.getMenu().getBlockEntity().getBlockState().getValue(ControllerBlock.FACING).toYRot();
            Vec3 offset = new Vec3(0, 2, -2).yRot((float) Math.toRadians(yRot));
            Vec3 blockPos = Vec3.atCenterOf(surgeonScreen.getMenu().getBlockEntity().getBlockPos());
            Vec3 cameraPos = blockPos.add(offset);
            event.getCamera().setPosition(cameraPos);
            event.setYaw((float) Math.toDegrees(Math.atan2(cameraPos.z - blockPos.z, cameraPos.x - blockPos.x))+72.5F);
            event.setPitch(35F);
        }
    }
}
