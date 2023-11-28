package com.github.x3r.synchroma.client.screen;

import com.github.x3r.synchroma.Synchroma;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.IFluidTank;

import java.util.function.Supplier;

import static net.minecraft.network.chat.Component.*;

public final class SynchromaWidgets {

    private static final ResourceLocation SYNCHROMA_WIDGETS_LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/synchroma_widgets.png");

    public static class InformationWidget extends ImageButton {
        private String machineId;
        public InformationWidget(int pX, int pY, String machineId) {
            super(pX, pY, 9, 9, 0, 0, 9, SYNCHROMA_WIDGETS_LOCATION, pButton -> {});
            this.machineId = machineId;
            this.setTooltip(Tooltip.create(literal("gui.machine_information")));
        }

        @Override
        public void onPress() {
            //Open Patchouli page corresponding to machineId
        }
    }
    public static class StatusBarWidget extends AbstractWidget {
        private final Supplier<Float> getBarRatio;

        public StatusBarWidget(int pX, int pY, Supplier<Float> getBarRatio) {
            super(pX, pY, 37, 3, empty());
            this.getBarRatio = getBarRatio;
        }

        @Override
        protected void renderWidget(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
            graphics.pose().pushPose();
            float ratio = getBarRatio.get();
            int i = (int) Math.floor(37*ratio);
            this.setTooltip(Tooltip.create(literal((int)(ratio*100) + "%")));
            graphics.blit(SYNCHROMA_WIDGETS_LOCATION, getX(), getY(), 9, 3, 37, 3);
            graphics.blit(SYNCHROMA_WIDGETS_LOCATION, getX(), getY(), 9, 0, i, 3);
            graphics.pose().popPose();
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

        }
    }

    public static class EnergyWidget extends AbstractWidget {

        private final Supplier<IEnergyStorage> getEnergyStorage;
        public EnergyWidget(int pX, int pY, Supplier<IEnergyStorage> getEnergyStorage) {
            super(pX, pY, 11, 48, empty());
            this.getEnergyStorage = getEnergyStorage;
        }

        @Override
        protected void renderWidget(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
            IEnergyStorage storage = getEnergyStorage.get();
            graphics.pose().pushPose();
            graphics.blit(SYNCHROMA_WIDGETS_LOCATION, getX(), getY(), 9, 6, 11, 48);
            int energyStored = storage.getEnergyStored();
            int maxEnergyStored = storage.getMaxEnergyStored();
            this.setTooltip(Tooltip.create(literal(energyStored + "/" + maxEnergyStored + "RF")));
            int v = (int) Math.floor(48*((float)energyStored/maxEnergyStored));
            graphics.blit(SYNCHROMA_WIDGETS_LOCATION, getX(), getY()+48-v, 20, 6+48-v, 11, v);
            graphics.pose().popPose();
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

        }
    }
    public static class FluidStackWidget extends AbstractWidget {

        private final Supplier<IFluidTank> getFluidTank;
        public FluidStackWidget(int pX, int pY, Supplier<IFluidTank> getFluidTank) {
            super(pX, pY, 18, 51, empty());
            this.getFluidTank = getFluidTank;
        }

        @Override
        protected void renderWidget(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
            Minecraft minecraft = Minecraft.getInstance();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            IFluidTank tank = getFluidTank.get();
            int stored = tank.getFluidAmount();
            float capacity = tank.getCapacity();
            float filledVolume = stored / capacity;
            int renderableHeight = (int)(filledVolume * 32);
            setTooltip(Tooltip.create(literal(stored + "/" + (int) capacity + "mB")));
            if(!tank.getFluid().isEmpty()) {
                IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(tank.getFluid().getFluid());
                ResourceLocation resource = extensions.getStillTexture();
                if (resource != null) {
                    AbstractTexture texture = minecraft.getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS);
                    if(texture instanceof TextureAtlas atlas) {
                        TextureAtlasSprite sprite = atlas.getSprite(resource);

                        int color = extensions.getTintColor();
                        RenderSystem.setShaderColor(
                                FastColor.ARGB32.red(color) / 255.0F,
                                FastColor.ARGB32.green(color) / 255.0F,
                                FastColor.ARGB32.blue(color) / 255.0F,
                                FastColor.ARGB32.alpha(color) / 255.0F);
                        RenderSystem.enableBlend();

                        int atlasWidth = (int)(sprite.contents().width() / (sprite.getU1() - sprite.getU0()));
                        int atlasHeight = (int)(sprite.contents().height() / (sprite.getV1() - sprite.getV0()));

                        graphics.pose().pushPose();
                        graphics.pose().translate(0, height-16, 0);
                        for (int i = 0; i < Math.ceil(renderableHeight / 16f); i++) {
                            int drawingHeight = Math.min(16, renderableHeight - 16*i);
                            int notDrawingHeight = 16 - drawingHeight;
                            graphics.blit(TextureAtlas.LOCATION_BLOCKS, getX()+1, getY() + notDrawingHeight-10, 0, sprite.getU0()*atlasWidth, sprite.getV0()*atlasHeight + notDrawingHeight, width, drawingHeight, atlasWidth, atlasHeight);
                            graphics.pose().translate(0,-16, 0);
                        }
                        RenderSystem.setShaderColor(1, 1, 1, 1);
                        graphics.pose().popPose();
                    }
                }
            }
            graphics.pose().pushPose();
            graphics.blit(SYNCHROMA_WIDGETS_LOCATION, getX(), getY(), 31, 6, 18, 49);
            graphics.pose().popPose();
            RenderSystem.disableDepthTest();
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

        }

    }

    public static class BodyPartIndicatorWidget extends Button {

        private static final ResourceLocation LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/surgeon.png");
        public BodyPartIndicatorWidget(int pX, int pY, OnPress pOnPress) {
            super(pX, pY, 8, 8, Component.empty(), pOnPress, DEFAULT_NARRATION);
        }

        @Override
        protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            pGuiGraphics.blit(LOCATION, getX(), getY(), 8 * (int) (Minecraft.getInstance().level.getGameTime()/2%5), 185, 8, 8);
        }
    }

    public static class SurgeonPreviousPage extends Button {

        private static final ResourceLocation LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/surgeon.png");
        protected SurgeonPreviousPage(int pX, int pY, OnPress pOnPress) {
            super(pX, pY, 8, 13, Component.empty(), pOnPress, DEFAULT_NARRATION);
        }

        @Override
        protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            pGuiGraphics.blit(LOCATION, getX(), getY(), 178, 0, 8, 13);
        }
    }

    public static class SurgeonNextPage extends Button {

        private static final ResourceLocation LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/surgeon.png");
        protected SurgeonNextPage(int pX, int pY, OnPress pOnPress) {
            super(pX, pY, 8, 13, Component.empty(), pOnPress, DEFAULT_NARRATION);
        }

        @Override
        protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            pGuiGraphics.blit(LOCATION, getX(), getY(), 187, 0, 8, 13);
        }
    }

    public static class SurgeonOpenVisualMenu extends Button {

        private static final ResourceLocation LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/surgeon.png");
        protected SurgeonOpenVisualMenu(int pX, int pY, OnPress pOnPress) {
            super(pX, pY, 7, 7, Component.empty(), pOnPress, DEFAULT_NARRATION);
        }

        @Override
        protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            pGuiGraphics.blit(LOCATION, getX(), getY(), 197, 0, 7, 7);
        }
    }

    public static class SurgeonTextField extends EditBox {

        public SurgeonTextField(Font pFont, int pX, int pY, int pWidth, int pHeight) {
            super(pFont, pX, pY, pWidth, pHeight, Component.empty());
            //0x2f373f
            this.setTextColor(-1);
            this.setTextColorUneditable(-1);
            this.setBordered(false);
            this.setMaxLength(5);
            this.setEditable(true);
            this.setValue("0.0");
        }

        @Override
        public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            setTooltip(Tooltip.create(Component.literal(this.getValue())));
            super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }

        @Override
        public boolean canConsumeInput() {
            return this.isVisible() && super.canConsumeInput();
        }
    }
}
