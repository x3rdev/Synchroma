package com.github.x3r.synchroma.client.screen;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.solar_panel.BasicSolarPanelBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.IFluidTank;

import java.util.function.Supplier;

public final class SynchromaWidgets {

    private static final ResourceLocation SYNCHROMA_WIDGETS_LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/synchroma_widgets.png");

    public static class InformationWidget extends ImageButton {
        private String machineId;
        public InformationWidget(int pX, int pY, String machineId) {
            super(pX, pY, 9, 9, 0, 0, 9, SYNCHROMA_WIDGETS_LOCATION, pButton -> {});
            this.machineId = machineId;
            this.setTooltip(Tooltip.create(Component.literal("Information")));
        }

        @Override
        public void onPress() {
            //Open Patchouli page corresponding to machineId
        }
    }

    public static class EnergyWidget extends AbstractWidget {

        private final Supplier<IEnergyStorage> getEnergyStorage;
        public EnergyWidget(int pX, int pY, Supplier<IEnergyStorage> getEnergyStorage) {
            super(pX, pY, 11, 48, Component.empty());
            this.getEnergyStorage = getEnergyStorage;
        }

        @Override
        protected void renderWidget(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
            IEnergyStorage storage = getEnergyStorage.get();
            graphics.pose().pushPose();
            graphics.blit(SYNCHROMA_WIDGETS_LOCATION, getX(), getY(), 11, 18, 11, 48);
            if(storage != null) {
                int energyStored = storage.getEnergyStored();
                int maxEnergyStored = storage.getMaxEnergyStored();
                this.setTooltip(Tooltip.create(Component.literal(energyStored + "/" + maxEnergyStored + "RF")));
                int v = (int) (48 * ((Math.ceil((float)energyStored/maxEnergyStored))));
                graphics.blit(SYNCHROMA_WIDGETS_LOCATION, getX(), getY(), 0, 18, 11, v);
            }
            graphics.pose().popPose();
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

        }
    }
    public static class FluidStackWidget extends AbstractWidget {

        private final Supplier<IFluidTank> getFluidTank;
        public FluidStackWidget(int pX, int pY, int pWidth, int pHeight, Supplier<IFluidTank> getFluidTank) {
            super(pX, pY, pWidth, pHeight, Component.empty());
            this.getFluidTank = getFluidTank;
        }

        @Override
        protected void renderWidget(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
            Minecraft minecraft = Minecraft.getInstance();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            IFluidTank tank = getFluidTank.get();
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

                        int stored = tank.getFluidAmount();
                        float capacity = tank.getCapacity();
                        float filledVolume = stored / capacity;
                        int renderableHeight = (int)(filledVolume * getHeight());

                        setTooltip(Tooltip.create(Component.literal(stored + "/" + (int) capacity + "mB")));

                        int atlasWidth = (int)(sprite.contents().width() / (sprite.getU1() - sprite.getU0()));
                        int atlasHeight = (int)(sprite.contents().height() / (sprite.getV1() - sprite.getV0()));

                        double widthScale = Math.ceil(width/(float)sprite.contents().width());
                        graphics.pose().pushPose();
                        graphics.pose().translate(0, getHeight() - renderableHeight, 0);
                        for(int i = 0; i < widthScale; i++) {
                            int offset = (i*sprite.contents().width());
                            int border = Math.min(0, getWidth() - ((i+1)*sprite.contents().width()));
                            graphics.blit(TextureAtlas.LOCATION_BLOCKS, getX() + offset, getY(), 0, sprite.getU0() * atlasWidth, sprite.getV0() * atlasHeight, sprite.contents().width() + border, renderableHeight, atlasWidth, atlasHeight);
                        }
                        RenderSystem.setShaderColor(1, 1, 1, 1);

                        graphics.pose().popPose();
                    }
                }
            }
            RenderSystem.disableDepthTest();
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

        }

    }
}
