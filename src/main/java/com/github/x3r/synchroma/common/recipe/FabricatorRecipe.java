package com.github.x3r.synchroma.common.recipe;

import com.github.x3r.synchroma.common.registry.RecipeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class FabricatorRecipe extends SynchromaRecipe {
    public FabricatorRecipe(ResourceLocation id, ItemStack[] inputItems, ItemStack[] outputItems, int processingTime) {
        super(id, inputItems, null, outputItems, null, processingTime);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.FABRICATOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.FABRICATOR.get();
    }

    @Override
    public int maxItemInputs() {
        return 4;
    }
}
