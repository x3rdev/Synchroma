package com.github.x3r.synchroma.client.screen;

import com.github.x3r.synchroma.client.menu.SyncedMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public abstract class SynchromaScreen<T extends SyncedMenu> extends AbstractContainerScreen<T> {

    protected SynchromaScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new SynchromaWidgets.InformationWidget(leftPos + 158, topPos + 6, getMenu().getBlockEntity().getType().toString()));
        this.addRenderableWidget(new SynchromaWidgets.StatusBarWidget(leftPos + 104, topPos + 5, this::statusBarRatio));
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752);
    }

    protected abstract float statusBarRatio();
}

