package com.github.x3r.synchroma.client.screen;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.menu.BasicSolarPanelMenu;
import com.github.x3r.synchroma.common.block.solar_panel.BasicSolarPanelBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.ArrayList;
import java.util.List;

public class BasicSolarPanelScreen extends AbstractContainerScreen<BasicSolarPanelMenu> {
    private static final ResourceLocation CONTAINER_LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/basic_solar_panel.png");

    public BasicSolarPanelScreen(BasicSolarPanelMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.addRenderableWidget(new SynchromaWidgets.InformationWidget(i + 158, j + 6, getMenu().getBlockEntity().getType().toString()));
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(CONTAINER_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        getMenu().getBlockEntity().getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> {
            int v = (int) (48 * (1-((float) iEnergyStorage.getEnergyStored() / BasicSolarPanelBlockEntity.MAX_ENERGY)));
            graphics.blit(CONTAINER_LOCATION, i+5, j+21, 176, 12, 11, v);
        });
        int time = (int) (getMenu().getBlockEntity().getLevel().getDayTime() % 24000L);
        int f = Math.round(37*(Math.abs(6000 - Math.max(0, 12000 - time)) / 6000F));
        graphics.blit(CONTAINER_LOCATION, i+104+37-f, j+5, 176+37-f, 9, f, 3);
    }
}
