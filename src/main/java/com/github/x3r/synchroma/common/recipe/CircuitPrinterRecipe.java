package com.github.x3r.synchroma.common.recipe;

import com.github.x3r.synchroma.common.registry.RecipeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class CircuitPrinterRecipe extends SynchromaRecipe {

    public CircuitPrinterRecipe(ResourceLocation id, @Nullable ItemStack[] inputItems, @Nullable ItemStack[] outputItems, int processingTime) {
        super(id, inputItems, outputItems, null, null, processingTime);
    }

    @Override
    public int maxItemInputs() {
        return 7;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.CIRCUIT_PRINTER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.CIRCUIT_PRINTER.get();
    }
}
