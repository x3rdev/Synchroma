package com.github.x3r.synchroma.common.datagen;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.registry.BlockRegistry;
import com.github.x3r.synchroma.common.tag.SynchromaBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SynchromaTagProvider extends BlockTagsProvider {

    public SynchromaTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Synchroma.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(SynchromaBlockTags.ENERGY_PIPE).add(BlockRegistry.ENERGY_PIPE.get());
    }
}
