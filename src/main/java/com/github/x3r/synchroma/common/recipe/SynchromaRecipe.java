package com.github.x3r.synchroma.common.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public abstract class SynchromaRecipe implements Recipe<Container> {

    protected final ResourceLocation id;
    protected final ItemStack[] inputItems;
    protected final FluidStack[] inputFluids;
    protected final ItemStack[] outputItems;
    protected final FluidStack[] outputFluids;
    protected final int processingTime;

    protected SynchromaRecipe(ResourceLocation id, @Nullable ItemStack[] inputItems, @Nullable FluidStack[] inputFluids, @Nullable ItemStack[] outputItems, @Nullable FluidStack[] outputFluids, int processingTime) {
        this.id = id;
        this.inputItems = inputItems;
        this.inputFluids = inputFluids;
        this.outputItems = outputItems;
        this.outputFluids = outputFluids;
        this.processingTime = processingTime;
    }

    public ItemStack[] getInputItems() {

        return inputItems;
    }

    public FluidStack[] getInputFluids() {

        return inputFluids;
    }

    public ItemStack[] getOutputItems() {

        return outputItems;
    }

    public FluidStack[] getOutputFluids() {

        return outputFluids;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public abstract int maxItemInputs();

    // Do fluids as well
    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        for (int i = 0; i < maxItemInputs(); i++) {
            ItemStack correctItem = this.inputItems[i].copy();
            ItemStack containerItem = pContainer.getItem(i);
            if(containerItem.getCount() < correctItem.getCount() || !containerItem.is(correctItem.getItem())) {
                return false;
            }
            CompoundTag correctTag = correctItem.getTag();
            CompoundTag containerTag = containerItem.getTag();
            if(correctTag != null) {
                if(containerTag == null) {
                    return false;
                }
                if(correctTag.getAllKeys().stream().noneMatch(s -> containerTag.contains(s) && containerTag.get(s).equals(correctTag.get(s)))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }
}
