package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.renderer.DefaultedBlockItemGeoModel;
import com.github.x3r.synchroma.common.item.GeckoBlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BlockItemRegistry {

    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Synchroma.MOD_ID);

    public static final RegistryObject<BlockItem> FRAME_ITEM = BLOCK_ITEMS.register("frame",
            () -> new BlockItem(BlockRegistry.FRAME.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> EMPTY_CONTROLLER_ITEM = BLOCK_ITEMS.register("empty_controller",
            () -> new BlockItem(BlockRegistry.EMPTY_CONTROLLER.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> MIMIC_ITEM = BLOCK_ITEMS.register("mimic",
            () -> new BlockItem(BlockRegistry.MIMIC.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> WEAPON_WORKBENCH_ITEM = BLOCK_ITEMS.register("weapon_workbench",
            () -> new BlockItem(BlockRegistry.WEAPON_WORKBENCH.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> BASIC_CIRCUIT_PRINTER_ITEM = BLOCK_ITEMS.register("basic_circuit_printer",
            () -> new BlockItem(BlockRegistry.BASIC_CIRCUIT_PRINTER.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> ENERGY_BUFFER_ITEM = BLOCK_ITEMS.register("energy_buffer",
            () -> new BlockItem(BlockRegistry.ENERGY_BUFFER.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> BASIC_SOLAR_PANEL = BLOCK_ITEMS.register("basic_solar_panel",
            () -> new BlockItem(BlockRegistry.BASIC_SOLAR_PANEL.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> ENHANCED_SOLAR_PANEL = BLOCK_ITEMS.register("enhanced_solar_panel",
            () -> new BlockItem(BlockRegistry.ENHANCED_SOLAR_PANEL.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> ADVANCED_SOLAR_PANEL = BLOCK_ITEMS.register("advanced_solar_panel",
            () -> new BlockItem(BlockRegistry.ADVANCED_SOLAR_PANEL.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> BASIC_ENERGY_STORAGE = BLOCK_ITEMS.register("basic_energy_storage",
            () -> new BlockItem(BlockRegistry.BASIC_ENERGY_STORAGE.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> BASIC_PUMP = BLOCK_ITEMS.register("basic_pump",
            () -> new BlockItem(BlockRegistry.BASIC_PUMP.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> ENERGY_PIPE = BLOCK_ITEMS.register("energy_pipe",
            () -> new BlockItem(BlockRegistry.ENERGY_PIPE.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> MICROSCOPE = BLOCK_ITEMS.register("microscope",
            () -> new BlockItem(BlockRegistry.MICROSCOPE.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> TITANITE_CRYSTAL_ITEM = BLOCK_ITEMS.register("titanite_crystal",
            () -> new GeckoBlockItem(BlockRegistry.TITANITE_CRYSTAL.get(), new ResourceLocation(Synchroma.MOD_ID, "titanite_crystal"), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> HEX_SOLAR_PLATE_ITEM = BLOCK_ITEMS.register("hex_solar_plate",
            () -> new GeckoBlockItem(BlockRegistry.HEX_SOLAR_PLATE.get(), new ResourceLocation(Synchroma.MOD_ID, "hex_solar_plate"), ItemRegistry.DEFAULT_PROPERTIES));
}
