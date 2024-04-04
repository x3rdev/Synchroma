package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.worldgen.VoidChunkGenerator;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

import java.util.OptionalLong;

public class DimensionRegistry {

    public static final ResourceKey<DimensionType> SPACESHIP_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(Synchroma.MOD_ID, "spaceship_dimension_type"));
    public static final ResourceKey<LevelStem> SPACESHIP_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(Synchroma.MOD_ID, "spaceship_level_stem"));

    public static void bootstrapDimensionType(BootstapContext<DimensionType> context) {
        context.register(SPACESHIP_DIMENSION_TYPE, new DimensionType(
                OptionalLong.of(12000),
                false,
                false,
                false,
                false,
                1.0,
                false,
                false,
                0,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                1.0F,
                new DimensionType.MonsterSettings(false, false, ConstantInt.ZERO, 0)
        ));
    }

    public static void bootstrapLevelStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);

        VoidChunkGenerator voidChunkGenerator = new VoidChunkGenerator(biomeRegistry.getOrThrow(Biomes.THE_VOID));

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(DimensionRegistry.SPACESHIP_DIMENSION_TYPE), voidChunkGenerator);
        context.register(SPACESHIP_LEVEL_STEM, stem);
    }
}
