package com.github.x3r.synchroma.client.renderer.block.solar_panel;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.hex_solar_plate.HexSolarPlateBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class HexSolarPlateRenderer extends GeoBlockRenderer<HexSolarPlateBlockEntity> {

    public HexSolarPlateRenderer() {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(Synchroma.MOD_ID, "hex_solar_plate")));
    }
}
