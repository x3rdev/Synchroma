package com.github.x3r.synchroma.common.block.multiblock;

import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MimicBlockEntity extends PartBlockEntity {
    private BlockState originalState;

    public MimicBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.MIMIC.get(), pPos, pBlockState);
    }

    @Override
    public void load(CompoundTag tag) {
        HolderGetter<Block> holdergetter = (this.level != null ? this.level.holderLookup(Registries.BLOCK) : BuiltInRegistries.BLOCK.asLookup());
        if(tag.contains(ORIGINAL_STATE)) originalState = NbtUtils.readBlockState(holdergetter, tag.getCompound(ORIGINAL_STATE));
        super.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        if(!getOriginalState().isAir()) tag.put(ORIGINAL_STATE, NbtUtils.writeBlockState(getOriginalState()));
        super.saveAdditional(tag);
    }

    public void setOriginalState(BlockState previousState) {
        this.originalState = previousState;
    }

    public BlockState getOriginalState() {
        if(originalState == null || originalState.getBlock() instanceof MimicBlock) {
            return Blocks.AIR.defaultBlockState();
        }
        return originalState;
    }

    @Override
    public void disassemble() {
        super.disassemble();
        level.setBlockAndUpdate(getBlockPos(), getOriginalState());
    }
}
