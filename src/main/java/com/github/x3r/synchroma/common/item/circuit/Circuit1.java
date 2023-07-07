package com.github.x3r.synchroma.common.item.circuit;

import com.github.x3r.synchroma.common.registry.BlockRegistry;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

public class Circuit1 extends CircuitItem {

    private static BlockPattern pattern;
    public Circuit1(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public BlockPattern getPattern() {
        if(pattern == null) {
            pattern = BlockPatternBuilder.start()
                    .where('f', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockRegistry.FRAME.get())))
                    .where('c', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockRegistry.CONTROLLER.get())))
                    .where('*', blockInWorld -> blockInWorld.getState().isAir())
                    .aisle("c***", "ffff")
                    .aisle("****", "*fff")
                    .build();
        }
        return pattern;
    }
}
