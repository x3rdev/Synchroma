package com.github.x3r.synchroma.client.menu;

import com.github.x3r.synchroma.common.block.solar_panel.BasicSolarPanelBlockEntity;
import com.github.x3r.synchroma.common.block.solar_panel.EnhancedSolarPanelBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EnhancedSolarPanelMenu extends SyncedMenu<EnhancedSolarPanelBlockEntity> {

    protected EnhancedSolarPanelMenu(@Nullable MenuType<?> pMenuType, int pContainerId, Inventory inventory, @Nullable EnhancedSolarPanelBlockEntity blockEntity) {
        super(pMenuType, pContainerId, inventory, blockEntity);
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
