package com.github.x3r.synchroma.common.menu;

import com.github.x3r.synchroma.common.block.circuit_printer.CircuitPrinterBlockEntity;
import com.github.x3r.synchroma.common.item.circuit.CircuitItem;
import com.github.x3r.synchroma.common.registry.ItemRegistry;
import com.github.x3r.synchroma.common.registry.MenuTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CircuitPrinterMenu extends SyncedMenu<CircuitPrinterBlockEntity> {

    private final Container container;
    public CircuitPrinterMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buf) {
        this(pContainerId, inventory, (CircuitPrinterBlockEntity) SyncedMenu.getBufferBlockEntity(inventory.player.level(), buf));
    }
    public CircuitPrinterMenu(int pContainerId, Inventory inventory, CircuitPrinterBlockEntity blockEntity) {
        super(MenuTypeRegistry.CIRCUIT_PRINTER.get(), pContainerId, inventory, blockEntity);
        this.container = blockEntity;
        checkContainerSize(container, 5);
        this.addSlot(new Slot(container, 0, 27, 35) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(ItemRegistry.EMPTY_CIRCUIT_BOARD.get());
            }
        });
        this.addSlot(new Slot(container, 1, 47, 25));
        this.addSlot(new Slot(container, 2, 67, 25));
        this.addSlot(new Slot(container, 3, 87, 25));
        this.addSlot(new Slot(container, 4, 47, 45));
        this.addSlot(new Slot(container, 5, 67, 45));
        this.addSlot(new Slot(container, 6, 87, 45));
        this.addSlot(new Slot(container, 7, 147, 35));

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
}