package com.github.x3r.synchroma.common.block;

import com.github.x3r.synchroma.Synchroma;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SynchromaFluidStorage implements IFluidHandler {
    private final SynchromaFluidTank[] tanks;

    public SynchromaFluidStorage(SynchromaFluidTank[] tanks) {
        this.tanks = tanks;
    }

    @Override
    public int getTanks() {
        return tanks.length;
    }

    public SynchromaFluidTank getTank(int tank) {
        SynchromaFluidTank fluidTank = null;
        try {
            fluidTank = tanks[tank];
        } catch (ArrayIndexOutOfBoundsException exception) {
            Synchroma.LOGGER.warn("Attempted to access invalid tank");
        }
        return fluidTank;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return getTank(tank).getFluid();
    }

    public void setFluidInTank(int tank, FluidStack stack) {
        getTank(tank).setFluid(stack);
    }

    @Override
    public int getTankCapacity(int tank) {
        return getTank(tank).getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return getTank(tank).isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        FluidStack copy = resource.copy();
        for (int i = 0; i < tanks.length; i++) {
            copy.shrink(tanks[i].fill(copy, action));
        }
        return resource.getAmount() - copy.getAmount();
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
        FluidStack copy = resource.copy();
        for (int i = tanks.length - 1; i >= 0; i--) {
            copy.shrink(tanks[i].drain(copy, action).getAmount());
        }
        return new FluidStack(resource.getFluid(), resource.getAmount() - copy.getAmount());
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
        int drained = 0;
        Fluid fluid = Arrays.stream(tanks).map(t -> t.fluid.getFluid()).toList().get(getTanks() - 1);
        for (int i = tanks.length - 1; i >= 0; i--) {
            if(tanks[i].getFluid().getFluid().isSame(fluid)) {
                FluidStack stack = tanks[i].drain(maxDrain - drained, action);
                drained += stack.getAmount();
            }
        }
        return new FluidStack(fluid, drained);
    }

    public static class SynchromaFluidTank implements IFluidTank {

        private final int capacity;
        private FluidStack fluid = FluidStack.EMPTY;
        private Predicate<FluidStack> validator;

        public SynchromaFluidTank(int capacity) {
            this(capacity, fluidStack -> true);
        }

        public SynchromaFluidTank(int capacity, Predicate<FluidStack> validator) {
            this.capacity = capacity;
            this.validator = validator;
        }

        @Override
        public @NotNull FluidStack getFluid() {
            return this.fluid;
        }

        @Override
        public int getFluidAmount() {
            return this.fluid.getAmount();
        }

        @Override
        public int getCapacity() {
            return this.capacity;
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return validator.test(stack);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (resource.isEmpty() || !isFluidValid(resource)) {
                return 0;
            }
            if (action.simulate()) {
                if (fluid.isEmpty()) {
                    return Math.min(capacity, resource.getAmount());
                }
                if (!fluid.isFluidEqual(resource)) {
                    return 0;
                }
                return Math.min(capacity - fluid.getAmount(), resource.getAmount());
            }
            if (fluid.isEmpty()) {
                fluid = new FluidStack(resource, Math.min(capacity, resource.getAmount()));
                onContentsChanged();
                return fluid.getAmount();
            }
            if (!fluid.isFluidEqual(resource)) {
                return 0;
            }
            int filled = capacity - fluid.getAmount();

            if (resource.getAmount() < filled) {
                fluid.grow(resource.getAmount());
                filled = resource.getAmount();
            }
            else {
                fluid.setAmount(capacity);
            }
            if (filled > 0) onContentsChanged();
            return filled;
        }

        //Just in case its needed later
        protected void onContentsChanged() {

        }

        @Override
        public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
            if (resource.isEmpty() || !resource.isFluidEqual(fluid)) {
                return FluidStack.EMPTY;
            }
            return drain(resource.getAmount(), action);
        }

        @Override
        public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
            int drained = maxDrain;
            if (fluid.getAmount() < drained) {
                drained = fluid.getAmount();
            }
            FluidStack stack = new FluidStack(fluid, drained);
            if (action.execute() && drained > 0) {
                fluid.shrink(drained);
                onContentsChanged();
            }
            return stack;
        }

        public void setFluid(FluidStack stack) {
            this.fluid = stack;
        }

    }
}
