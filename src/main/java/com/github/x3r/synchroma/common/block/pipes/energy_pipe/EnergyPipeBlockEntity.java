package com.github.x3r.synchroma.common.block.pipes.energy_pipe;

import com.github.x3r.synchroma.common.block.pipes.BasePipeBlock;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class EnergyPipeBlockEntity extends BlockEntity {
    public EnergyPipeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.ENERGY_PIPE.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, EnergyPipeBlockEntity pBlockEntity) {
        Optional<Boolean> b = pState.getOptionalValue(BasePipeBlock.HAS_BRAIN);
        if(b.isEmpty() || !b.get()) return;

    }
}
