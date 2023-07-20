package com.github.x3r.synchroma.client.menu;

import com.github.x3r.synchroma.common.registry.MenuTypeRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BasicSolarPanelMenu extends AbstractContainerMenu {
    private final Container container;
    public BasicSolarPanelMenu(int pContainerId, Inventory inventory) {
        this(pContainerId, inventory, new SimpleContainer(3));
    }

    public BasicSolarPanelMenu(int containerId, Inventory inventory, Container container) {
        super(MenuTypeRegistry.BASIC_SOLAR_PANEL_MENU.get(), containerId);
        checkContainerSize(container, 3);
        this.container = container;
        container.startOpen(inventory.player);

        for(int i = 0; i < 3; ++i) {
            this.addSlot(new Slot(container, i, 44 + i*36, 34));
        }

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(inventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 8 + l * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return this.container.stillValid(pPlayer);
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.container.stopOpen(pPlayer);
    }
}
