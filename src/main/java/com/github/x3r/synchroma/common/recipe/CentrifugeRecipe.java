package com.github.x3r.synchroma.common.recipe;

import com.github.x3r.synchroma.common.registry.RecipeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class CentrifugeRecipe extends SynchromaRecipe {

    public CentrifugeRecipe(ResourceLocation id, @Nullable ItemStack[] inputItems, @Nullable ItemStack[] outputItems, int processingTime) {
        super(id, inputItems, outputItems, null, null, processingTime);
    }
    @Override
    public int maxItemInputs() {
        return 1;
    }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.CENTRIFUGE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.CENTRIFUGE.get();
    }
}
