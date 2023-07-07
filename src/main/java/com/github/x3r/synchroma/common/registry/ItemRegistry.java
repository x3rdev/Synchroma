package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.item.CapacitorItem;
import com.github.x3r.synchroma.common.item.bullets.TestBullet;
import com.github.x3r.synchroma.common.item.circuit.Circuit1;
import com.github.x3r.synchroma.common.item.guns.TestGun;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Synchroma.MOD_ID);

    public static final DeferredRegister<Item> GUN_MODIFICATIONS = DeferredRegister.create(ForgeRegistries.ITEMS, Synchroma.MOD_ID);

    public static final Item.Properties DEFAULT_PROPERTIES = new Item.Properties();

    public static final RegistryObject<Item> SUGARCANE_CAPACITOR = ITEMS.register("sugarcane_capacitor",
            () -> new CapacitorItem(DEFAULT_PROPERTIES, 10));
    public static final RegistryObject<Item> TEST_GUN = ITEMS.register("test_gun",
            () -> new TestGun(DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TEST_BULLET = ITEMS.register("test_bullet",
            () -> new TestBullet(DEFAULT_PROPERTIES));

    public static final RegistryObject<Item> CIRCUIT_1 = ITEMS.register("circuit_1",
            () -> new Circuit1(DEFAULT_PROPERTIES));

    public static class SynchromaItemTab {

        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Synchroma.MOD_ID);

        public static final RegistryObject<CreativeModeTab> SYNCHROMA_ITEM_TAB = CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
                .icon(Items.NAME_TAG::getDefaultInstance)
//                .title(Component.translatable("itemGroup.synchroma"))
                .displayItems((displayParameters, output) -> {
                    ItemRegistry.ITEMS.getEntries().forEach(itemRegistryObject -> {
                        output.accept(itemRegistryObject.get());
                    });
                    BlockItemRegistry.BLOCK_ITEMS.getEntries().forEach(itemRegistryObject -> {
                        output.accept(itemRegistryObject.get());
                    });
                })
                .build());
    }
}
