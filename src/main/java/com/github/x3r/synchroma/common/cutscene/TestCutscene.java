package com.github.x3r.synchroma.common.cutscene;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class TestCutscene extends Cutscene {

    public TestCutscene(ServerPlayer player) {
        super(player);
    }

    @Override
    public void tick() {
        super.tick();
        if(tick > 100) {
            ServerCutsceneManager.getInstance().exitCutscene(player);
        }
    }

    @Override
    public Vec3 getPosition(int tick) {
        return player.getPosition(0).add(5, 5+(tick/20F), 5);
    }

    @Override
    public float getPitch(int tick) {
        return 0;
    }

    @Override
    public float getYaw(int tick) {
        return (float) (40*Math.sin(tick/20F));
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
