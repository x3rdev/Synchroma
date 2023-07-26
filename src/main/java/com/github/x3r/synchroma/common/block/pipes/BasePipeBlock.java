package com.github.x3r.synchroma.common.block.pipes;

import com.github.x3r.synchroma.common.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.Nullable;

public abstract class BasePipeBlock extends BaseEntityBlock {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty HAS_BRAIN = BooleanProperty.create("has_brain");

    protected BasePipeBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(HAS_BRAIN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH);
        pBuilder.add(EAST);
        pBuilder.add(SOUTH);
        pBuilder.add(WEST);
        pBuilder.add(UP);
        pBuilder.add(DOWN);
        pBuilder.add(HAS_BRAIN);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {

        VoxelShape[] shapes = new VoxelShape[] {
                pState.getValue(NORTH) ? Block.box(5.5, 5.5, 0, 10.5, 10.5, 5.5) : Shapes.empty(),
                pState.getValue(EAST) ? Block.box(10.5, 5.5, 5.5, 16, 10.5, 10.5) : Shapes.empty(),
                pState.getValue(SOUTH) ? Block.box(5.5, 5.5, 10.5, 10.5, 10.5, 16) : Shapes.empty(),
                pState.getValue(WEST) ? Block.box(0, 5.5, 5.5, 5.5, 10.5, 10.5) : Shapes.empty(),
                pState.getValue(UP) ? Block.box(5.5, 10.5, 5.5, 10.5, 16, 10.5) : Shapes.empty(),
                pState.getValue(DOWN) ? Block.box(5.5, 0, 5.5, 10.5, 5.5, 10.5) : Shapes.empty()
        };
        return Shapes.or(Block.box(5.5, 5.5, 5.5, 10.5, 10.5, 10.5), shapes);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        return pState.setValue(getDirectionFromString(pDirection), isValidConnection(pLevel, pNeighborPos));
    }

    public BooleanProperty getDirectionFromString(Direction direction) {
        return switch (direction.toString()) {
            case "north" -> NORTH;
            case "east" -> EAST;
            case "south" -> SOUTH;
            case "west" -> WEST;
            case "up" -> UP;
            case "down" -> DOWN;
            default -> null;
        };
    }

    public boolean isValidConnection(LevelAccessor pLevel, BlockPos pos) {
        return (pLevel.getBlockEntity(pos) != null &&
                pLevel.getBlockEntity(pos).getCapability(ForgeCapabilities.ENERGY).isPresent()) ||
                pLevel.getBlockState(pos).is(getPipeTag());
    }

    public abstract TagKey<Block> getPipeTag();
}
