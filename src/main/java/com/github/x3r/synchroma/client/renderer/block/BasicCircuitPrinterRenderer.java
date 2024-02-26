package com.github.x3r.synchroma.client.renderer.block;

import com.github.x3r.synchroma.client.model.block.CircuitPrinterModel;
import com.github.x3r.synchroma.common.block.circuit_printer.CircuitPrinterBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BasicCircuitPrinterRenderer extends GeoBlockRenderer<CircuitPrinterBlockEntity> {
    public BasicCircuitPrinterRenderer() {
        super(new CircuitPrinterModel());
    }
}
