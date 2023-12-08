package com.github.x3r.synchroma.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface CyberwareCapabilityInterface extends INBTSerializable<CompoundTag> {

    String getValue();

    void setMyValue(String myValue);

}
