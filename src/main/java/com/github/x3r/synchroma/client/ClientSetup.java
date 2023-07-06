package com.github.x3r.synchroma.client;


import com.github.x3r.synchroma.client.particle.SparkParticle;
import com.github.x3r.synchroma.client.renderer.block.FrameRenderer;
import com.github.x3r.synchroma.client.renderer.block.RipperdocChairRenderer;
import com.github.x3r.synchroma.client.renderer.block.RipperdocInterfaceRenderer;
import com.github.x3r.synchroma.client.renderer.block.WeaponWorkbenchRenderer;
import com.github.x3r.synchroma.client.renderer.entity.BulletRenderer;
import com.github.x3r.synchroma.common.block.weapon_workbench.WeaponWorkbenchBlock;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.common.registry.EntityRegistry;
import com.github.x3r.synchroma.common.registry.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class ClientSetup {

    private ClientSetup(){}

    @SubscribeEvent
    public static void setup(final FMLClientSetupEvent event) {
        EntityRenderers.register(EntityRegistry.BULLET_PROJECTILE.get(), BulletRenderer::new);
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.RIPPERDOC_CHAIR_TILE.get(), pContext -> new RipperdocChairRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.RIPPERDOC_INTERFACE_TILE.get(), pContext -> new RipperdocInterfaceRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.WEAPON_WORKBENCH.get(), pContext -> new WeaponWorkbenchRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.FRAME.get(), pContext -> new FrameRenderer());
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleRegistry.SPARK.get(), SparkParticle.SparkParticleProvider::new);
    }
}
