package com.github.x3r.synchroma.client.screen;

import net.minecraft.client.gui.GuiGraphics;

public record ScreenArea(int x, int y, int width, int height, RenderConsumer renderConsumer, ClickConsumer clickConsumer) {
    @FunctionalInterface
    public interface RenderConsumer {
        void accept(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY);
    }

    @FunctionalInterface
    public interface ClickConsumer {
        void accept(double pMouseX, double pMouseY);
    }
}
