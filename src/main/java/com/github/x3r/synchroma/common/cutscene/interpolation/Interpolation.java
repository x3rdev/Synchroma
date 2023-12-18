package com.github.x3r.synchroma.common.cutscene.interpolation;

import net.minecraft.util.Mth;

public interface Interpolation {

    Interpolation LINEAR = Mth::lerp;
    float apply(float delta, float start, float end);
}
