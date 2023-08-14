package com.github.x3r.synchroma.client;


import com.github.x3r.synchroma.client.particle.SparkParticle;
import com.github.x3r.synchroma.client.renderer.block.*;
import com.github.x3r.synchroma.client.renderer.block.solar_panel.EnhancedSolarPanelRenderer;
import com.github.x3r.synchroma.client.renderer.entity.BulletRenderer;
import com.github.x3r.synchroma.client.screen.BasicSolarPanelScreen;
import com.github.x3r.synchroma.client.screen.BasicPumpScreen;
import com.github.x3r.synchroma.client.screen.EnhancedSolarPanelScreen;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.common.registry.EntityRegistry;
import com.github.x3r.synchroma.common.registry.MenuTypeRegistry;
import com.github.x3r.synchroma.common.registry.ParticleRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class ClientSetup {

    private ClientSetup(){}

    @SubscribeEvent
    public static void setup(final FMLClientSetupEvent event) {
        EntityRenderers.register(EntityRegistry.BULLET_PROJECTILE.get(), BulletRenderer::new);
        event.enqueueWork(() -> {
            MenuScreens.register(MenuTypeRegistry.BASIC_SOLAR_PANEL.get(), BasicSolarPanelScreen::new);
            MenuScreens.register(MenuTypeRegistry.ENHANCED_SOLAR_PANEL.get(), EnhancedSolarPanelScreen::new);
            MenuScreens.register(MenuTypeRegistry.BASIC_PUMP.get(), BasicPumpScreen::new);
        });
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.WEAPON_WORKBENCH.get(), pContext -> new WeaponWorkbenchRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.BASIC_CIRCUIT_PRINTER.get(), pContext -> new BasicCircuitPrinterRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.TITANITE_CRYSTAL.get(), pContext -> new TitaniteCrystalRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.MICROSCOPE.get(), pContext -> new MicroscopeRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.ENHANCED_SOLAR_PANEL.get(), pContext -> new EnhancedSolarPanelRenderer());
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleRegistry.SPARK.get(), SparkParticle.SparkParticleProvider::new);
    }
}
