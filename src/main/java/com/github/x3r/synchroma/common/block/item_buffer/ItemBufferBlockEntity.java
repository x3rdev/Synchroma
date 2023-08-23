package com.github.x3r.synchroma.common.block.item_buffer;

import com.github.x3r.synchroma.common.block.multiblock.ControllerBlockEntity;
import com.github.x3r.synchroma.common.block.multiblock.PartBlockEntity;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemBufferBlockEntity extends PartBlockEntity implements ICapabilityProvider {
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
}
