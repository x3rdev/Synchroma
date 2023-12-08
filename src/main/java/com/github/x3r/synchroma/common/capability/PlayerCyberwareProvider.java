package com.github.x3r.synchroma.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerCyberwareProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    private CyberwareCapability cyberware;
    private final LazyOptional<CyberwareCapability> cyberwareLazyOptional = LazyOptional.of(this::getPlayerCyberware);

    private CyberwareCapability getPlayerCyberware() {
        if(this.cyberware == null) {
            this.cyberware = new CyberwareCapability();
        }

        return this.cyberware;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if(capability.equals(CyberwareCapability.INSTANCE)) {
            return cyberwareLazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        getPlayerCyberware().saveToNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        getPlayerCyberware().loadFromNBT(compoundTag);
    }
}
