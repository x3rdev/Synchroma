package com.github.x3r.synchroma.common.block.surgeon;

import com.github.x3r.synchroma.common.item.cyberware.ImplantLocation;
import com.github.x3r.synchroma.common.menu.SurgeonMenu;
import com.github.x3r.synchroma.common.block.SynchromaEnergyStorage;
import com.github.x3r.synchroma.common.block.SynchromaItemHandler;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlockEntity;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.common.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.state.BoneSnapshot;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

public class SurgeonBlockEntity extends ControllerBlockEntity {

    public final Map<String, BoneSnapshot> boneSnapshots = new HashMap<>();
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final LazyOptional<SynchromaItemHandler> itemHandlerOptional = LazyOptional.of(() -> new SynchromaItemHandler(6));
    private final LazyOptional<SynchromaEnergyStorage> energyStorageOptional = LazyOptional.of(() -> new SynchromaEnergyStorage(1000, 0, 20000));
    private final DataSlot dataAccess;
    private ImplantLocation implantLocation = ImplantLocation.HEAD;
    public SurgeonBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.SURGEON.get(), pPos, pBlockState);
        dataAccess = new DataSlot() {
            @Override
            public int get() {
                return implantLocation.ordinal();
            }

            @Override
            public void set(int i) {
                implantLocation = ImplantLocation.values()[i];
            }
        };
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, SurgeonBlockEntity blockEntity) {
        blockEntity.markUpdated();
    }

    @Override
    public double getTick(Object blockEntity) {
        return getAge();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        if(itemHandlerOptional.isPresent()) {
            return itemHandlerOptional.orElse(null).getItems();
        }
        return NonNullList.of(ItemStack.EMPTY);
    }

    @Override
    public BlockState[][][] getBlockPattern() {
        BlockState a = BlockRegistry.SURGEON.get().defaultBlockState();
        BlockState b = Blocks.IRON_BLOCK.defaultBlockState();
        return new BlockState[][][] {
                {{a}},
                {{b}}
        };
    }

    @Override
    public void assemble(ServerPlayer player) {
//        triggerAnim("assemble_controller", "open");
        super.assemble(player);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("machine.surgeon");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SurgeonMenu(pContainerId, pPlayerInventory, this, dataAccess);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandlerOptional.ifPresent(itemHandler -> itemHandler.deserializeNBT(tag));
        energyStorageOptional.ifPresent(energyStorage -> energyStorage.deserializeNBT(tag));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        itemHandlerOptional.ifPresent(itemHandler -> tag.put(SynchromaItemHandler.TAG_KEY, itemHandler.serializeNBT()));
        energyStorageOptional.ifPresent(energyStorage -> tag.put(SynchromaEnergyStorage.TAG_KEY, energyStorage.serializeNBT()));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
            return itemHandlerOptional.cast();
        }
        if(cap.equals(ForgeCapabilities.ENERGY)) {
            return energyStorageOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandlerOptional.invalidate();
        energyStorageOptional.invalidate();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, animationState -> PlayState.STOP)
                .triggerableAnim("setup", RawAnimation.begin().thenPlay("animation.surgeon.setup")));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
