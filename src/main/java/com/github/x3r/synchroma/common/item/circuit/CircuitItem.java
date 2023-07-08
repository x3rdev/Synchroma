package com.github.x3r.synchroma.common.item.circuit;

import com.github.x3r.synchroma.common.block.controller.ControllerBlockEntity;
import com.github.x3r.synchroma.common.block.multiblock.MultiBlockPart;
import com.github.x3r.synchroma.common.block.multiblock.MultiBlockPartEntity;
import com.github.x3r.synchroma.common.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

import java.util.function.Predicate;

public abstract class CircuitItem extends Item {

    public CircuitItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.is(BlockRegistry.CONTROLLER.get())) {
            BlockEntity blockentity = level.getBlockEntity(blockpos);
            if (blockentity instanceof ControllerBlockEntity frameEntity) {
                frameEntity.setItem(0, pContext.getItemInHand().copyWithCount(1));
                pContext.getItemInHand().shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    public abstract BlockPattern getPattern();

    public static Predicate<BlockInWorld> mbBlockMatches(Block block) {
        return blockInWorld -> {
            if(blockInWorld.getState().is(block)) return true;
            if(blockInWorld.getEntity() instanceof MultiBlockPartEntity partEntity) {
                return partEntity.getOriginalState().is(block);
            }
            return false;
        };
    }
}
