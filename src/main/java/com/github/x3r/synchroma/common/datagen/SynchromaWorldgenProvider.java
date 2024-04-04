package com.github.x3r.synchroma.common.datagen;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.registry.DimensionRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SynchromaWorldgenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, DimensionRegistry::bootstrapDimensionType)
            .add(Registries.LEVEL_STEM, DimensionRegistry::bootstrapLevelStem);

    public SynchromaWorldgenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Synchroma.MOD_ID));
    }
}
