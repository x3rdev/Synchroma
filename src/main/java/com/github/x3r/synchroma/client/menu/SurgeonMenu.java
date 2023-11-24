package com.github.x3r.synchroma.client.menu;

import com.github.x3r.synchroma.common.block.surgeon.SurgeonBlockEntity;
import com.github.x3r.synchroma.common.registry.MenuTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SurgeonMenu extends SyncedMenu<SurgeonBlockEntity> {
    private final Container container;
    public SurgeonMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buf) {
        this(pContainerId, inventory, (SurgeonBlockEntity) SyncedMenu.getBufferBlockEntity(inventory.player.level(), buf));
    }
    public SurgeonMenu(int pContainerId, Inventory inventory, SurgeonBlockEntity blockEntity) {
        super(MenuTypeRegistry.SURGEON.get(), pContainerId, inventory, blockEntity);
        this.container = blockEntity;
        checkContainerSize(container, 6);
        this.addSlot(new Slot(container, 0, 111, 44));
        this.addSlot(new Slot(container, 1, 136, 44));
        this.addSlot(new Slot(container, 2, 111, 69));
        this.addSlot(new Slot(container, 3, 136, 69));
        this.addSlot(new Slot(container, 4, 111, 94));
        this.addSlot(new Slot(container, 5, 136, 94));

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 11 + l * 18, 125));
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
        pPlayer.stopRiding();
        super.removed(pPlayer);
    }
}
