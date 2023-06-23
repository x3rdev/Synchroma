package com.github.x3r.synchroma.client;


import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.renderer.block.RipperdocChairRenderer;
import com.github.x3r.synchroma.client.renderer.block.RipperdocInterfaceRenderer;
import com.github.x3r.synchroma.client.renderer.entity.BulletRenderer;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.common.registry.EntityRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class ClientSetup {

    private ClientSetup(){}

    public static void setup(final FMLClientSetupEvent event) {
        EntityRenderers.register(EntityRegistry.BULLET_PROJECTILE.get(), BulletRenderer::new);
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.RIPPERDOC_CHAIR_TILE.get(), RipperdocChairRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.RIPPERDOC_INTERFACE_TILE.get(), RipperdocInterfaceRenderer::new);
    }
}
