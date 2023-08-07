package com.github.x3r.synchroma.common.block.energy_buffer;

import com.github.x3r.synchroma.common.block.multiblock.PartBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EnergyOutputBufferBlock extends PartBlock {
    public EnergyOutputBufferBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EnergyOutputBufferBlockEntity(pPos, pState);
    }
}
