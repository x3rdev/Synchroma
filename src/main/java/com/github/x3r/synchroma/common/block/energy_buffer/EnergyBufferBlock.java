package com.github.x3r.synchroma.common.block.energy_buffer;

import com.github.x3r.synchroma.common.block.multiblock.PartBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EnergyBufferBlock extends PartBlock {
    public EnergyBufferBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EnergyBufferBlockEntity(pPos, pState);
    }
}
