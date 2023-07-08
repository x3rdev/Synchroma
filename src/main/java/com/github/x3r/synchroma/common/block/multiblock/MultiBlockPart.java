package com.github.x3r.synchroma.common.block.multiblock;

import com.github.x3r.synchroma.common.block.controller.ControllerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public class MultiBlockPart extends Block implements EntityBlock {
    public MultiBlockPart(Properties pProperties) {
        super(pProperties);

    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MultiBlockPartEntity(pPos, pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            if(pNewState.isAir()) {
                BlockEntity blockentity = pLevel.getBlockEntity(pPos);
                if (blockentity instanceof MultiBlockPartEntity part) {
                    pNewState = part.getOriginalState();
                    pLevel.setBlockAndUpdate(pPos, pNewState);
                    part.getControllerPos().ifPresent(pos -> {
                        if (pLevel.getBlockEntity(pos) instanceof ControllerBlockEntity controllerBlockEntity) {
                            controllerBlockEntity.disassemble();
                        }
                    });
                }
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }

    }
}
