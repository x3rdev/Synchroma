package com.github.x3r.synchroma.common.block.item_buffer;

import com.github.x3r.synchroma.common.block.SynchromaBlockEntity;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlockEntity;
import com.github.x3r.synchroma.common.block.multiblock.PartBlockEntity;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemBufferBlockEntity extends PartBlockEntity implements Container, ICapabilityProvider {
    public ItemBufferBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.ITEM_BUFFER.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap.equals(ForgeCapabilities.ITEM_HANDLER) && getControllerPos().isPresent() && level.getBlockEntity(getControllerPos().get()) instanceof ControllerBlockEntity controller) {
            return controller.getCapability(cap, side);
        }
        return super.getCapability(cap, side);
    }

    @Override
    public int getContainerSize() {
        return getController().isPresent() ? getController().get().getContainerSize() : 0;
    }

    @Override
    public boolean isEmpty() {
        return getController().isEmpty() || getController().get().isEmpty();
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return getController().isPresent() ? getController().get().getItem(pSlot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return getController().isPresent() ? getController().get().removeItem(pSlot, pAmount) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return getController().isPresent() ? getController().get().removeItemNoUpdate(pSlot) : ItemStack.EMPTY;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        getController().ifPresent(controllerBlockEntity -> {
            controllerBlockEntity.setItem(pSlot, pStack);
        });
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return getController().isPresent() && getController().get().stillValid(pPlayer);
    }

    @Override
    public void clearContent() {
        getController().ifPresent(SynchromaBlockEntity::clearContent);
    }
}
