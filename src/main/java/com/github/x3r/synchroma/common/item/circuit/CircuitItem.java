package com.github.x3r.synchroma.common.item.circuit;

import net.minecraft.world.item.Item;

public abstract class CircuitItem extends Item {

    protected CircuitItem(Properties pProperties) {
        super(pProperties);
    }

//    @Override
//    public InteractionResult useOn(UseOnContext pContext) {
//        Level level = pContext.getLevel();
//        BlockPos blockpos = pContext.getClickedPos();
//        BlockState blockstate = level.getBlockState(blockpos);
//        if (blockstate.is(BlockRegistry.CONTROLLER.get())) {
//            BlockEntity blockentity = level.getBlockEntity(blockpos);
//            if (blockentity instanceof ControllerBlockEntity frameEntity) {
//                frameEntity.setItem(0, pContext.getItemInHand().copyWithCount(1));
//                pContext.getItemInHand().shrink(1);
//            }
//            return InteractionResult.sidedSuccess(level.isClientSide);
//        } else {
//            return InteractionResult.PASS;
//        }
//    }
//    public abstract BlockPattern getPattern();
//
//    public abstract Vec3i getPatternOffset();
//
//    public abstract MultiBlockRenderer getRenderer();
//
//    public abstract void useMB(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit);
//
//    public abstract void tickMB(Level pLevel, BlockPos pPos, BlockState pState, ControllerBlockEntity pBlockEntity);
//
}
