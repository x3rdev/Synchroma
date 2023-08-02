package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.menu.BasicPumpMenu;
import com.github.x3r.synchroma.client.menu.BasicSolarPanelMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Synchroma.MOD_ID);
    public static final RegistryObject<MenuType<BasicSolarPanelMenu>> BASIC_SOLAR_PANEL = MENUS.register("basic_solar_panel_menu",
            () -> IForgeMenuType.create(BasicSolarPanelMenu::new));
    public static final RegistryObject<MenuType<BasicPumpMenu>> BASIC_PUMP = MENUS.register("basic_pump_menu",
            () -> IForgeMenuType.create(BasicPumpMenu::new));

}
