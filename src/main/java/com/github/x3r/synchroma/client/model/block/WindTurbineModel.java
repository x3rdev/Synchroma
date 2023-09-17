package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.wind_turbine.WindTurbineBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class WindTurbineModel extends DefaultedBlockGeoModel<WindTurbineBlockEntity> {

    public WindTurbineModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "wind_turbine"));
    }

    @Override
    public void setCustomAnimations(WindTurbineBlockEntity animatable, long instanceId, AnimationState<WindTurbineBlockEntity> animationState) {
        CoreGeoBone geoBone = getAnimationProcessor().getBone("rotating_part");
        if(!animationState.getController().isPlayingTriggeredAnimation()) {
            geoBone.setRotY(animatable.boneSnapshots.getOrDefault(geoBone.getName(), geoBone.getInitialSnapshot()).getRotY() + (0.02F));
            animatable.boneSnapshots.put(geoBone.getName(), geoBone.saveSnapshot());
        } else {
            animatable.boneSnapshots.put(geoBone.getName(), geoBone.getInitialSnapshot());
        }
    }
}
