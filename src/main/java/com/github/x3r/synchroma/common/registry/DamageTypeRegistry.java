package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class DamageTypeRegistry {

    public static final ResourceKey<DamageType> BULLET_BODY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Synchroma.MOD_ID, "bullet_body"));
}
