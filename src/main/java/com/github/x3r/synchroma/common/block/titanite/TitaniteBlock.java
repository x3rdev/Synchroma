package com.github.x3r.synchroma.common.block.titanite;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TitaniteBlock extends Block implements EntityBlock {

    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 12, 14);

    public TitaniteBlock(Properties pProperties) {
        super(pProperties.noOcclusion().isViewBlocking((pState, pLevel, pPos) -> false));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TitaniteBlockEntity(pPos, pState);
    }
}
