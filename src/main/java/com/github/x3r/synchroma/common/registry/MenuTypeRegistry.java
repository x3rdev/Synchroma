package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MenuTypeRegistry {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Synchroma.MOD_ID);
}
