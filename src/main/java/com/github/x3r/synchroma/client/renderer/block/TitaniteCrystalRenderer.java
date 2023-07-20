package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.titanite.TitaniteBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class TitaniteCrystalRenderer extends GeoBlockRenderer<TitaniteBlockEntity> {
    public TitaniteCrystalRenderer() {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(Synchroma.MOD_ID, "titanite_crystal")));
    }
}
