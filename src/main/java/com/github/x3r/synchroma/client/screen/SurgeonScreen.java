package com.github.x3r.synchroma.client.screen;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.menu.SurgeonMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Inventory;
import org.joml.Quaternionf;

public class SurgeonScreen extends SynchromaScreen<SurgeonMenu> {

    private static final ResourceLocation LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/surgeon.png");

    public SurgeonScreen(SurgeonMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected float statusBarRatio() {
        return 0;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        graphics.blit(LOCATION, leftPos, topPos, 0, 0, getXSize(), getYSize());
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        Skeleton skeleton = new Skeleton(EntityType.SKELETON, Minecraft.getInstance().level);

        skeleton.setYHeadRot(0);
        InventoryScreen.renderEntityInInventory(pGuiGraphics, leftPos, topPos, 32, new Quaternionf(1, 0, 0, 0), null, minecraft.player);
        InventoryScreen.renderEntityInInventory(pGuiGraphics, leftPos, topPos, 32, new Quaternionf(1, 0, 0, 0), null, skeleton);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
//        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
        super.renderBackground(pGuiGraphics);
    }

    @Override
    protected void init() {
        this.imageWidth = 177;
        this.imageHeight = 147;
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        this.addRenderableWidget(new SynchromaWidgets.InformationWidget(leftPos + 158, topPos + 6, getMenu().getBlockEntity().getType().toString()));
        this.addRenderableWidget(new SynchromaWidgets.BodyPartIndicatorWidget(leftPos + 60, topPos + 50, pButton -> {}));
        this.addRenderableWidget(new SynchromaWidgets.BodyPartIndicatorWidget(leftPos + 90, topPos + 55, pButton -> {}));
        this.addRenderableWidget(new SynchromaWidgets.BodyPartIndicatorWidget(leftPos + 85, topPos + 20, pButton -> {}));
        this.addRenderableWidget(new SynchromaWidgets.BodyPartIndicatorWidget(leftPos + 68, topPos + 32, pButton -> {}));
        this.addRenderableWidget(new SynchromaWidgets.BodyPartIndicatorWidget(leftPos + 75, topPos + 50, pButton -> {}));
        this.addRenderableWidget(new SynchromaWidgets.BodyPartIndicatorWidget(leftPos + 55, topPos + 70, pButton -> {}));
        this.addRenderableWidget(new SynchromaWidgets.BodyPartIndicatorWidget(leftPos + 75, topPos + 70, pButton -> {}));
        Minecraft.getInstance().gameRenderer.setRenderHand(false);
        Minecraft.getInstance().gameRenderer.getMainCamera().detached = true;
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().gameRenderer.setRenderHand(true);
        Minecraft.getInstance().gameRenderer.getMainCamera().detached = false;
        Minecraft.getInstance().gameRenderer.getMainCamera().reset();
        super.onClose();
    }


}
