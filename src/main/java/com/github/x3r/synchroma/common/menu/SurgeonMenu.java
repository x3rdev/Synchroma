package com.github.x3r.synchroma.common.menu;

import com.github.x3r.synchroma.common.block.surgeon.SurgeonBlockEntity;
import com.github.x3r.synchroma.common.capability.CyberwareCapability;
import com.github.x3r.synchroma.common.cutscene.ServerCutsceneManager;
import com.github.x3r.synchroma.common.cutscene.SurgeonCutscene;
import com.github.x3r.synchroma.common.item.cyberware.CyberwareItem;
import com.github.x3r.synchroma.common.item.cyberware.ImplantLocation;
import com.github.x3r.synchroma.common.registry.MenuTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class SurgeonMenu extends SyncedMenu<SurgeonBlockEntity> {
    private final Container container;

    private ImplantLocation implantLocation = ImplantLocation.HEAD;

    public SurgeonMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buf) {
        this(pContainerId, inventory, (SurgeonBlockEntity) SyncedMenu.getBufferBlockEntity(inventory.player.level(), buf));
    }

    @Override
    public void sendAllDataToRemote() {
        super.sendAllDataToRemote();
    }

    public SurgeonMenu(int pContainerId, Inventory inventory, SurgeonBlockEntity blockEntity) {
        super(MenuTypeRegistry.SURGEON.get(), pContainerId, inventory, blockEntity);
        this.container = blockEntity;
        checkContainerSize(container, 6);
        this.addSlot(new SurgeonSlot(container, 0, 111, 44));
        this.addSlot(new SurgeonSlot(container, 1, 136, 44));
        this.addSlot(new SurgeonSlot(container, 2, 111, 69));
        this.addSlot(new SurgeonSlot(container, 3, 136, 69));
        this.addSlot(new SurgeonSlot(container, 4, 111, 94));
        this.addSlot(new SurgeonSlot(container, 5, 136, 94));

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 11 + l * 18, 125));
        }

        loadImplants(inventory.player);
    }

    private class SurgeonSlot extends Slot {

        public SurgeonSlot(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            if(pStack.getItem() instanceof CyberwareItem cyberwareItem) {
                return Arrays.asList(cyberwareItem.getImplantLocation()).contains(ImplantLocation.values()[SurgeonMenu.this.implantLocation.ordinal()]);
            }
            return false;
        }

        @Override
        public void onTake(Player pPlayer, ItemStack pStack) {
            super.onTake(pPlayer, pStack);
            if(!pPlayer.level().isClientSide()) {
                inventory.player.getCapability(CyberwareCapability.INSTANCE).ifPresent(cap -> {
                    cap.removeImplant(inventory.player, implantLocation, getContainerSlot());
                    if (pStack.getItem() instanceof CyberwareItem) {
                        CyberwareItem.setInstalled(pStack, false);
                    }
                });
            }
        }
    }

    @Override
    public boolean clickMenuButton(Player pPlayer, int pId) {
        if(!pPlayer.level().isClientSide()) {
            if((pId == 0 || pId == 1)) {
                if (pId == 0) implantLocation = implantLocation.previous();
                if (pId == 1) implantLocation = implantLocation.next();
                moveItemsBackToInventory();
                loadImplants(pPlayer);
            }
            if(pId == 2) {
                installImplants(pPlayer);
            }
        }
        return super.clickMenuButton(pPlayer, pId);
    }

    private void moveItemsBackToInventory() {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = this.container.getItem(i);
            if(!stack.isEmpty() && !CyberwareItem.isInstalled(stack)) {
                if (!inventory.add(stack)) {
                    ItemEntity itemEntity = new ItemEntity(blockEntity.getLevel(),
                            blockEntity.getBlockPos().getX(),
                            blockEntity.getBlockPos().getY(),
                            blockEntity.getBlockPos().getZ(),
                            stack);
                    blockEntity.getLevel().addFreshEntity(itemEntity);
                }
                this.container.setItem(0, ItemStack.EMPTY);
            }
        }
    }

    private void loadImplants(Player player) {
        player.getCapability(CyberwareCapability.INSTANCE).ifPresent(cap -> {
            ItemStack[] items = cap.getImplants(implantLocation);
            for (int i = 0; i < items.length; i++) {
                this.container.setItem(i, items[i]);
            }
        });
    }

    private void installImplants(Player player) {
        player.getCapability(CyberwareCapability.INSTANCE).ifPresent(cap -> {
            for (int i = 0; i < container.getContainerSize(); i++) {
                cap.addImplant(player, container.getItem(i), implantLocation, i);
            }
        });
        getBlockEntity().triggerAnim("install", "install_cyberware");
        ServerCutsceneManager.getInstance().startCutscene((ServerPlayer) player, new SurgeonCutscene((ServerPlayer) player));
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
        if(!pPlayer.level().isClientSide()) {
            moveItemsBackToInventory();
            getBlockEntity().setPlayer(null);
            getBlockEntity().triggerAnim("controller", "deactivate");
        }
        super.removed(pPlayer);
    }
}
