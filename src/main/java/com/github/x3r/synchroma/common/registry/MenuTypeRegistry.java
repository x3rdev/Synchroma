package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.menu.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Synchroma.MOD_ID);
    public static final RegistryObject<MenuType<BasicSolarPanelMenu>> BASIC_SOLAR_PANEL = MENUS.register("basic_solar_panel",
            () -> IForgeMenuType.create(BasicSolarPanelMenu::new));
    public static final RegistryObject<MenuType<EnhancedSolarPanelMenu>> ENHANCED_SOLAR_PANEL = MENUS.register("enhanced_solar_panel",
            () -> IForgeMenuType.create(EnhancedSolarPanelMenu::new));
    public static final RegistryObject<MenuType<AdvancedSolarPanelMenu>> ADVANCED_SOLAR_PANEL = MENUS.register("advanced_solar_panel",
            () -> IForgeMenuType.create(AdvancedSolarPanelMenu::new));
    public static final RegistryObject<MenuType<ZenithSolarPanelMenu>> ZENITH_SOLAR_PANEL = MENUS.register("zenith_solar_panel",
            () -> IForgeMenuType.create(ZenithSolarPanelMenu::new));
    public static final RegistryObject<MenuType<BasicPumpMenu>> BASIC_PUMP = MENUS.register("basic_pump",
            () -> IForgeMenuType.create(BasicPumpMenu::new));
    public static final RegistryObject<MenuType<CentrifugeMenu>> CENTRIFUGE = MENUS.register("centrifuge",
            () -> IForgeMenuType.create(CentrifugeMenu::new));
    public static final RegistryObject<MenuType<WindTurbineMenu>> WIND_TURBINE = MENUS.register("wind_turbine",
            () -> IForgeMenuType.create(WindTurbineMenu::new));
    public static final RegistryObject<MenuType<FabricatorMenu>> FABRICATOR = MENUS.register("fabricator",
            () -> IForgeMenuType.create(FabricatorMenu::new));
    public static final RegistryObject<MenuType<SurgeonMenu>> SURGEON = MENUS.register("surgeon",
            () -> IForgeMenuType.create(SurgeonMenu::new));

}
