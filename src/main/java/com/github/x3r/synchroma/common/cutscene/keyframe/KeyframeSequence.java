package com.github.x3r.synchroma.common.cutscene.keyframe;

import com.github.x3r.synchroma.common.cutscene.interpolation.Interpolation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class KeyframeSequence {

    private final Keyframe[] keyframes;

    public KeyframeSequence(Keyframe... keyframes) {
        this.keyframes = keyframes;
    }

    public float getValue(int tick) {
        if(tick == 0) {
            return initialValue();
        }
        Keyframe previous = getPreviousKeyframe(tick);
        Keyframe next = getNextKeyframe(tick);
        float f0 = (float) tick - previous.getTimeStamp();
        float f1 = (float) next.getTimeStamp() - previous.getTimeStamp();
        float delta = f1 > 0 ? f0/f1 : 1;
        return next.getInterpolation().apply(delta, previous.getValue(), next.getValue());
    }

    private Keyframe getPreviousKeyframe(int tick) {
        List<Keyframe> keyframeList = Arrays.stream(keyframes).filter(keyframe -> keyframe.getTimeStamp() < tick).sorted().toList();
        if(keyframeList.isEmpty()) {
            return new Keyframe(0, initialValue(), Interpolation.LINEAR);
        }
        return keyframeList.get(keyframeList.size()-1);
    }

    private Keyframe getNextKeyframe(int tick) {
        List<Keyframe> keyframeList = Arrays.stream(keyframes).filter(keyframe -> keyframe.getTimeStamp() >= tick).sorted().toList();
        if(keyframeList.isEmpty()) {
            return getPreviousKeyframe(tick);
        }
        return keyframeList.get(0);
    }

    private float initialValue() {
        Optional<Keyframe> frame0 = Arrays.stream(keyframes).filter(keyframe -> keyframe.getTimeStamp() == 0).findFirst();
        return frame0.map(Keyframe::getValue).orElse(0F);
    }
}
