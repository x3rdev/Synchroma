package com.github.x3r.synchroma.common.block.multiblock;

import com.github.x3r.synchroma.common.scheduler.Scheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.Option;
import java.util.Optional;

public abstract class PartBlockEntity extends BlockEntity {
    public static final String CONTROLLER_POS = "ControllerPos";
    public static final String ORIGINAL_STATE = "OriginalState";
    protected BlockPos controllerPos;
    protected PartBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if(tag.contains(CONTROLLER_POS)) controllerPos = NbtUtils.readBlockPos(tag.getCompound(CONTROLLER_POS));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if(getControllerPos().isPresent()) tag.put(CONTROLLER_POS, NbtUtils.writeBlockPos(getControllerPos().get()));
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    public void assemble(BlockPos controllerPos) {
        if(!getBlockState().getValue(PartBlock.ASSEMBLED)) {
            this.controllerPos = controllerPos;
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(PartBlock.ASSEMBLED, true));
        }
    }
    public void disassemble() {
        if(getBlockState().getValue(PartBlock.ASSEMBLED)) {
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(PartBlock.ASSEMBLED, false));
            getControllerPos().ifPresent(pos -> {
                if (level.getBlockEntity(pos) instanceof ControllerBlockEntity controllerBlockEntity) {
                    controllerBlockEntity.disassemble();
                }
            });
        }
    }
    public Optional<BlockPos> getControllerPos() {
        return Optional.ofNullable(controllerPos);
    }

    protected Optional<ControllerBlockEntity> getController() {
        return getControllerPos().isPresent() ? Optional.ofNullable(((ControllerBlockEntity) level.getBlockEntity(getControllerPos().get()))) : Optional.empty();
    }
}
