package com.github.x3r.synchroma.client.screen;

import com.github.x3r.synchroma.Synchroma;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;

public final class SynchromaWidgets {

    public static class InformationWidget extends ImageButton {
        private static final ResourceLocation INFO_WIDGET_LOCATION = new ResourceLocation(Synchroma.MOD_ID, "textures/gui/container/information_widget.png");

        private String machineId;
        public InformationWidget(int pX, int pY, String machineId) {
            super(pX, pY, 9, 9, 0, 0, 9, INFO_WIDGET_LOCATION, pButton -> {});
            this.machineId = machineId;
        }

        @Override
        public void onPress() {
            //Open Patchouli page corresponding to machineId
        }
    }
}
