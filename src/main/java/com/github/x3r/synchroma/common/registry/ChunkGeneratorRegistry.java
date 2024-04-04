package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.worldgen.VoidChunkGenerator;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ChunkGeneratorRegistry {

    public static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNK_GENERATORS = DeferredRegister.create(Registries.CHUNK_GENERATOR, Synchroma.MOD_ID);

    public static final RegistryObject<Codec<? extends ChunkGenerator>> VOID_CHUNK_GENERATOR = CHUNK_GENERATORS.register("void",
            () -> VoidChunkGenerator.CODEC);
}
