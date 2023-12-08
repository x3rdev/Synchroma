package com.github.x3r.synchroma.common.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

/*
    A little stolen from enderio :P
 */

public abstract class SyncedMenu<T extends BlockEntity> extends AbstractContainerMenu {
    protected final Inventory inventory;
    @Nullable
    protected final T blockEntity;
    protected SyncedMenu(@Nullable MenuType<?> pMenuType, int pContainerId, Inventory inventory, @Nullable T blockEntity) {
        super(pMenuType, pContainerId);
        this.inventory = inventory;
        this.blockEntity = blockEntity;
    }

    public T getBlockEntity() {
        return blockEntity;
    }

    @Nullable
    public static BlockEntity getBufferBlockEntity(Level level, FriendlyByteBuf buf) {
        return level.getBlockEntity(buf.readBlockPos());
    }
}
