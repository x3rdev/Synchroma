package com.github.x3r.synchroma.common.block.pipes.energy_pipe;

import com.github.x3r.synchroma.common.block.controller.ControllerBlockEntity;
import com.github.x3r.synchroma.common.block.pipes.BasePipeBlock;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.common.tag.SynchromaBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.Nullable;

public class EnergyPipeBlock extends BasePipeBlock {
    public EnergyPipeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Capability<?> getCapability() {
        return ForgeCapabilities.ENERGY;
    }

    @Override
    public TagKey<Block> getPipeTag() {
        return SynchromaBlockTags.ENERGY_PIPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EnergyPipeBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, BlockEntityRegistry.ENERGY_PIPE.get(), EnergyPipeBlockEntity::tick);
    }
}
