package com.github.x3r.synchroma.common.block.pipes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

public abstract class BasePipeBlockEntity extends BlockEntity {

    protected BasePipeBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Nullable
    public Pair<Direction, BlockPos> findSource() {
        Pair<Direction, BlockPos>[] positions = getAdjacentSources(getBlockPos());
        return positions.length > 0 ? positions[0] : null;
    }

    @Nullable
    public Pair<Direction, BlockPos> findEndpoint() {
        Set<BlockPos> traversed = new HashSet<>();
        Set<BlockPos> working = new HashSet<>();
        working.add(getBlockPos());
        while (!working.isEmpty()) {
            Set<BlockPos> toTraverse = new HashSet<>();
            for (BlockPos blockPos : working) {
                if(!traversed.contains(blockPos)) {
                    Pair<Direction, BlockPos>[] endpoints = getAdjacentEndpoints(blockPos);
                    if (endpoints.length > 0) return endpoints[0];
                    traversed.add(blockPos);
                    toTraverse.addAll(List.of(getAdjacentPipes(blockPos)));
                }
            }
            working.removeAll(traversed);
            working.addAll(toTraverse);

        }
        return null;
    }

    @SuppressWarnings("unchecked")
    protected Pair<Direction, BlockPos>[] getAdjacentSources(BlockPos pos) {
        return Arrays.stream(Direction.values())
                .filter(direction -> isValidSource().test(pos, direction))
                .map(direction -> new Pair<>(direction, pos.relative(direction))).toArray(Pair[]::new);
    }

    @SuppressWarnings("unchecked")
    protected Pair<Direction, BlockPos>[] getAdjacentEndpoints(BlockPos pos) {
        return Arrays.stream(Direction.values())
                .filter(direction -> isValidEndpoint().test(pos, direction))
                .map(direction -> new Pair<>(direction, pos.relative(direction))).toArray(Pair[]::new);
    }

    protected BlockPos[] getAdjacentPipes(BlockPos pos) {
        BlockPos[] positions = Arrays.stream(Direction.values())
                .filter(direction -> isValidPipe().test(pos, direction))
                .map(pos::relative).toArray(BlockPos[]::new);
        return positions;
    }

    protected abstract BiPredicate<BlockPos, Direction> isValidSource();
    protected abstract BiPredicate<BlockPos, Direction> isValidEndpoint();
    protected abstract BiPredicate<BlockPos, Direction> isValidPipe();
}
