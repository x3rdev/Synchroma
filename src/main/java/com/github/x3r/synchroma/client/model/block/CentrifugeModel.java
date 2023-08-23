package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.centrifuge.CentrifugeBlockEntity;
import com.github.x3r.synchroma.common.block.solar_panel.AdvancedSolarPanelBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class CentrifugeModel extends DefaultedBlockGeoModel<CentrifugeBlockEntity> {

    public CentrifugeModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "centrifuge"));
    }

    @Override
    public void setCustomAnimations(CentrifugeBlockEntity animatable, long instanceId, AnimationState<CentrifugeBlockEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
