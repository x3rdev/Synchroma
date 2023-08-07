package com.github.x3r.synchroma.common.block.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.network.PacketSendListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

public abstract class PartBlock extends BaseEntityBlock {
    public static final BooleanProperty ASSEMBLED = BooleanProperty.create("assembled");

    protected PartBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(PartBlock.ASSEMBLED, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return pState.getValue(PartBlock.ASSEMBLED) ? RenderShape.INVISIBLE : RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pState.getValue(ASSEMBLED)) {
            if (!pLevel.isClientSide) {
                PartBlockEntity partBlockEntity = (PartBlockEntity) pLevel.getBlockEntity(pPos);
                BlockPos controllerPos = partBlockEntity.getControllerPos().get();
                ControllerBlockEntity controller = (ControllerBlockEntity) pLevel.getBlockEntity(controllerPos);
                controller.getBlockState().getBlock().use(pLevel.getBlockState(controllerPos), pLevel, controllerPos, pPlayer, pHand, pHit);
            }
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            if (pLevel.getBlockEntity(pPos) instanceof PartBlockEntity part) {
                part.disassemble();
                pLevel.setBlockAndUpdate(pPos, pNewState);
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(PartBlock.ASSEMBLED);
    }
}
