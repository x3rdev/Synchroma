package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.menu.BasicSolarPanelMenu;
import com.github.x3r.synchroma.common.block.weapon_workbench.WeaponWorkbenchMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Synchroma.MOD_ID);

    public static final RegistryObject<MenuType<WeaponWorkbenchMenu>> WEAPON_WORKBENCH_MENU = MENUS.register("weapon_workbench_menu",
            () -> new MenuType<>(WeaponWorkbenchMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final RegistryObject<MenuType<BasicSolarPanelMenu>> BASIC_SOLAR_PANEL_MENU = MENUS.register("basic_solar_panel_menu",
            () -> new MenuType<>(BasicSolarPanelMenu::new, FeatureFlags.DEFAULT_FLAGS));

}
