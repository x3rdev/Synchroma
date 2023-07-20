package com.github.x3r.synchroma.common.block.titanite;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TitaniteBlock extends Block implements EntityBlock {
    public TitaniteBlock(Properties pProperties) {
        super(pProperties.noOcclusion().isViewBlocking((pState, pLevel, pPos) -> false));
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
