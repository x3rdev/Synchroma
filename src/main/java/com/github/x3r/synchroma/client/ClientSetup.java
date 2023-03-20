package com.github.x3r.synchroma.client;


import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.renderer.block.RipperdocChairRenderer;
import com.github.x3r.synchroma.client.renderer.block.RipperdocInterfaceRenderer;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = Synchroma.MOD_ID)
public final class ClientSetup {

    private ClientSetup(){}

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.RIPPERDOC_CHAIR_TILE.get(), RipperdocChairRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.RIPPERDOC_INTERFACE_TILE.get(), RipperdocInterfaceRenderer::new);
    }
}
