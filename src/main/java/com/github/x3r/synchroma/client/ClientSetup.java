package com.github.x3r.synchroma.client;


import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.particle.SparkParticle;
import com.github.x3r.synchroma.client.renderer.block.*;
import com.github.x3r.synchroma.client.renderer.block.solar_panel.AdvancedSolarPanelRenderer;
import com.github.x3r.synchroma.client.renderer.block.solar_panel.EnhancedSolarPanelRenderer;
import com.github.x3r.synchroma.client.renderer.block.solar_panel.HexSolarPlateRenderer;
import com.github.x3r.synchroma.client.renderer.block.solar_panel.ZenithSolarPanelRenderer;
import com.github.x3r.synchroma.client.renderer.entity.BulletRenderer;
import com.github.x3r.synchroma.client.renderer.entity.CiabattaRenderer;
import com.github.x3r.synchroma.client.renderer.entity.RideableRenderer;
import com.github.x3r.synchroma.client.screen.*;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.common.registry.EntityRegistry;
import com.github.x3r.synchroma.common.registry.MenuTypeRegistry;
import com.github.x3r.synchroma.common.registry.ParticleRegistry;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Objects;

public final class ClientSetup {

    @Nullable
    private static ShaderInstance rendertypeHologramShader;

    private ClientSetup(){}

    @SubscribeEvent
    public static void setup(final FMLClientSetupEvent event) {
        EntityRenderers.register(EntityRegistry.BULLET_PROJECTILE.get(), BulletRenderer::new);
        EntityRenderers.register(EntityRegistry.RIDEABLE.get(), RideableRenderer::new);
        event.enqueueWork(() -> {
            MenuScreens.register(MenuTypeRegistry.BASIC_SOLAR_PANEL.get(), BasicSolarPanelScreen::new);
            MenuScreens.register(MenuTypeRegistry.ENHANCED_SOLAR_PANEL.get(), EnhancedSolarPanelScreen::new);
            MenuScreens.register(MenuTypeRegistry.ADVANCED_SOLAR_PANEL.get(), AdvancedSolarPanelScreen::new);
            MenuScreens.register(MenuTypeRegistry.ZENITH_SOLAR_PANEL.get(), ZenithSolarPanelScreen::new);
            MenuScreens.register(MenuTypeRegistry.BASIC_PUMP.get(), BasicPumpScreen::new);
            MenuScreens.register(MenuTypeRegistry.CENTRIFUGE.get(), CentrifugeScreen::new);
            MenuScreens.register(MenuTypeRegistry.WIND_TURBINE.get(), WindTurbineScreen::new);
            MenuScreens.register(MenuTypeRegistry.FABRICATOR.get(), FabricatorScreen::new);
            MenuScreens.register(MenuTypeRegistry.SURGEON.get(), SurgeonScreen::new);
            MenuScreens.register(MenuTypeRegistry.CIRCUIT_PRINTER.get(), CircuitPrinterScreen::new);
        });
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.WEAPON_WORKBENCH.get(), context -> new WeaponWorkbenchRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.CIRCUIT_PRINTER.get(), context -> new CircuitPrinterRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.MICROSCOPE.get(), context -> new MicroscopeRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.ENHANCED_SOLAR_PANEL.get(), context -> new EnhancedSolarPanelRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.ADVANCED_SOLAR_PANEL.get(), context -> new AdvancedSolarPanelRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.ZENITH_SOLAR_PANEL.get(), context -> new ZenithSolarPanelRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.CENTRIFUGE.get(), context -> new CentrifugeRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.TITANITE_CRYSTAL.get(), context -> new TitaniteCrystalRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.HEX_SOLAR_PLATE.get(), context -> new HexSolarPlateRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.WIND_TURBINE.get(), context -> new WindTurbineRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.FABRICATOR.get(), context -> new FabricatorRenderer());
        event.registerBlockEntityRenderer(BlockEntityRegistry.SURGEON.get(), context -> new SurgeonRenderer());

        event.registerEntityRenderer(EntityRegistry.CIABATTA.get(), CiabattaRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleRegistry.SPARK.get(), SparkParticle.SparkParticleProvider::new);
    }

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) {
        ResourceProvider provider = event.getResourceProvider();
        try {
            event.registerShader(new ShaderInstance(provider, new ResourceLocation(Synchroma.MOD_ID, "hologram"), DefaultVertexFormat.BLOCK), shaderInstance -> {
                rendertypeHologramShader = shaderInstance;
            });
        } catch (IOException e) {
            Synchroma.LOGGER.warn("Failed to load shader", e);
        }
    }
    public static ShaderInstance getHologramShader() {
        return Objects.requireNonNull(rendertypeHologramShader, "Attempted to call getHologramShader before shaders have finished loading.");
    }

}
