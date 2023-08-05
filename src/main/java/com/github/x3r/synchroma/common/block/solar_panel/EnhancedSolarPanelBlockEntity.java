package com.github.x3r.synchroma.common.block.solar_panel;

import com.github.x3r.synchroma.common.block.SynchromaEnergyStorage;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlockEntity;
import com.github.x3r.synchroma.common.item.circuit.CircuitItem;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.common.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class EnhancedSolarPanelBlockEntity extends ControllerBlockEntity {

    public static final int MAX_ENERGY = 20000;
    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private final SynchromaEnergyStorage energyStorage = new SynchromaEnergyStorage(0, 1000, MAX_ENERGY);
    private final LazyOptional<SynchromaEnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);

    public EnhancedSolarPanelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.ENHANCED_SOLAR_PANEL.get(), pPos, pBlockState);
    }

    @Override
    protected BlockPattern getBlockPattern() {
        return BlockPatternBuilder.start()
                .where('i', ControllerBlockEntity.blockMatch(BlockRegistry.ENERGY_INPUT_BUFFER.get()))
                .where('c', ControllerBlockEntity.blockMatch(BlockRegistry.ENHANCED_SOLAR_PANEL.get()))
                .where('s', ControllerBlockEntity.blockMatch(BlockRegistry.BASIC_SOLAR_PANEL.get()))
                .where('*', blockInWorld -> blockInWorld.getState().isAir())
                .aisle("s*s", "***")
                .aisle("scs", "*i*")
                .aisle("s*s", "***")
                .build();
    }

    @Override
    protected Vec3i getPatternOffset() {
        return new Vec3i(-1, 0, 1);
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return null;
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return null;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {

    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    @Override
    public void clearContent() {

    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }
}
