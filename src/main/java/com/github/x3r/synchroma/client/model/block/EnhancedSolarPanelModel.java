package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlock;
import com.github.x3r.synchroma.common.block.solar_panel.EnhancedSolarPanelBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class EnhancedSolarPanelModel extends DefaultedBlockGeoModel<EnhancedSolarPanelBlockEntity> {

    public EnhancedSolarPanelModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "enhanced_solar_panel"));
    }

    // Line 29 is not useless, fixes lerp issue when multiple models are in world
    @SuppressWarnings("java:S1854")
    @Override
    public void setCustomAnimations(EnhancedSolarPanelBlockEntity animatable, long instanceId, AnimationState<EnhancedSolarPanelBlockEntity> animationState) {
        if(!animationState.getController().isPlayingTriggeredAnimation()) {
            CoreGeoBone geoBone = getAnimationProcessor().getBone("rotating_part");
            double endAngle = Math.PI / 7;
            float f = (float) (Mth.clamp(((animatable.getLevel().getSunAngle(0) + (3 * Math.PI / 2)) % (Math.PI)), endAngle, Math.PI - endAngle) - (Math.PI / 2));
            boolean flip = animatable.getBlockState().getValue(ControllerBlock.FACING).equals(Direction.WEST);
            geoBone.setRotX((float) Mth.lerp(0.05, geoBone.getRotX(), flip ? -f : f));
            if(flip) f *= -1;
        }
    }
}
