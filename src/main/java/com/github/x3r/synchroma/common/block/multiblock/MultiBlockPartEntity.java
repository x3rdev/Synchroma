package com.github.x3r.synchroma.common.block.multiblock;

import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.common.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MultiBlockPartEntity extends BlockEntity {

    public static final String CONTROLLER_POS = "ControllerPos";
    public static final String ORIGINAL_STATE = "OriginalState";
    private BlockPos controllerPos;

    private BlockState originalState;
    public MultiBlockPartEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.MULTI_BLOCK_PART.get(), pPos, pBlockState);
    }

    @Override
    public void load(CompoundTag tag) {
        HolderGetter<Block> holdergetter = (this.level != null ? this.level.holderLookup(Registries.BLOCK) : BuiltInRegistries.BLOCK.asLookup());
        if(tag.contains(CONTROLLER_POS)) controllerPos = NbtUtils.readBlockPos(tag.getCompound(CONTROLLER_POS));
        if(tag.contains(ORIGINAL_STATE)) originalState = NbtUtils.readBlockState(holdergetter, tag.getCompound(ORIGINAL_STATE));
        super.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        if(getControllerPos().isPresent()) tag.put(CONTROLLER_POS, NbtUtils.writeBlockPos(getControllerPos().get()));
        if(!getOriginalState().isAir()) tag.put(ORIGINAL_STATE, NbtUtils.writeBlockState(getOriginalState()));
        super.saveAdditional(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = super.getUpdateTag();
        saveAdditional(compoundtag);
        return compoundtag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void setControllerPos(BlockPos controllerPos) {
        this.controllerPos = controllerPos;
    }

    public void setOriginalState(BlockState previousState) {
        this.originalState = previousState;
    }

    public Optional<BlockPos> getControllerPos() {
        return Optional.ofNullable(controllerPos);
    }

    public BlockState getOriginalState() {
        if(originalState == null || originalState.getBlock() instanceof MultiBlockPart) {
            return Blocks.AIR.defaultBlockState();
        }
        return originalState;
    }
}
