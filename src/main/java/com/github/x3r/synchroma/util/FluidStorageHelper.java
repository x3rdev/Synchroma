package com.github.x3r.synchroma.util;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.SynchromaFluidStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.fluids.FluidStack;

public final class FluidStorageHelper {

    private FluidStorageHelper() {}

    public static void saveFluidStorage(CompoundTag tag, SynchromaFluidStorage storage) {
        ListTag listTag = new ListTag();
        for (int i = 0; i < storage.getTanks(); i++) {
            CompoundTag compoundTag = new CompoundTag();
            storage.getFluidInTank(i).writeToNBT(compoundTag);
            compoundTag.putInt("Capacity", storage.getTankCapacity(i));

            listTag.add(compoundTag);
        }
        tag.put("IFluidHandler", listTag);
    }

    //Validator is not saved
    public static void loadFluidStorage(CompoundTag tag, SynchromaFluidStorage storage) {
        ListTag listTag = tag.getList("IFluidHandler", 10);
        SynchromaFluidStorage.SynchromaFluidTank[] tanks = listTag.stream().map(compoundTag -> {
            FluidStack stack = FluidStack.loadFluidStackFromNBT((CompoundTag) compoundTag);
            int capacity = ((CompoundTag) compoundTag).getInt("Capacity");
            SynchromaFluidStorage.SynchromaFluidTank tank = new SynchromaFluidStorage.SynchromaFluidTank(capacity);
            tank.setFluid(stack);
            return tank;
        }).toArray(SynchromaFluidStorage.SynchromaFluidTank[]::new);
        for (int i = 0; i < storage.getTanks(); i++) {
            if(storage.getTankCapacity(i) != tanks[i].getCapacity()) {
                Synchroma.LOGGER.warn("Tank capacity was saved incorrectly or changed, still attempting to fill tank");
            }
            storage.setFluidInTank(i, tanks[i].getFluid());
        }
    }
}
