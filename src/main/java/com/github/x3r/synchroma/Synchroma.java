package com.github.x3r.synchroma;

import com.github.x3r.synchroma.client.ClientSetup;
import com.github.x3r.synchroma.common.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod("synchroma")
public class Synchroma {

    public static final String MOD_ID = "synchroma";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Synchroma() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        registerDeferredRegisters(modEventBus);

        modEventBus.addListener(ClientSetup::setup);
        modEventBus.addListener(ClientSetup::registerRenderers);
    }

    private static void registerDeferredRegisters(IEventBus bus) {
        ItemRegistry.ITEMS.register(bus);
        ItemRegistry.SynchromaItemTab.CREATIVE_MODE_TABS.register(bus);
        BlockRegistry.BLOCKS.register(bus);
        EntityRegistry.ENTITIES.register(bus);
        BlockItemRegistry.BLOCK_ITEMS.register(bus);
        BlockEntityRegistry.BLOCK_ENTITIES.register(bus);
    }
}
