package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.FrameBlock;
import com.github.x3r.synchroma.common.block.basic_circuit_printer.BasicCircuitPrinterBlock;
import com.github.x3r.synchroma.common.block.centrifuge.CentrifugeBlock;
import com.github.x3r.synchroma.common.block.energy_buffer.EnergyBufferBlock;
import com.github.x3r.synchroma.common.block.energy_storage.BasicEnergyStorageBlock;
import com.github.x3r.synchroma.common.block.hex_solar_plate.HexSolarPlateBlock;
import com.github.x3r.synchroma.common.block.item_buffer.ItemBufferBlock;
import com.github.x3r.synchroma.common.block.microscope.MicroscopeBlock;
import com.github.x3r.synchroma.common.block.multiblock.MimicBlock;
import com.github.x3r.synchroma.common.block.pipes.energy_pipe.EnergyPipeBlock;
import com.github.x3r.synchroma.common.block.pump.BasicPumpBlock;
import com.github.x3r.synchroma.common.block.solar_panel.AdvancedSolarPanelBlock;
import com.github.x3r.synchroma.common.block.solar_panel.BasicSolarPanelBlock;
import com.github.x3r.synchroma.common.block.solar_panel.EnhancedSolarPanelBlock;
import com.github.x3r.synchroma.common.block.solar_panel.ZenithSolarPanelBlock;
import com.github.x3r.synchroma.common.block.titanite.TitaniteBlock;
import com.github.x3r.synchroma.common.block.weapon_workbench.WeaponWorkbenchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {

    public static final BlockBehaviour.Properties DEFAULT_PROPERTIES = BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK);

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Synchroma.MOD_ID);
    public static final RegistryObject<Block> FRAME = BLOCKS.register("frame",
            () -> new FrameBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> EMPTY_CONTROLLER = BLOCKS.register("empty_controller",
            () -> new Block(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> MIMIC = BLOCKS.register("mimic",
            () -> new MimicBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> WEAPON_WORKBENCH = BLOCKS.register("weapon_workbench",
            () -> new WeaponWorkbenchBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> BASIC_CIRCUIT_PRINTER = BLOCKS.register("basic_circuit_printer",
            () -> new BasicCircuitPrinterBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> ENERGY_BUFFER = BLOCKS.register("energy_buffer",
            () -> new EnergyBufferBlock(DEFAULT_PROPERTIES));

//    public static final RegistryObject<Block> FLUID_BUFFER = BLOCKS.register("fluid_buffer",
//            () -> new EnergyBufferBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> ITEM_BUFFER = BLOCKS.register("item_buffer",
            () -> new ItemBufferBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> BASIC_SOLAR_PANEL = BLOCKS.register("basic_solar_panel",
            () -> new BasicSolarPanelBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> ENHANCED_SOLAR_PANEL = BLOCKS.register("enhanced_solar_panel",
            () -> new EnhancedSolarPanelBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> ADVANCED_SOLAR_PANEL = BLOCKS.register("advanced_solar_panel",
            () -> new AdvancedSolarPanelBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> ZENITH_SOLAR_PANEL = BLOCKS.register("zenith_solar_panel",
            () -> new ZenithSolarPanelBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> BASIC_ENERGY_STORAGE = BLOCKS.register("basic_energy_storage",
            () -> new BasicEnergyStorageBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> BASIC_PUMP = BLOCKS.register("basic_pump",
            () -> new BasicPumpBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> CENTRIFUGE = BLOCKS.register("centrifuge",
            () -> new CentrifugeBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> ENERGY_PIPE = BLOCKS.register("energy_pipe",
            () -> new EnergyPipeBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> MICROSCOPE = BLOCKS.register("microscope",
            () -> new MicroscopeBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> TITANITE_CRYSTAL = BLOCKS.register("titanite_crystal",
            () -> new TitaniteBlock(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST)));
    public static final RegistryObject<Block> HEX_SOLAR_PLATE = BLOCKS.register("hex_solar_plate",
            () -> new HexSolarPlateBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> SHAFT = BLOCKS.register("shaft",
            () -> new Block(DEFAULT_PROPERTIES));
}

