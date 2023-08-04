package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.basic_pump.BasicPumpBlock;
import com.github.x3r.synchroma.common.block.energy_buffer.EnergyOutputBufferBlock;
import com.github.x3r.synchroma.common.block.microscope.MicroscopeBlock;
import com.github.x3r.synchroma.common.block.pipes.energy_pipe.EnergyPipeBlock;
import com.github.x3r.synchroma.common.block.solar_panel.BasicSolarPanelBlock;
import com.github.x3r.synchroma.common.block.FrameBlock;
import com.github.x3r.synchroma.common.block.basic_circuit_printer.BasicCircuitPrinterBlock;
import com.github.x3r.synchroma.common.block.controller.ControllerBlock;
import com.github.x3r.synchroma.common.block.energy_buffer.EnergyInputBufferBlock;
import com.github.x3r.synchroma.common.block.multiblock.MultiBlockPart;
import com.github.x3r.synchroma.common.block.ripperdoc_chair.RipperdocChairBlock;
import com.github.x3r.synchroma.common.block.ripperdoc_interface.RipperdocInterfaceBlock;
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

    public static final RegistryObject<Block> RIPPERDOC_CHAIR_BLOCK = BLOCKS.register("ripperdoc_chair",
            () -> new RipperdocChairBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> RIPPERDOC_INTERFACE_BLOCK = BLOCKS.register("ripperdoc_interface",
            () -> new RipperdocInterfaceBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> FRAME = BLOCKS.register("frame",
            () -> new FrameBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> CONTROLLER = BLOCKS.register("controller",
            () -> new ControllerBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> MULTI_BLOCK_PART = BLOCKS.register("multi_block_part",
            () -> new MultiBlockPart(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> WEAPON_WORKBENCH = BLOCKS.register("weapon_workbench",
            () -> new WeaponWorkbenchBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> BASIC_CIRCUIT_PRINTER = BLOCKS.register("basic_circuit_printer",
            () -> new BasicCircuitPrinterBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> ENERGY_INPUT_BUFFER = BLOCKS.register("energy_input_buffer",
            () -> new EnergyInputBufferBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> ENERGY_OUTPUT_BUFFER = BLOCKS.register("energy_output_buffer",
            () -> new EnergyOutputBufferBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> TITANITE_CRYSTAL = BLOCKS.register("titanite_crystal",
            () -> new TitaniteBlock(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST)));
    public static final RegistryObject<Block> BASIC_SOLAR_PANEL = BLOCKS.register("basic_solar_panel",
            () -> new BasicSolarPanelBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> BASIC_PUMP = BLOCKS.register("basic_pump",
            () -> new BasicPumpBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> ENERGY_PIPE = BLOCKS.register("energy_pipe",
            () -> new EnergyPipeBlock(DEFAULT_PROPERTIES));
    public static final RegistryObject<Block> MICROSCOPE = BLOCKS.register("microscope",
            () -> new MicroscopeBlock(DEFAULT_PROPERTIES));
}

