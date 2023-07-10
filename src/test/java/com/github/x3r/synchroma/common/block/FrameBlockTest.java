package com.github.x3r.synchroma.common.block;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.junit.Test;
public class FrameBlockTest {

    @Test
    public void use() {
        InteractionHand hand = InteractionHand.OFF_HAND;
        assert InteractionHand.values()[hand.ordinal() == 0 ? 1 : 0].equals(InteractionHand.MAIN_HAND);
    }
}