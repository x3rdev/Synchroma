package com.github.x3r.synchroma.common.menu;

import com.github.x3r.synchroma.common.block.solar_panel.ZenithSolarPanelBlockEntity;
import com.github.x3r.synchroma.common.registry.MenuTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ZenithSolarPanelMenu extends SyncedMenu<ZenithSolarPanelBlockEntity> {
    private final Container container;

    public ZenithSolarPanelMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buf) {
        this(pContainerId, inventory, (ZenithSolarPanelBlockEntity) SyncedMenu.getBufferBlockEntity(inventory.player.level(), buf));
    }
    public ZenithSolarPanelMenu(int pContainerId, Inventory inventory, ZenithSolarPanelBlockEntity blockEntity) {
        super(MenuTypeRegistry.ZENITH_SOLAR_PANEL.get(), pContainerId, inventory, blockEntity);
        this.container = blockEntity;
        checkContainerSize(container, 3);
        this.addSlot(new Slot(container, 0, 44, 34){
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return super.mayPlace(pStack);
            }
        });
        this.addSlot(new Slot(container, 1, 80, 34){
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return super.mayPlace(pStack);
            }
        });
        this.addSlot(new Slot(container, 2, 116, 34){
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return super.mayPlace(pStack);
            }
        });

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
