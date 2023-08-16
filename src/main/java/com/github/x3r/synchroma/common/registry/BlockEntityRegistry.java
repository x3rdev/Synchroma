package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.basic_circuit_printer.BasicCircuitPrinterBlockEntity;
import com.github.x3r.synchroma.common.block.energy_buffer.EnergyBufferBlockEntity;
import com.github.x3r.synchroma.common.block.energy_storage.BasicEnergyStorageBlockEntity;
import com.github.x3r.synchroma.common.block.hex_solar_plate.HexSolarPlateBlockEntity;
import com.github.x3r.synchroma.common.block.microscope.MicroscopeBlockEntity;
import com.github.x3r.synchroma.common.block.multiblock.MimicBlockEntity;
import com.github.x3r.synchroma.common.block.pipes.energy_pipe.EnergyPipeBlockEntity;
import com.github.x3r.synchroma.common.block.pump.BasicPumpBlockEntity;
import com.github.x3r.synchroma.common.block.solar_panel.AdvancedSolarPanelBlockEntity;
import com.github.x3r.synchroma.common.block.solar_panel.BasicSolarPanelBlockEntity;
import com.github.x3r.synchroma.common.block.solar_panel.EnhancedSolarPanelBlockEntity;
import com.github.x3r.synchroma.common.block.titanite.TitaniteBlockEntity;
import com.github.x3r.synchroma.common.block.weapon_workbench.WeaponWorkbenchBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Synchroma.MOD_ID);
    public static final RegistryObject<BlockEntityType<MimicBlockEntity>> MIMIC = BLOCK_ENTITIES.register("mimic",
            () -> BlockEntityType.Builder.of(MimicBlockEntity::new, BlockRegistry.MIMIC.get()).build(null));
    public static final RegistryObject<BlockEntityType<WeaponWorkbenchBlockEntity>> WEAPON_WORKBENCH = BLOCK_ENTITIES.register("weapon_workbench",
            () -> BlockEntityType.Builder.of(WeaponWorkbenchBlockEntity::new, BlockRegistry.WEAPON_WORKBENCH.get()).build(null));
    public static final RegistryObject<BlockEntityType<BasicCircuitPrinterBlockEntity>> BASIC_CIRCUIT_PRINTER = BLOCK_ENTITIES.register("basic_circuit_printer",
            () -> BlockEntityType.Builder.of(BasicCircuitPrinterBlockEntity::new, BlockRegistry.BASIC_CIRCUIT_PRINTER.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyBufferBlockEntity>> ENERGY_BUFFER = BLOCK_ENTITIES.register("energy_buffer",
            () -> BlockEntityType.Builder.of(EnergyBufferBlockEntity::new, BlockRegistry.ENERGY_BUFFER.get()).build(null));

    public static final RegistryObject<BlockEntityType<BasicSolarPanelBlockEntity>> BASIC_SOLAR_PANEL = BLOCK_ENTITIES.register("basic_solar_panel",
            () -> BlockEntityType.Builder.of(BasicSolarPanelBlockEntity::new, BlockRegistry.BASIC_SOLAR_PANEL.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnhancedSolarPanelBlockEntity>> ENHANCED_SOLAR_PANEL = BLOCK_ENTITIES.register("enhanced_solar_panel",
            () -> BlockEntityType.Builder.of(EnhancedSolarPanelBlockEntity::new, BlockRegistry.ENHANCED_SOLAR_PANEL.get()).build(null));
    public static final RegistryObject<BlockEntityType<AdvancedSolarPanelBlockEntity>> ADVANCED_SOLAR_PANEL = BLOCK_ENTITIES.register("advanced_solar_panel",
            () -> BlockEntityType.Builder.of(AdvancedSolarPanelBlockEntity::new, BlockRegistry.ADVANCED_SOLAR_PANEL.get()).build(null));
    public static final RegistryObject<BlockEntityType<BasicPumpBlockEntity>> BASIC_PUMP = BLOCK_ENTITIES.register("basic_pump",
            () -> BlockEntityType.Builder.of(BasicPumpBlockEntity::new, BlockRegistry.BASIC_PUMP.get()).build(null));
    public static final RegistryObject<BlockEntityType<BasicEnergyStorageBlockEntity>> BASIC_ENERGY_STORAGE = BLOCK_ENTITIES.register("basic_energy_storage",
            () -> BlockEntityType.Builder.of(BasicEnergyStorageBlockEntity::new, BlockRegistry.BASIC_ENERGY_STORAGE.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyPipeBlockEntity>> ENERGY_PIPE = BLOCK_ENTITIES.register("energy_pipe",
            () -> BlockEntityType.Builder.of(EnergyPipeBlockEntity::new, BlockRegistry.ENERGY_PIPE.get()).build(null));
    public static final RegistryObject<BlockEntityType<MicroscopeBlockEntity>> MICROSCOPE = BLOCK_ENTITIES.register("microscope",
            () -> BlockEntityType.Builder.of(MicroscopeBlockEntity::new, BlockRegistry.MICROSCOPE.get()).build(null));
    public static final RegistryObject<BlockEntityType<TitaniteBlockEntity>> TITANITE_CRYSTAL = BLOCK_ENTITIES.register("titanite_crystal",
            () -> BlockEntityType.Builder.of(TitaniteBlockEntity::new, BlockRegistry.TITANITE_CRYSTAL.get()).build(null));
    public static final RegistryObject<BlockEntityType<HexSolarPlateBlockEntity>> HEX_SOLAR_PLATE = BLOCK_ENTITIES.register("hex_solar_plate",
            () -> BlockEntityType.Builder.of(HexSolarPlateBlockEntity::new, BlockRegistry.HEX_SOLAR_PLATE.get()).build(null));
}
