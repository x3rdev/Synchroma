package com.github.x3r.synchroma.common.cutscene;

import com.github.x3r.synchroma.common.packet.StartCutscenePacket;
import com.github.x3r.synchroma.common.packet.StopCutscenePacket;
import com.github.x3r.synchroma.common.packet.SynchromaPacketHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ServerCutsceneManager {

    static ServerCutsceneManager serverCutsceneManager = new ServerCutsceneManager();

    private final ConcurrentMap<ServerPlayer, Cutscene> cutscenes = new ConcurrentHashMap<>();

    public static ServerCutsceneManager getInstance(){
        return serverCutsceneManager;
    }

    public void startCutscene(ServerPlayer player, Cutscene cutscene) {
        exitCutscene(player);
        cutscenes.put(player, cutscene);
        cutscene.onStart();
        SynchromaPacketHandler.sendToClient(new StartCutscenePacket(), player);
    }

    public void exitCutscene(ServerPlayer player) {
        if(cutscenes.containsKey(player)) {
            cutscenes.get(player).onStop();
            SynchromaPacketHandler.sendToClient(new StopCutscenePacket(), player);
            cutscenes.remove(player);
        }
    }

    public Map<ServerPlayer, Cutscene> getCutscenes() {
        return cutscenes;
    }

    public Cutscene getCurrentCutscene(ServerPlayer player) {
        return cutscenes.get(player);
    }

    @SubscribeEvent
    public static void cutsceneTick(TickEvent.ServerTickEvent event) {
        ServerCutsceneManager manager = ServerCutsceneManager.getInstance();
        manager.getCutscenes().forEach((player, cutscene) -> {
            if(cutscene != null) {
                cutscene.tick();
            }
        });
    }
}
