package com.github.x3r.synchroma.client.screen;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.menu.CircuitPrinterMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class CircuitPrinterScreen extends SynchromaScreen<CircuitPrinterMenu> {
    private static final ResourceLocation CONTAINER_LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/circuit_printer.png");

    public CircuitPrinterScreen(CircuitPrinterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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
        return 1;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        graphics.pose().pushPose();
        graphics.blit(CONTAINER_LOCATION, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        int processTime = getMenu().getBlockEntity().getProcessTime();
        int recipeProcessTime = getMenu().getBlockEntity().getRecipeProcessTime();
        if(recipeProcessTime != 0) {
            graphics.blit(CONTAINER_LOCATION, leftPos+113, topPos+35, 176, 0, (int) (24F*processTime/recipeProcessTime), 17);
        }
        graphics.pose().popPose();
    }
}
