package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.client.model.block.BasicCircuitPrinterModel;
import com.github.x3r.synchroma.common.block.basic_circuit_printer.BasicCircuitPrinterBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BasicCircuitPrinterRenderer extends GeoBlockRenderer<BasicCircuitPrinterBlockEntity> {
    public BasicCircuitPrinterRenderer() {
        super(new BasicCircuitPrinterModel());
    }
}
