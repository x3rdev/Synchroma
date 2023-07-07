package com.github.x3r.synchroma.common.block.multiblock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public abstract class MBBlock extends Block {
    public static final BooleanProperty ASSEMBLED = BooleanProperty.create("assembled");
    protected MBBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ASSEMBLED, false));
    }
    public abstract RenderShape getDefaultRenderShape();

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return isAssembled(pState) ? RenderShape.INVISIBLE : getDefaultRenderShape();
    }
    protected boolean isAssembled(BlockState state) {
        return state.getValue(ASSEMBLED);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(ASSEMBLED);
    }
}
