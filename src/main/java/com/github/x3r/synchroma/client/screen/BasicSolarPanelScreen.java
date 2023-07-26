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

    //TODO use minecraft widget system
    public static final List<ScreenArea> SCREEN_AREAS = new ArrayList<>();
    private static final ResourceLocation CONTAINER_LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/basic_solar_panel.png");

    private boolean infoScreen = false;

    public BasicSolarPanelScreen(BasicSolarPanelMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        SCREEN_AREAS.add(new ScreenArea(11, 17, 9, 50, (graphics, pPartialTick, pMouseX, pMouseY) -> {
            menu.getBlockEntity().getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> {
                graphics.renderTooltip(this.font, Component.literal(String.valueOf(iEnergyStorage.getEnergyStored())), pMouseX, pMouseY);
            });
        }, (pMouseX, pMouseY) -> {}));
        SCREEN_AREAS.add(new ScreenArea(160, 70, 9, 9, (graphics, pPartialTick, pMouseX, pMouseY) -> {},
                (pMouseX, pMouseY) -> infoScreen = !infoScreen));
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        SCREEN_AREAS.forEach(screenArea -> {
            if(pMouseX >= i+screenArea.x() && pMouseX < i+screenArea.x()+screenArea.width() && pMouseY >= j+screenArea.y() && pMouseY < j+screenArea.y()+screenArea.height()) {
                screenArea.renderConsumer().accept(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
            }
        });
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(CONTAINER_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        getMenu().getBlockEntity().getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> {
            int v = (int) (50 * ((float) iEnergyStorage.getEnergyStored() / BasicSolarPanelBlockEntity.MAX_ENERGY));
            graphics.blit(CONTAINER_LOCATION, i+11, j+17+(50-v), 176, 9, 9, v);
        });
        if(infoScreen) {
            graphics.blit(CONTAINER_LOCATION, i+160, j+70, 176, 0, 9, 9);
        }
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        SCREEN_AREAS.forEach(screenArea -> {
            if(pMouseX >= i+screenArea.x() && pMouseX < i+screenArea.x()+screenArea.width() && pMouseY >= j+screenArea.y() && pMouseY < j+screenArea.y()+screenArea.height()) {
                screenArea.clickConsumer().accept(pMouseX, pMouseY);
            }
        });
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
}
