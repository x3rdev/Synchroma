package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.circuit_printer.CircuitPrinterBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class CircuitPrinterModel extends DefaultedBlockGeoModel<CircuitPrinterBlockEntity> {
    public CircuitPrinterModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "circuit_printer"));
    }

}
