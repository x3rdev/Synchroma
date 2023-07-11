package com.github.x3r.synchroma.common.block.weapon_workbench;

import com.github.x3r.synchroma.common.registry.BlockRegistry;
import com.github.x3r.synchroma.common.registry.MenuTypeRegistry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class WeaponWorkbenchMenu extends AbstractContainerMenu {
    private Inventory inventory;
    private ContainerLevelAccess access;
    public WeaponWorkbenchMenu(int pContainerId, Inventory inventory) {
        this(pContainerId, inventory, new ItemStackHandler(5), ContainerLevelAccess.NULL);
    }
    public WeaponWorkbenchMenu(int pContainerId, Inventory inventory, IItemHandler dataInventory, ContainerLevelAccess access) {
        super(MenuTypeRegistry.WEAPON_WORKBENCH_MENU.get(), pContainerId);
        this.inventory = inventory;
        this.access = access;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return AbstractContainerMenu.stillValid(this.access, pPlayer, BlockRegistry.CONTROLLER.get());
    }
}
