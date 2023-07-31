package com.github.x3r.synchroma.common.block.solar_panel;

import org.junit.Test;

import static org.junit.Assert.*;

public class BasicSolarPanelBlockEntityTest {

    @Test
    public void serverTick() {
        final int daytime = 12000;

        float sunlightStrength = 1-(Math.abs(6000-Math.max(0, 12000-daytime))/6000F);

        System.out.println(sunlightStrength);
        assertEquals(0D, sunlightStrength, 0);
    }
}