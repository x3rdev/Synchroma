package com.github.x3r.synchroma.common.block.basic_pump;

import com.github.x3r.synchroma.client.menu.BasicPumpMenu;
import com.github.x3r.synchroma.common.block.SynchromaEnergyStorage;
import com.github.x3r.synchroma.common.block.SynchromaFluidStorage;
import com.github.x3r.synchroma.common.block.solar_panel.BasicSolarPanelBlockEntity;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.util.FluidStorageHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class BasicPumpBlockEntity extends BaseContainerBlockEntity {
    public static final int MAX_ENERGY = 10000;
    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private final SynchromaEnergyStorage energyStorage = new SynchromaEnergyStorage(1000, 0, MAX_ENERGY);
    private final LazyOptional<SynchromaEnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);
    private final SynchromaFluidStorage fluidStorage = new SynchromaFluidStorage(new SynchromaFluidStorage.SynchromaFluidTank[]{new SynchromaFluidStorage.SynchromaFluidTank(1000, fluidStack -> fluidStack.getFluid().isSame(Fluids.WATER))});
    private final LazyOptional<SynchromaFluidStorage> fluidStorageLazyOptional = LazyOptional.of(() -> fluidStorage);
    public BasicPumpBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.BASIC_PUMP.get(), pPos, pBlockState);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, BasicPumpBlockEntity pBlockEntity) {
        if(pLevel.getGameTime() % 2 == 0 && pLevel.getFluidState(pPos.below()).is(Fluids.WATER)) {
            if(pBlockEntity.energyStorage.getEnergyStored() > 10) {
                int amount = pBlockEntity.fluidStorage.fill(new FluidStack(Fluids.WATER, 10), IFluidHandler.FluidAction.EXECUTE);
                if (amount > 0) {
                    pBlockEntity.energyStorage.setEnergyStored(pBlockEntity.energyStorage.getEnergyStored() - 10);
                    pLevel.setBlockAndUpdate(pPos.below(), Blocks.AIR.defaultBlockState());
                }
            }
        }
        pBlockEntity.markUpdated();
    }

    @Override
    protected Component getDefaultName() {
        return Component.literal("container.basic_pump");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new BasicPumpMenu(pContainerId, pInventory, this);
    }

    @Override
    public int getContainerSize() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        return this.getItems().stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return getItems().get(pSlot);
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        ItemStack itemstack = ContainerHelper.removeItem(this.getItems(), pSlot, pAmount);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return ContainerHelper.takeItem(this.getItems(), pSlot);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        this.getItems().set(pSlot, pStack);
        if (pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }
        this.setChanged();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, this.items);
        this.energyStorage.setEnergyStored(pTag.getInt("Energy"));
        FluidStorageHelper.loadFluidStorage(pTag, this.fluidStorage);

    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
        tag.putInt("Energy", this.energyStorage.getEnergyStored());
        FluidStorageHelper.saveFluidStorage(tag, this.fluidStorage);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        ContainerHelper.saveAllItems(tag, this.items);
        tag.putInt("Energy", this.energyStorage.getEnergyStored());
        FluidStorageHelper.saveFluidStorage(tag, this.fluidStorage);
        return tag;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if(cap.equals(ForgeCapabilities.ENERGY)) {
            return energyStorageLazyOptional.cast();
        }
        if(cap.equals(ForgeCapabilities.FLUID_HANDLER)) {
            return fluidStorageLazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyStorageLazyOptional.invalidate();
        fluidStorageLazyOptional.invalidate();
    }

    private void markUpdated() {
        this.getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }
}
