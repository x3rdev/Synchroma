package com.github.x3r.synchroma.common.block.solar_panel;

import com.github.x3r.synchroma.common.block.multiblock.ControllerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EnhancedSolarPanelBlock extends ControllerBlock {

    public EnhancedSolarPanelBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EnhancedSolarPanelBlockEntity(pPos, pState);
    }
}
