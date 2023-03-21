package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockItemRegistry {

    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Synchroma.MOD_ID);

    public static final RegistryObject<BlockItem> RIPPERDOC_CHAIR_ITEM = BLOCK_ITEMS.register("ripperdoc_chair",
            () -> new BlockItem(BlockRegistry.RIPPERDOC_CHAIR_BLOCK.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> RIPPERDOC_INTERFACE_ITEM = BLOCK_ITEMS.register("ripperdoc_interface",
            () -> new BlockItem(BlockRegistry.RIPPERDOC_INTERFACE_BLOCK.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> POWER_CABLE_STRAIGHT_ITEM = BLOCK_ITEMS.register("power_cable_straight",
            () -> new BlockItem(BlockRegistry.POWER_CABLE_STRAIGHT.get(), ItemRegistry.DEFAULT_PROPERTIES));

    public static final RegistryObject<BlockItem> POWER_CABLE_L_ITEM = BLOCK_ITEMS.register("power_cable_l",
            () -> new BlockItem(BlockRegistry.POWER_CABLE_L.get(), ItemRegistry.DEFAULT_PROPERTIES));
}
