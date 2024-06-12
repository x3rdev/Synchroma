package com.github.x3r.synchroma.common.block.energy_storage;

import com.github.x3r.synchroma.common.block.SynchromaEnergyStorage;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasicEnergyStorageBlockEntity extends BlockEntity {

    private final LazyOptional<SynchromaEnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> new SynchromaEnergyStorage(1000, 0, 10000));

    public BasicEnergyStorageBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.BASIC_ENERGY_STORAGE.get(), pPos, pBlockState);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, BasicEnergyStorageBlockEntity pBlockEntity) {
        pBlockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> {
            int i = (int) (14*((float) iEnergyStorage.getEnergyStored() / iEnergyStorage.getMaxEnergyStored()));
            if(pState.getValue(BasicEnergyStorageBlock.LEVEL) != i) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(BasicEnergyStorageBlock.LEVEL, i));
            }
        });
        pBlockEntity.markUpdated();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        energyStorageLazyOptional.ifPresent(energyStorage -> energyStorage.deserializeNBT(tag));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        energyStorageLazyOptional.ifPresent(energyStorage -> tag.put(SynchromaEnergyStorage.TAG_KEY, energyStorage.serializeNBT()));
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

    protected void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }
}
