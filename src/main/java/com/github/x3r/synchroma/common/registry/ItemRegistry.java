package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.item.CapacitorItem;
import com.github.x3r.synchroma.common.item.bullets.TestBullet;
import com.github.x3r.synchroma.common.item.guns.TestGun;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Synchroma.MOD_ID);

    public static final Item.Properties DEFAULT_PROPERTIES = new Item.Properties().tab(ItemRegistry.SynchromaItemTab.instance);

    public static final RegistryObject<Item> SUGARCANE_CAPACITOR = ITEMS.register("sugarcane_capacitor",
            () -> new CapacitorItem(DEFAULT_PROPERTIES, 10));
    public static final RegistryObject<Item> TEST_GUN = ITEMS.register("test_gun",
            () -> new TestGun(DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TEST_BULLET = ITEMS.register("test_bullet",
            () -> new TestBullet(DEFAULT_PROPERTIES));
    public static class SynchromaItemTab extends CreativeModeTab {
        public static final SynchromaItemTab instance = new SynchromaItemTab(CreativeModeTab.TABS.length, Synchroma.MOD_ID);
        private SynchromaItemTab(int index, String tabName) {
            super(index, tabName);
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.NAME_TAG);
        }
    }
}
