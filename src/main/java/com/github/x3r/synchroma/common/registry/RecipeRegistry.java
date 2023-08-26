package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.recipe.CentrifugeRecipe;
import com.github.x3r.synchroma.common.recipe.SynchromaRecipe;
import com.github.x3r.synchroma.common.recipe.SynchromaRecipeSerializer;
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
            () -> new RecipeType<>() {
                @Override
                public String toString() {

                    return Synchroma.MOD_ID+":centrifuge";
                }
            });

    public static final RegistryObject<RecipeSerializer<CentrifugeRecipe>> CENTRIFUGE_SERIALIZER = RECIPE_SERIALIZERS.register("centrifuge",
            () -> new SynchromaRecipeSerializer<>((recipeId, inputItems, inputFluids, outputItems, outputFluids, processingTime) -> new CentrifugeRecipe(recipeId, inputItems, outputItems, processingTime)));

}
