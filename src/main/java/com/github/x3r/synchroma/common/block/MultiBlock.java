package com.github.x3r.synchroma.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockPattern;

public interface MultiBlock {
    BlockPattern getValidationPattern();
    BlockPattern getFinalPattern();
    default BlockPattern.BlockPatternMatch patternValid(Level level, BlockPos pos) {
        return getValidationPattern().find(level, pos);
    }
}
