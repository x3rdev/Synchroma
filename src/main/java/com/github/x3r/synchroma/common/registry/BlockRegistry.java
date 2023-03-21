package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.cable.PowerCableLBlock;
import com.github.x3r.synchroma.common.block.cable.PowerCableStraightBlock;
import com.github.x3r.synchroma.common.block.ripperdoc_chair.RipperdocChairBlock;
import com.github.x3r.synchroma.common.block.ripperdoc_interface.RipperdocInterfaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Synchroma.MOD_ID);

    public static final RegistryObject<Block> RIPPERDOC_CHAIR_BLOCK = BLOCKS.register("ripperdoc_chair",
            () -> new RipperdocChairBlock(BlockBehaviour.Properties.of(Blocks.IRON_BLOCK.defaultBlockState().getMaterial())));

    public static final RegistryObject<Block> RIPPERDOC_INTERFACE_BLOCK = BLOCKS.register("ripperdoc_interface",
            () -> new RipperdocInterfaceBlock(BlockBehaviour.Properties.of(Blocks.IRON_BLOCK.defaultBlockState().getMaterial())));

    public static final RegistryObject<Block> POWER_CABLE_STRAIGHT = BLOCKS.register("power_cable_straight",
            () -> new PowerCableStraightBlock(BlockBehaviour.Properties.of(Blocks.IRON_BLOCK.defaultBlockState().getMaterial())));

    public static final RegistryObject<Block> POWER_CABLE_L = BLOCKS.register("power_cable_l",
            () -> new PowerCableLBlock(BlockBehaviour.Properties.of(Blocks.IRON_BLOCK.defaultBlockState().getMaterial())));
}

