package com.github.x3r.synchroma.client.screen;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.menu.BasicPumpMenu;
import com.github.x3r.synchroma.common.block.SynchromaFluidStorage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class BasicPumpScreen extends SynchromaScreen<BasicPumpMenu> {

    private static final ResourceLocation CONTAINER_LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/basic_pump.png");


    public BasicPumpScreen(BasicPumpMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.getMenu().getBlockEntity().getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> {
            this.addRenderableWidget(new SynchromaWidgets.EnergyWidget(leftPos+5, topPos+21,
                    () -> iEnergyStorage));
        });
        this.getMenu().getBlockEntity().getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(iFluidHandler -> {
            this.addRenderableWidget(new SynchromaWidgets.FluidStackWidget(leftPos+155, topPos+24,
                    () -> ((SynchromaFluidStorage) iFluidHandler).getTank(0)));
        });

    }

    @Override
    protected float statusBarRatio() {
        final float[] f = {0};
        getMenu().getBlockEntity().getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> {
            getMenu().getBlockEntity().getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(iFluidHandler -> {
                f[0] = iEnergyStorage.getEnergyStored() > 10 && iFluidHandler.getFluidInTank(0).getAmount() < iFluidHandler.getTankCapacity(0)
                ? 1 : 0;
            });
        });
        return f[0];
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        graphics.pose().pushPose();
        graphics.blit(CONTAINER_LOCATION, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        graphics.pose().popPose();
    }
}
