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

public class BasicSolarPanelScreen extends SynchromaScreen<BasicSolarPanelMenu> {
    private static final ResourceLocation CONTAINER_LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/basic_solar_panel.png");

    public BasicSolarPanelScreen(BasicSolarPanelMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.getMenu().getBlockEntity().getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> {
            this.addRenderableWidget(new SynchromaWidgets.EnergyWidget(leftPos+5, topPos+21,
                    () -> iEnergyStorage));
        });
    }

    @Override
    protected float statusBarRatio() {
        int time = (int) (getMenu().getBlockEntity().getLevel().getDayTime() % 24000L);
        return 1-(Math.abs(6000 - Math.max(0, 12000 - time)) / 6000F);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        graphics.pose().pushPose();
        graphics.blit(CONTAINER_LOCATION, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        graphics.pose().popPose();
    }
}
