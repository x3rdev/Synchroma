package com.github.x3r.synchroma.common.cutscene;

import com.github.x3r.synchroma.common.cutscene.interpolation.Interpolation;
import com.github.x3r.synchroma.common.cutscene.keyframe.Keyframe;
import com.github.x3r.synchroma.common.cutscene.keyframe.KeyframeSequence;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class SurgeonCutscene extends Cutscene {

//    private static final KeyframeSequence xSequence = new KeyframeSequence(
//            new Keyframe(20, 3, Interpolation.LINEAR),
//            new Keyframe(40, 5, Interpolation.LINEAR),
//            new Keyframe(60, 6, Interpolation.LINEAR));
//
    private static final KeyframeSequence ySequence = new KeyframeSequence(
            new Keyframe(0, 2, Interpolation.LINEAR),
            new Keyframe(20, 1.4F, Interpolation.LINEAR),
            new Keyframe(40, 1.3F, Interpolation.LINEAR),
            new Keyframe(80, 1.2F, Interpolation.LINEAR),
            new Keyframe(120, 2F, Interpolation.LINEAR));
//
//    private static final KeyframeSequence zSequence = new KeyframeSequence(
//            new Keyframe(20, 1, Interpolation.LINEAR),
//            new Keyframe(40, 2, Interpolation.LINEAR),
//            new Keyframe(60, 2.5F, Interpolation.LINEAR));

    private final Vec3 pos0;

    private double rot = 0;
    public SurgeonCutscene(ServerPlayer player) {
        super(player, 140);
        pos0 = player.getPosition(0);
    }

    @Override
    public Vec3 getPosition(int tick) {
        if(tick <= 70) {
            rot += ((1.25*Math.PI)/140F);
        } else {
            rot += ((0.75*Math.PI)/140F);
        }
        return new Vec3(pos0.x+3*Math.sin(rot), pos0.y+ySequence.getValue(tick),pos0.z+3*Math.cos(rot));
    }

    @Override
    public float getPitch(int tick) {
        Vec3 playerPos = player.getPosition(0);
        double hDist = getPosition(tick).multiply(1, 0, 1)
                .distanceTo(playerPos.multiply(1, 0, 1));
        return (float) (Math.toDegrees(Math.atan2(getPosition(tick).y-playerPos.y, hDist)));
    }

    @Override
    public float getYaw(int tick) {
        return (float) (180-Math.toDegrees(Math.atan2(getPosition(tick).x-player.getX(), getPosition(tick).z-player.getZ())));
    }

    @Override
    public float getRoll(int tick) {
        return 0;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
