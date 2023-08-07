package com.github.x3r.synchroma.common.block.pump;

import com.github.x3r.synchroma.client.menu.BasicPumpMenu;
import com.github.x3r.synchroma.common.block.SynchromaBlockEntity;
import com.github.x3r.synchroma.common.block.SynchromaEnergyStorage;
import com.github.x3r.synchroma.common.block.SynchromaFluidHandler;
import com.github.x3r.synchroma.common.block.SynchromaItemHandler;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class BasicPumpBlockEntity extends SynchromaBlockEntity {

    private final LazyOptional<SynchromaItemHandler> itemHandlerOptional = LazyOptional.of(() -> new SynchromaItemHandler(3));
    private final LazyOptional<SynchromaEnergyStorage> energyStorageOptional = LazyOptional.of(() -> new SynchromaEnergyStorage(1000, 0, 10000));
    private final LazyOptional<SynchromaFluidHandler> fluidStorageOptional = LazyOptional.of(() -> new SynchromaFluidHandler(new SynchromaFluidHandler.SynchromaFluidTank[]{new SynchromaFluidHandler.SynchromaFluidTank(1000, fluidStack -> fluidStack.getFluid().isSame(Fluids.WATER))}));
    public BasicPumpBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.BASIC_PUMP.get(), pPos, pBlockState);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, BasicPumpBlockEntity pBlockEntity) {
        if (pLevel.getGameTime() % 2 == 0 && pLevel.getFluidState(pPos.below()).is(Fluids.WATER)) {
            pBlockEntity.energyStorageOptional.ifPresent(storage -> {
                if(storage.getEnergyStored() > 10) {
                    pBlockEntity.fluidStorageOptional.ifPresent(synchromaFluidHandler -> {
                        int amount = synchromaFluidHandler.fill(new FluidStack(Fluids.WATER, 10), IFluidHandler.FluidAction.EXECUTE);
                        if (amount > 0) {
                            storage.setEnergyStored(storage.getEnergyStored() - 10);
                            pLevel.setBlockAndUpdate(pPos.below(), Blocks.AIR.defaultBlockState());
                        }
                    });
                }
            });
        }
        pBlockEntity.markUpdated();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        if(itemHandlerOptional.isPresent()) {
            return itemHandlerOptional.orElse(null).getItems();
        }
        return NonNullList.of(ItemStack.EMPTY);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("machine.basic_solar_panel");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new BasicPumpMenu(pContainerId, pPlayerInventory, this);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandlerOptional.ifPresent(itemHandler -> itemHandler.deserializeNBT(pTag));
        energyStorageOptional.ifPresent(energyStorage -> energyStorage.deserializeNBT(pTag));
        fluidStorageOptional.ifPresent(fluidHandler -> fluidHandler.deserializeNBT(pTag));
    }
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        itemHandlerOptional.ifPresent(itemHandler -> tag.put(SynchromaItemHandler.TAG_KEY, itemHandler.serializeNBT()));
        energyStorageOptional.ifPresent(energyStorage -> tag.put(SynchromaEnergyStorage.TAG_KEY, energyStorage.serializeNBT()));
        fluidStorageOptional.ifPresent(fluidHandler -> tag.put(SynchromaFluidHandler.TAG_KEY, fluidHandler.serializeNBT()));
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if(cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
            return itemHandlerOptional.cast();
        }
        if(cap.equals(ForgeCapabilities.ENERGY)) {
            return energyStorageOptional.cast();
        }
        if(cap.equals(ForgeCapabilities.FLUID_HANDLER)) {
            return fluidStorageOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandlerOptional.invalidate();
        energyStorageOptional.invalidate();
        fluidStorageOptional.invalidate();
    }
}
