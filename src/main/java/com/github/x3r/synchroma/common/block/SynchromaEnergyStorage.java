package com.github.x3r.synchroma.common.block;

import net.minecraftforge.energy.IEnergyStorage;

public class SynchromaEnergyStorage implements IEnergyStorage {
    private final int maxReceive;
    private final int maxExtract;
    private final int maxEnergy;

    private int energy = 0;

    public SynchromaEnergyStorage(int maxReceive, int maxExtract, int maxEnergy) {
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.maxEnergy = maxEnergy;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int amount = Math.min(Math.min(this.maxReceive, maxReceive), getMaxEnergyStored() - getEnergyStored());
        if(!simulate) {
            energy += amount;
        }
        return amount;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int amount = Math.min(Math.min(this.maxExtract, maxExtract), getEnergyStored());
        if(!simulate) {
            energy -= amount;
        }
        return amount;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    public void setEnergyStored(int energy) {
        this.energy = Math.min(this.getMaxEnergyStored(), energy);
    }

    @Override
    public int getMaxEnergyStored() {
        return maxEnergy;
    }

    @Override
    public boolean canExtract() {
        return maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return maxReceive > 0;
    }
}
