package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.item.*;
import com.github.x3r.synchroma.common.item.bullets.TestBullet;
import com.github.x3r.synchroma.common.item.circuit.Circuit1;
import com.github.x3r.synchroma.common.item.guns.HunterRifle;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Synchroma.MOD_ID);

    public static final Item.Properties DEFAULT_PROPERTIES = new Item.Properties();

    public static final RegistryObject<Item> SUGARCANE_CAPACITOR = ITEMS.register("sugarcane_capacitor",
            () -> new CapacitorItem(DEFAULT_PROPERTIES, 10));
    public static final RegistryObject<Item> RESEARCH_JOURNAL = ITEMS.register("research_journal",
            () -> new ResearchJournalItem(DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> HUNTER_RIFLE = ITEMS.register("hunter_rifle",
            () -> new HunterRifle(DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TEST_BULLET = ITEMS.register("test_bullet",
            () -> new TestBullet(DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> CIRCUIT_1 = ITEMS.register("circuit_1",
            () -> new Circuit1(DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> CROWBAR = ITEMS.register("crowbar",
            () -> new CrowbarItem(DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> SCREW_DRIVER = ITEMS.register("screw_driver",
            () -> new ScrewDriverItem(DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> WELDING_GUN = ITEMS.register("welding_gun",
            () -> new WeldingGunItem(DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> WIRE_CUTTERS = ITEMS.register("wire_cutters",
            () -> new WireCuttersItem(DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench",
            () -> new WrenchItem(DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TITANIUM_PLATES = ITEMS.register("titanium_plates",
            () -> new Item(DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> WIRES = ITEMS.register("wires",
            () -> new Item(DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> RATE_UPGRADE = ITEMS.register("rate_upgrade",
            () -> new MachineUpgradeItem(DEFAULT_PROPERTIES, 1, Component.literal("test")));
    public static final RegistryObject<Item> REVERSE_UPGRADE = ITEMS.register("reverse_upgrade",
            () -> new MachineUpgradeItem(DEFAULT_PROPERTIES, 2, Component.literal("test")));
    public static final RegistryObject<Item> EFFICIENCY_UPGRADE = ITEMS.register("efficiency_upgrade",
            () -> new MachineUpgradeItem(DEFAULT_PROPERTIES, 3, Component.literal("test")));

    public static class SynchromaItemTab {
        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Synchroma.MOD_ID);

        public static final RegistryObject<CreativeModeTab> SYNCHROMA_ITEM_TAB = CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
                .icon(Items.NAME_TAG::getDefaultInstance)
                .title(Component.literal("itemGroup.synchroma"))
                .displayItems((displayParameters, output) -> {
                    ItemRegistry.ITEMS.getEntries().forEach(itemRegistryObject -> output.accept(itemRegistryObject.get()));
                    BlockItemRegistry.BLOCK_ITEMS.getEntries().forEach(itemRegistryObject -> output.accept(itemRegistryObject.get()));
                })
                .build());

        public static final RegistryObject<CreativeModeTab> GUN_MODIFICATION_ITEM_TAB = CREATIVE_MODE_TABS.register("attachments", () -> CreativeModeTab.builder()
                .icon(Items.BAKED_POTATO::getDefaultInstance)
                .title(Component.literal("itemGroup.synchroma"))
                .displayItems((displayParameters, output) -> {
                    GunModificationRegistry.GUN_MODIFICATIONS.getEntries().forEach(itemRegistryObject -> output.accept(itemRegistryObject.get()));
                })
                .build());
    }
}
