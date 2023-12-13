package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.surgeon.SurgeonBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationProcessor;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class SurgeonModel extends DefaultedBlockGeoModel<SurgeonBlockEntity> {

    public SurgeonModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "surgeon"));
    }

    @Override
    public void setCustomAnimations(SurgeonBlockEntity animatable, long instanceId, AnimationState<SurgeonBlockEntity> animationState) {
        AnimationProcessor.QueuedAnimation currentAnimation = animationState.getController().getCurrentAnimation();
        animatable.installing = currentAnimation != null && currentAnimation.animation().name().equals("install_cyberware");
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
