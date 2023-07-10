package com.github.x3r.synchroma.common.block.energy_buffer;

import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnergyBufferBlockEntity extends BlockEntity {

    private final EnergyBufferEnergyStorage energyStorage;
    public EnergyBufferBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.ENERGY_BUFFER.get(), pPos, pBlockState);
        this.energyStorage = new EnergyBufferEnergyStorage(pBlockState.getValue(EnergyBufferBlock.INPUT_BUFFER));
    }

    private static class EnergyBufferEnergyStorage implements IEnergyStorage, ICapabilityProvider {
        private final boolean inputBuffer;
        private int energy;

        public EnergyBufferEnergyStorage(boolean inputBuffer) {
            this.inputBuffer = inputBuffer;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            if (!canReceive())
                return 0;

            int energyReceived = Math.min(getMaxEnergyStored() - energy, Math.min(1000, maxReceive));
            if (!simulate)
                energy += energyReceived;
            return energyReceived;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return 0;
        }

        @Override
        public int getEnergyStored() {
            return this.energy;
        }

        @Override
        public int getMaxEnergyStored() {
            return 10000;
        }

        @Override
        public boolean canExtract() {
            return !inputBuffer;
        }

        @Override
        public boolean canReceive() {
            return inputBuffer;
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return null;
        }

        public void setEnergy(int energy) {
            this.energy = energy;
        }
    }

}
