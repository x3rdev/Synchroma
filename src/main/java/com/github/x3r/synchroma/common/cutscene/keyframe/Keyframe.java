package com.github.x3r.synchroma.common.cutscene.keyframe;

import com.github.x3r.synchroma.common.cutscene.interpolation.Interpolation;
import org.jetbrains.annotations.NotNull;

public class Keyframe implements Comparable<Keyframe> {

    private final int timeStamp;
    private final float value;
    private final Interpolation interpolation;

    public Keyframe(int timeStamp, float value, Interpolation interpolation) {
        this.timeStamp = timeStamp;
        this.value = value;
        this.interpolation = interpolation;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public float getValue() {
        return value;
    }

    public Interpolation getInterpolation() {
        return interpolation;
    }

    @Override
    public int compareTo(@NotNull Keyframe o) {
        return this.timeStamp - o.timeStamp;
    }
}
