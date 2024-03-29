package com.github.x3r.synchroma.common.block;

import com.github.x3r.synchroma.common.recipe.FabricatorRecipe;
import com.github.x3r.synchroma.common.recipe.SynchromaRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class SynchromaItemHandler extends ItemStackHandler {

    public static final String TAG_KEY = "ItemHandler";
    public SynchromaItemHandler(int size) {
        super(size);
    }

    public SynchromaItemHandler(NonNullList<ItemStack> stacks) {
        super(stacks);
    }

    public NonNullList<ItemStack> getItems() {
        return stacks;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt.getCompound(TAG_KEY));
    }

    public boolean outputSpacePresent(SynchromaRecipe recipe, int[] outputSlots) {
        for (int i = 0; i < outputSlots.length; i++) {
            ItemStack result = recipe.getOutputItems()[i].copy();
            if(!this.insertItem(outputSlots[i], result, true).equals(ItemStack.EMPTY)) {
                return false;
            }
        }
        return true;
    }
}
