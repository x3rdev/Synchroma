package com.github.x3r.synchroma.common.block.ripperdoc_chair;

import com.github.x3r.synchroma.common.block.BlockUtils;
import com.github.x3r.synchroma.common.block.VoxelShapeBlock;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class RipperdocChairBlock extends Block implements EntityBlock, VoxelShapeBlock {

    protected final VoxelShape VOXEL_NORTH = BlockUtils.rotate(defaultShape(), Direction.NORTH);

    protected final VoxelShape VOXEL_EAST = BlockUtils.rotate(defaultShape(), Direction.EAST);

    protected final VoxelShape VOXEL_SOUTH = BlockUtils.rotate(defaultShape(), Direction.SOUTH);

    protected final VoxelShape VOXEL_WEST = BlockUtils.rotate(defaultShape(), Direction.WEST);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public RipperdocChairBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        switch (direction) {
            case NORTH -> {
                return VOXEL_NORTH;
            }
            case EAST -> {
                return VOXEL_EAST;
            }
            case SOUTH -> {
                return VOXEL_SOUTH;
            }
            case WEST -> {
                return VOXEL_WEST;
            }
            default -> {
                return Shapes.empty();
            }
        }
    }

    @Override
    public VoxelShape defaultShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0, 0.875, 0.0625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.0625, 0.1875, 0.75, 0.4375, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.4375, 0.0625, 0.875, 0.5625, 0.875), BooleanOp.OR);
        return shape;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return BlockEntityRegistry.RIPPERDOC_CHAIR_TILE.get().create(pPos, pState);
    }
}
