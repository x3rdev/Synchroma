package com.github.x3r.synchroma.client.screen;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.menu.SurgeonMenu;
import com.github.x3r.synchroma.common.item.cyberware.ImplantLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SurgeonScreen extends SynchromaScreen<SurgeonMenu> {

    private static final ResourceLocation LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/surgeon.png");

    private ImplantLocation page = ImplantLocation.HEAD;

    private boolean editVisuals = false;
    private int editVisualsSlot = -1;

    private final SynchromaWidgets.SurgeonTextField[] editBoxes = new SynchromaWidgets.SurgeonTextField[7];

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
        if(this.editVisuals && !getMenu().getItems().get(this.editVisualsSlot).isEmpty()) {
            renderEditVisualsMenu(graphics, leftPos - 60, topPos + 38);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

//        Skeleton skeleton = new Skeleton(EntityType.SKELETON, Minecraft.getInstance().level);
//        skeleton.setYHeadRot(0);
//        InventoryScreen.renderEntityInInventory(pGuiGraphics, leftPos, topPos, 32, new Quaternionf(1, 0, 0, 0), null, minecraft.player);
//        InventoryScreen.renderEntityInInventory(pGuiGraphics, leftPos, topPos, 32, new Quaternionf(1, 0, 0, 0), null, skeleton);
        pGuiGraphics.drawString(font, Component.literal(page.getName()), leftPos + 110, topPos + 25, 0xFFFFFF, false);

        for(SynchromaWidgets.SurgeonTextField box : editBoxes) {
            if(box != null) {
                box.setVisible(this.editVisuals);
            }
        }
    }


    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }

    private void renderEditVisualsMenu(GuiGraphics pGuiGraphics, int x, int y) {
        pGuiGraphics.blit(LOCATION, x, y, 191, 118, 61, 85);
        pGuiGraphics.drawString(font, Component.literal("Position"), leftPos - 56, topPos + 42, 4210752);
        pGuiGraphics.drawString(font, Component.literal("Scale"), leftPos - 56, topPos + 63, 4210752);
        pGuiGraphics.drawString(font, Component.literal("Rotation"), leftPos - 56, topPos + 84, 4210752);
    }

    @Override
    protected void init() {
        this.imageWidth = 177;
        this.imageHeight = 147;
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        this.addRenderableWidget(new SynchromaWidgets.InformationWidget(leftPos + 158, topPos + 6, getMenu().getBlockEntity().getType().toString()));
        this.addRenderableWidget(new SynchromaWidgets.SurgeonPreviousPage(leftPos + 96, topPos + 22, pButton -> page = page.previous()));
        this.addRenderableWidget(new SynchromaWidgets.SurgeonNextPage(leftPos + 158, topPos + 22, pButton -> page = page.next()));
        this.addRenderableWidget(new SynchromaWidgets.SurgeonOpenVisualMenu(leftPos + 126, topPos + 38, pButton -> {
            editVisuals = !editVisuals;
            editVisualsSlot = 0;
        }){
            @Override
            protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                if(!getMenu().getItems().get(0).isEmpty()) {
                    super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
            }
            @Override
            protected boolean isValidClickButton(int pButton) {
                return !getMenu().getItems().get(0).isEmpty();
            }
        });
        this.addRenderableWidget(new SynchromaWidgets.SurgeonOpenVisualMenu(leftPos + 151, topPos + 38, pButton -> {
            editVisuals = !editVisuals;
            editVisualsSlot = 1;
        }){
            @Override
            protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                if(!getMenu().getItems().get(1).isEmpty()) {
                    super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
            }
            @Override
            protected boolean isValidClickButton(int pButton) {
                return !getMenu().getItems().get(1).isEmpty();
            }
        });
        this.addRenderableWidget(new SynchromaWidgets.SurgeonOpenVisualMenu(leftPos + 126, topPos + 63, pButton -> {
            editVisuals = !editVisuals;
            editVisualsSlot = 2;
        }){
            @Override
            protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                if(!getMenu().getItems().get(2).isEmpty()) {
                    super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
            }
            @Override
            protected boolean isValidClickButton(int pButton) {
                return !getMenu().getItems().get(2).isEmpty();
            }
        });
        this.addRenderableWidget(new SynchromaWidgets.SurgeonOpenVisualMenu(leftPos + 151, topPos + 63, pButton -> {
            editVisuals = !editVisuals;
            editVisualsSlot = 3;
        }){
            @Override
            protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                if(!getMenu().getItems().get(3).isEmpty()) {
                    super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
            }
            @Override
            protected boolean isValidClickButton(int pButton) {
                return !getMenu().getItems().get(3).isEmpty();
            }
        });
        this.addRenderableWidget(new SynchromaWidgets.SurgeonOpenVisualMenu(leftPos + 126, topPos + 88, pButton -> {
            editVisuals = !editVisuals;
            editVisualsSlot = 4;
        }){
            @Override
            protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                if(!getMenu().getItems().get(4).isEmpty()) {
                    super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
            }
            @Override
            protected boolean isValidClickButton(int pButton) {
                return !getMenu().getItems().get(4).isEmpty();
            }
        });
        this.addRenderableWidget(new SynchromaWidgets.SurgeonOpenVisualMenu(leftPos + 151, topPos + 88, pButton -> {
            editVisuals = !editVisuals;
            editVisualsSlot = 5;
        }){
            @Override
            protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                if(!getMenu().getItems().get(5).isEmpty()) {
                    super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
            }
            @Override
            protected boolean isValidClickButton(int pButton) {
                return !getMenu().getItems().get(5).isEmpty();
            }
        });
        editBoxes[0] = new SynchromaWidgets.SurgeonTextField(this.font, leftPos-54, topPos+53, 15, 10){
            @Override
            public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                pGuiGraphics.pose().pushPose();
                if(SurgeonScreen.this.editVisuals && !getMenu().getItems().get(SurgeonScreen.this.editVisualsSlot).isEmpty()) {
                    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
                pGuiGraphics.pose().popPose();
            }
        };
        this.addRenderableWidget(editBoxes[0]);
        editBoxes[1] = new SynchromaWidgets.SurgeonTextField(this.font, leftPos-36, topPos+53, 15, 10){
            @Override
            public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                pGuiGraphics.pose().pushPose();
                if(SurgeonScreen.this.editVisuals && !getMenu().getItems().get(SurgeonScreen.this.editVisualsSlot).isEmpty()) {
                    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
                pGuiGraphics.pose().popPose();
            }
        };
        this.addRenderableWidget(editBoxes[1]);
        editBoxes[2] = new SynchromaWidgets.SurgeonTextField(this.font, leftPos-18, topPos+53, 15, 10){
            @Override
            public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                pGuiGraphics.pose().pushPose();
                if(SurgeonScreen.this.editVisuals && !getMenu().getItems().get(SurgeonScreen.this.editVisualsSlot).isEmpty()) {
                    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
                pGuiGraphics.pose().popPose();
            }
        };
        this.addRenderableWidget(editBoxes[2]);
        editBoxes[3] = new SynchromaWidgets.SurgeonTextField(this.font, leftPos-54, topPos+74, 15, 10){
            @Override
            public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                pGuiGraphics.pose().pushPose();
                if(SurgeonScreen.this.editVisuals && !getMenu().getItems().get(SurgeonScreen.this.editVisualsSlot).isEmpty()) {
                    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
                pGuiGraphics.pose().popPose();
            }
        };
        this.addRenderableWidget(editBoxes[3]);
        editBoxes[4] = new SynchromaWidgets.SurgeonTextField(this.font, leftPos-54, topPos+95, 15, 10){
            @Override
            public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                pGuiGraphics.pose().pushPose();
                if(SurgeonScreen.this.editVisuals && !getMenu().getItems().get(SurgeonScreen.this.editVisualsSlot).isEmpty()) {
                    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
                pGuiGraphics.pose().popPose();
            }
        };
        this.addRenderableWidget(editBoxes[4]);
        editBoxes[5] = new SynchromaWidgets.SurgeonTextField(this.font, leftPos-36, topPos+95, 15, 10){
            @Override
            public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                pGuiGraphics.pose().pushPose();
                if(SurgeonScreen.this.editVisuals && !getMenu().getItems().get(SurgeonScreen.this.editVisualsSlot).isEmpty()) {
                    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
                pGuiGraphics.pose().popPose();
            }
        };
        this.addRenderableWidget(editBoxes[5]);
        editBoxes[6] = new SynchromaWidgets.SurgeonTextField(this.font, leftPos-18, topPos+95, 15, 10){
            @Override
            public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                pGuiGraphics.pose().pushPose();
                if(SurgeonScreen.this.editVisuals && !getMenu().getItems().get(SurgeonScreen.this.editVisualsSlot).isEmpty()) {
                    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
                pGuiGraphics.pose().popPose();
            }
        };
        this.addRenderableWidget(editBoxes[6]);
//        this.addRenderableWidget(new SynchromaWidgets.BodyPartIndicatorWidget(leftPos + 60, topPos + 50, pButton -> {}));
//        this.addRenderableWidget(new SynchromaWidgets.BodyPartIndi atorWidget(leftPos + 90, topPos + 55, pButton -> {}));
//        this.addRenderableWidget(new SynchromaWidgets.BodyPartIndicatorWidget(leftPos + 85, topPos + 20, pButton -> {}));
//        this.addRenderableWidget(new SynchromaWidgets.BodyPartIndicatorWidget(leftPos + 68, topPos + 32, pButton -> {}));
//        this.addRenderableWidget(new SynchromaWidgets.BodyPartIndicatorWidget(leftPos + 75, topPos + 50, pButton -> {}));
//        this.addRenderableWidget(new SynchromaWidgets.BodyPartIndicatorWidget(leftPos + 55, topPos + 70, pButton -> {}));
//        this.addRenderableWidget(new SynchromaWidgets.BodyPartIndicatorWidget(leftPos + 75, topPos + 70, pButton -> {}));
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

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == 256) {
            this.minecraft.player.closeContainer();
        }
        boolean b = true;
        for(SynchromaWidgets.SurgeonTextField box : editBoxes) {
            if(box != null) {
                if (box.keyPressed(pKeyCode, pScanCode, pModifiers) || box.canConsumeInput()) {
                    b = false;
                }
            }
        }
        return !b || super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
}
