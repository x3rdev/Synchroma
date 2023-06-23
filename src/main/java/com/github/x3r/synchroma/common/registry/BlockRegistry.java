package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
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
            () -> new RipperdocChairBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> RIPPERDOC_INTERFACE_BLOCK = BLOCKS.register("ripperdoc_interface",
            () -> new RipperdocInterfaceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
}

