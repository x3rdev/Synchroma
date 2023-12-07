package com.github.x3r.synchroma.common.capability;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

@AutoRegisterCapability
public class CyberwareCapability {

    public static final Capability<CyberwareCapabilityInterface> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});
}
