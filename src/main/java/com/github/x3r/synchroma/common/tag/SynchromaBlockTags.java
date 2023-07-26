package com.github.x3r.synchroma.common.tag;

import com.github.x3r.synchroma.Synchroma;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class SynchromaBlockTags {
    public static final TagKey<Block> ENERGY_PIPE = TagKey.create(Registries.BLOCK,
            new ResourceLocation(Synchroma.MOD_ID, "energy_pipe"));
    public static final TagKey<Block> FLUID_PIPE = TagKey.create(Registries.BLOCK,
            new ResourceLocation(Synchroma.MOD_ID, "fluid_pipe"));
    public static final TagKey<Block> ITEM_PIPE = TagKey.create(Registries.BLOCK,
            new ResourceLocation(Synchroma.MOD_ID, "item_pipe"));
}
