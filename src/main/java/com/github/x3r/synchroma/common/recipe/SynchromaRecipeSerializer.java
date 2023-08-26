package com.github.x3r.synchroma.common.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class SynchromaRecipeSerializer<T extends SynchromaRecipe> implements RecipeSerializer<T> {

    protected final IFactory<T> factory;

    public SynchromaRecipeSerializer(IFactory<T> factory) {
        this.factory = factory;
    }


    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {

        ItemStack[] inputItems = new ItemStack[]{};
        FluidStack[] inputFluids = new FluidStack[]{};
        ItemStack[] outputItems = new ItemStack[]{};
        FluidStack[] outputFluids = new FluidStack[]{};
        int processingTime = 0;

        if(json.has("input_items")) {
            inputItems = json.getAsJsonArray("input_items").asList().stream().map(element -> {
                DataResult<ItemStack> result = ItemStack.CODEC.parse(JsonOps.INSTANCE, GsonHelper.getAsJsonObject(element.getAsJsonObject(), "item_stack"));
                return result.result().orElse(null);
            }).filter(Objects::nonNull).toArray(ItemStack[]::new);
        }
        if(json.has("input_fluids")) {
            inputFluids = json.getAsJsonArray("input_fluids").asList().stream().map(element -> {
                DataResult<FluidStack> result = FluidStack.CODEC.parse(JsonOps.INSTANCE, GsonHelper.getAsJsonObject(element.getAsJsonObject(), "fluid_stack"));
                return result.result().orElse(null);
            }).filter(Objects::nonNull).toArray(FluidStack[]::new);
        }
        if(json.has("output_items")) {
            outputItems = json.getAsJsonArray("output_items").asList().stream().map(element -> {
                DataResult<ItemStack> result = ItemStack.CODEC.parse(JsonOps.INSTANCE, GsonHelper.getAsJsonObject(element.getAsJsonObject(), "item_stack"));
                return result.result().orElse(null);
            }).filter(Objects::nonNull).toArray(ItemStack[]::new);
        }
        if(json.has("output_fluids")) {
            outputFluids = json.getAsJsonArray("output_fluids").asList().stream().map(element -> {
                DataResult<FluidStack> result = FluidStack.CODEC.parse(JsonOps.INSTANCE, GsonHelper.getAsJsonObject(element.getAsJsonObject(), "fluid_stack"));
                return result.result().orElse(null);
            }).filter(Objects::nonNull).toArray(FluidStack[]::new);
        }
        if(json.has("processing_time")) {
            processingTime = GsonHelper.getAsInt(json.getAsJsonObject(), "processing_time");
        }

        return factory.create(recipeId, inputItems, inputFluids, outputItems, outputFluids, processingTime);
    }

    @Override
    public @Nullable T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buf) {
        ItemStack[] inputItems = new ItemStack[buf.readVarInt()];
        for (int i = 0; i < inputItems.length; i++) {
            inputItems[i] = buf.readItem();
        }
        FluidStack[] inputFluids = new FluidStack[buf.readVarInt()];
        for (int i = 0; i < inputFluids.length; i++) {
            inputFluids[i] = FluidStack.readFromPacket(buf);
        }
        ItemStack[] outputItems = new ItemStack[buf.readVarInt()];
        for (int i = 0; i < outputItems.length; i++) {
            outputItems[i] = buf.readItem();
        }
        FluidStack[] outputFluids = new FluidStack[buf.readVarInt()];
        for (int i = 0; i < outputFluids.length; i++) {
            outputFluids[i] = FluidStack.readFromPacket(buf);
        }

        int processingTime = buf.readVarInt();

        return factory.create(recipeId, inputItems, inputFluids, outputItems, outputFluids, processingTime);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, T recipe) {
        buf.writeVarInt(recipe.getInputItems().length);
        Arrays.stream(recipe.inputItems).forEachOrdered(stack -> buf.writeItemStack(stack, false));
        buf.writeVarInt(recipe.getInputFluids().length);
        Arrays.stream(recipe.getInputFluids()).forEachOrdered(fluidStack -> fluidStack.writeToPacket(buf));
        buf.writeVarInt(recipe.getOutputItems().length);
        Arrays.stream(recipe.getOutputItems()).forEachOrdered(stack -> buf.writeItemStack(stack, false));
        buf.writeVarInt(recipe.getOutputFluids().length);
        Arrays.stream(recipe.getOutputFluids()).forEachOrdered(fluidStack -> fluidStack.writeToPacket(buf));
        buf.writeVarInt(recipe.processingTime);
    }

    public interface IFactory<T extends SynchromaRecipe> {

        T create(ResourceLocation recipeId, ItemStack[] inputItems, FluidStack[] inputFluids, ItemStack[] outputItems, FluidStack[] outputFluids, int processingTime);
    }
}
