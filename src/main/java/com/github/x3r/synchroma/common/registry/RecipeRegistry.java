package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.recipe.*;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class RecipeRegistry {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Synchroma.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Synchroma.MOD_ID);

    public static final RegistryObject<RecipeType<CentrifugeRecipe>> CENTRIFUGE = RECIPE_TYPES.register("centrifuge",
            () -> RecipeType.simple(new ResourceLocation(Synchroma.MOD_ID, "centrifuge")));

    public static final RegistryObject<RecipeType<FabricatorRecipe>> FABRICATOR = RECIPE_TYPES.register("fabricator",
            () -> RecipeType.simple(new ResourceLocation(Synchroma.MOD_ID, "fabricator")));

    public static final RegistryObject<RecipeType<CircuitPrinterRecipe>> CIRCUIT_PRINTER = RECIPE_TYPES.register("circuit_printer",
            () -> RecipeType.simple(new ResourceLocation(Synchroma.MOD_ID, "circuit_printer")));

    public static final RegistryObject<RecipeSerializer<CentrifugeRecipe>> CENTRIFUGE_SERIALIZER = RECIPE_SERIALIZERS.register("centrifuge",
            () -> new SynchromaRecipeSerializer<>((recipeId, inputItems, inputFluids, outputItems, outputFluids, processingTime) -> new CentrifugeRecipe(recipeId, inputItems, outputItems, processingTime)));
    public static final RegistryObject<RecipeSerializer<FabricatorRecipe>> FABRICATOR_SERIALIZER = RECIPE_SERIALIZERS.register("fabricator",
            () -> new SynchromaRecipeSerializer<>((recipeId, inputItems, inputFluids, outputItems, outputFluids, processingTime) -> new FabricatorRecipe(recipeId, inputItems, outputItems, processingTime)));
    public static final RegistryObject<RecipeSerializer<CircuitPrinterRecipe>> CIRCUIT_PRINTER_SERIALIZER = RECIPE_SERIALIZERS.register("circuit_printer",
            () -> new SynchromaRecipeSerializer<>((recipeId, inputItems, inputFluids, outputItems, outputFluids, processingTime) -> new CircuitPrinterRecipe(recipeId, inputItems, outputItems, processingTime)));
}
