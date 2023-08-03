package com.github.x3r.synchroma.common.block.solar_panel;

import com.github.x3r.synchroma.client.menu.BasicSolarPanelMenu;
import com.github.x3r.synchroma.common.block.SynchromaEnergyStorage;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
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
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasicSolarPanelBlockEntity extends BaseContainerBlockEntity implements ICapabilityProvider {
    public static final int MAX_ENERGY = 10000;
    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private final SynchromaEnergyStorage energyStorage = new SynchromaEnergyStorage(0, 1000, MAX_ENERGY);
    private final LazyOptional<SynchromaEnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);

    public BasicSolarPanelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.BASIC_SOLAR_PANEL.get(), pPos, pBlockState);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, BasicSolarPanelBlockEntity pBlockEntity) {
        if(pLevel.canSeeSky(pPos.above())) {
            int time = (int) (pLevel.getDayTime() % 24000);
            float f = 1-(Math.abs(6000 - Math.max(0, 12000 - time)) / 6000F);
            pBlockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> {
                if (iEnergyStorage instanceof SynchromaEnergyStorage storage) {
                    storage.setEnergyStored(storage.getEnergyStored() + Math.round(10 * f));

                }
            });
        }
        pBlockEntity.markUpdated();
    }

    @Override
    protected Component getDefaultName() {
        return Component.literal("container.basic_solar_panel");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new BasicSolarPanelMenu(pContainerId, pInventory, this);
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
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
        tag.putInt("Energy", this.energyStorage.getEnergyStored());
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
        return tag;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap.equals(ForgeCapabilities.ENERGY)) {
            return energyStorageLazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyStorageLazyOptional.invalidate();
    }

    private void markUpdated() {
        this.getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }
}
