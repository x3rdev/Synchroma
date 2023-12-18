package com.github.x3r.synchroma.client.camera;

import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class CameraSetup {

    private CameraSetup(){}



    //        if(Minecraft.getInstance().screen instanceof SurgeonScreen surgeonScreen) {
//            Minecraft.getInstance().gameRenderer.getMainCamera().detached = true;
//            SurgeonBlockEntity blockEntity = surgeonScreen.getMenu().getBlockEntity();
//            SurgeonModel model = (SurgeonModel) RenderUtils.getGeoModelForBlock(blockEntity);
//            model.getBone("camera").ifPresent(yRotBone -> {
//                model.getBone("camera_point").ifPresent(geoBone -> {
//                    float yRot = (float) Math.toRadians(180 - blockEntity.getBlockState().getValue(ControllerBlock.FACING).toYRot());
//                    Vec3 blockPos = Vec3.atCenterOf(surgeonScreen.getMenu().getBlockEntity().getBlockPos()).add(new Vec3(1, 0, 0).yRot(yRot));
//                    Vec3 bonePos = new Vec3(
//                            (geoBone.getPivotX()+geoBone.getPosX())/16F,
//                            (geoBone.getPivotY()+geoBone.getPosY())/16F,
//                            (geoBone.getPivotZ()+geoBone.getPosZ())/16F).yRot(yRot).yRot(yRotBone.getRotY());
//                    Vec3 cameraPos = blockPos.add(bonePos);
//                    event.getCamera().setPosition(cameraPos);
//                    event.setYaw((float) (180-Math.toDegrees(Math.atan2(cameraPos.x-blockPos.x, cameraPos.z-blockPos.z))));
//                    event.setPitch(35F);
//                });
//            });
//        }
}
