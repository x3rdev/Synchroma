package com.github.x3r.synchroma.client.screen;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.menu.BasicPumpMenu;
import com.github.x3r.synchroma.common.block.SynchromaFluidStorage;
import com.github.x3r.synchroma.common.block.solar_panel.BasicSolarPanelBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class BasicPumpScreen extends AbstractContainerScreen<BasicPumpMenu> {

    private static final ResourceLocation CONTAINER_LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/basic_pump.png");


    public BasicPumpScreen(BasicPumpMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.addRenderableWidget(new SynchromaWidgets.InformationWidget(i + 158, j + 6, getMenu().getBlockEntity().getType().toString()));
        this.getMenu().getBlockEntity().getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> {
            this.addRenderableWidget(new SynchromaWidgets.EnergyWidget(i+5, j+21,
                    () -> iEnergyStorage));
        });

        this.getMenu().getBlockEntity().getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(iFluidHandler -> {
            this.addRenderableWidget(new SynchromaWidgets.FluidStackWidget(i+44, j+54, 88, 16,
                    () -> ((SynchromaFluidStorage) iFluidHandler).getTank(0)));
        });

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
    }
}
