package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.centrifuge.CentrifugeBlockEntity;
import com.github.x3r.synchroma.common.block.fabricator.FabricatorBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class FabricatorModel extends DefaultedBlockGeoModel<FabricatorBlockEntity> {

    public FabricatorModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "fabricator"));
    }
}
