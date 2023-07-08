package com.github.x3r.synchroma.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class FrameBlock extends Block {

    public FrameBlock(Properties pProperties) {
        super(pProperties.noOcclusion().isViewBlocking((pState, pLevel, pPos) -> false));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
//        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
//        if (blockentity instanceof FrameBlockEntity frameEntity) {
//            if(frameEntity.getFirstItem().isEmpty()) {
//                return InteractionResult.PASS;
//            }
//            frameEntity.popOutCircuit();
//            return InteractionResult.sidedSuccess(pLevel.isClientSide);
//        }

        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
//        if (!pState.is(pNewState.getBlock())) {
//            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
//            if (blockentity instanceof FrameBlockEntity frameEntity) {
//                frameEntity.popOutCircuit();
//            }
//            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
//        }
    }

}
