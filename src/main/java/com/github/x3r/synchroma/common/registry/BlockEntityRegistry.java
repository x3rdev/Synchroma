package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.ripperdoc_chair.RipperdocChairBlockEntity;
import com.github.x3r.synchroma.common.block.ripperdoc_interface.RipperdocInterfaceBlock;
import com.github.x3r.synchroma.common.block.ripperdoc_interface.RipperdocInterfaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Synchroma.MOD_ID);

    public static final RegistryObject<BlockEntityType<RipperdocChairBlockEntity>> RIPPERDOC_CHAIR_TILE = BLOCK_ENTITIES.register("ripperdoc_chair",
            () -> BlockEntityType.Builder.of(RipperdocChairBlockEntity::new, BlockRegistry.RIPPERDOC_CHAIR_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<RipperdocInterfaceBlockEntity>> RIPPERDOC_INTERFACE_TILE = BLOCK_ENTITIES.register("ripperdoc_interface",
            () -> BlockEntityType.Builder.of(RipperdocInterfaceBlockEntity::new, BlockRegistry.RIPPERDOC_INTERFACE_BLOCK.get()).build(null));
}
