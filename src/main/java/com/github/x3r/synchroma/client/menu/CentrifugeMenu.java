package com.github.x3r.synchroma.client.menu;

import com.github.x3r.synchroma.common.block.centrifuge.CentrifugeBlockEntity;
import com.github.x3r.synchroma.common.block.solar_panel.ZenithSolarPanelBlockEntity;
import com.github.x3r.synchroma.common.registry.MenuTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CentrifugeMenu extends SyncedMenu<CentrifugeBlockEntity> {

    private final Container container;
    public CentrifugeMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buf) {
        this(pContainerId, inventory, (CentrifugeBlockEntity) SyncedMenu.getBufferBlockEntity(inventory.player.level(), buf));
    }
    public CentrifugeMenu(int pContainerId, Inventory inventory, CentrifugeBlockEntity blockEntity) {
        super(MenuTypeRegistry.CENTRIFUGE.get(), pContainerId, inventory, blockEntity);
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
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }
}