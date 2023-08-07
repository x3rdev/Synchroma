package com.github.x3r.synchroma.common.block.energy_buffer;

import com.github.x3r.synchroma.common.block.SynchromaEnergyStorage;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlockEntity;
import com.github.x3r.synchroma.common.block.multiblock.PartBlock;
import com.github.x3r.synchroma.common.block.multiblock.PartBlockEntity;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EnergyOutputBufferBlockEntity extends PartBlockEntity implements ICapabilityProvider {
    private final LazyOptional<SynchromaEnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> new SynchromaEnergyStorage(1000, 0, 10000));

    public EnergyOutputBufferBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.ENERGY_OUTPUT_BUFFER.get(), pPos, pBlockState);

    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        energyStorageLazyOptional.ifPresent(energyStorage -> tag.put(SynchromaEnergyStorage.TAG_KEY, energyStorage.serializeNBT()));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        energyStorageLazyOptional.ifPresent(energyStorage -> energyStorage.deserializeNBT(tag));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap.equals(ForgeCapabilities.ENERGY)) {
            return energyStorageLazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyStorageLazyOptional.invalidate();
    }
}
