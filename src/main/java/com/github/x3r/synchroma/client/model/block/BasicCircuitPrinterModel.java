package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.basic_circuit_printer.BasicCircuitPrinterBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class BasicCircuitPrinterModel extends DefaultedBlockGeoModel<BasicCircuitPrinterBlockEntity> {
    public BasicCircuitPrinterModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "basic_circuit_printer"));
    }

}
