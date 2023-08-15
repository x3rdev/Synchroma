package com.github.x3r.synchroma.common.block;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;

public class SynchromaEnergyStorage implements IEnergyStorage, INBTSerializable<CompoundTag> {

    public static final String TAG_KEY = "EnergyStorage";
    private int maxReceive;
    private int maxExtract;
    private int maxEnergy;
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

    public int forceReceiveEnergy(int maxReceive, boolean simulate) {
        int amount = Math.min(maxReceive, getMaxEnergyStored() - getEnergyStored());
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

    public int forceExtractEnergy(int maxExtract, boolean simulate) {
        int amount = Math.min(maxExtract, getEnergyStored());
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

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("MaxReceive", maxReceive);
        tag.putInt("MaxExtract", maxExtract);
        tag.putInt("MaxEnergy", maxEnergy);
        tag.putInt("Energy", energy);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        CompoundTag tag = nbt.getCompound(SynchromaEnergyStorage.TAG_KEY);
        this.maxReceive = tag.getInt("MaxReceive");
        this.maxExtract = tag.getInt("MaxExtract");
        this.maxEnergy = tag.getInt("MaxEnergy");
        this.energy = tag.getInt("Energy");
    }
}
