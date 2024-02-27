package com.github.x3r.synchroma.common.block.circuit_printer;

import com.github.x3r.synchroma.common.block.SynchromaBlockEntity;
import com.github.x3r.synchroma.common.block.SynchromaEnergyStorage;
import com.github.x3r.synchroma.common.block.SynchromaItemHandler;
import com.github.x3r.synchroma.common.menu.CircuitPrinterMenu;
import com.github.x3r.synchroma.common.recipe.CircuitPrinterRecipe;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.common.registry.RecipeRegistry;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class CircuitPrinterBlockEntity extends SynchromaBlockEntity implements GeoBlockEntity {
    public static final RawAnimation RECIPE_ANIM = RawAnimation.begin().thenPlay("animation.circuit_printer.print");
    public static final RawAnimation INTERRUPT_ANIM = RawAnimation.begin().thenPlay("animation.circuit_printer.interrupt");
    private final LazyOptional<SynchromaItemHandler> itemHandlerOptional = LazyOptional.of(() -> new SynchromaItemHandler(8));
    private final LazyOptional<SynchromaEnergyStorage> energyStorageOptional = LazyOptional.of(() -> new SynchromaEnergyStorage(1000, 0, 10000));
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    int processTime = 0;
    int recipeProcessTime = 0;
    public CircuitPrinterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.CIRCUIT_PRINTER.get(), pPos, pBlockState);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, CircuitPrinterBlockEntity blockEntity) {
        SynchromaItemHandler itemHandler = blockEntity.itemHandlerOptional.orElse(null);
        SynchromaEnergyStorage energyStorage = blockEntity.energyStorageOptional.orElse(null);
        Optional<CircuitPrinterRecipe> recipe = pLevel.getRecipeManager().getRecipeFor(RecipeRegistry.CIRCUIT_PRINTER.get(), blockEntity, pLevel);
        if (recipe.isPresent() && energyStorage.getEnergyStored() >= 20 && itemHandler.outputSpacePresent(recipe.get(), new int[]{7})) {
            blockEntity.processTime++;
            energyStorage.forceExtractEnergy(20, false);
            blockEntity.recipeProcessTime = recipe.get().getProcessingTime();
            if(blockEntity.processTime == 1) {
                blockEntity.triggerAnim("recipe_controller", "recipe");
            }
            if (blockEntity.processTime >= blockEntity.recipeProcessTime) {
                blockEntity.processTime = 0;
                for (int i = 0; i < recipe.get().getInputItems().length; i++) {
                    itemHandler.getStackInSlot(i).shrink(recipe.get().getInputItems()[i].getCount());
                }
                for (int i = 0; i < recipe.get().getOutputItems().length; i++) {
                    ItemStack result = recipe.get().getOutputItems()[i].copy();
                    itemHandler.insertItem(i + 7, result, false);
                }
            }
        } else {
            if (blockEntity.processTime > 0) {
                blockEntity.processTime--;
            } else {
                blockEntity.triggerAnim("recipe_controller", "interrupt");
            }
        }
        blockEntity.markUpdated();
    }

    public int getProcessTime() {
        return processTime;
    }

    public int getRecipeProcessTime() {
        return recipeProcessTime;
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
    public Component getDisplayName() {
        return Component.literal("machine.circuit_printer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new CircuitPrinterMenu(i, inventory, this);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandlerOptional.ifPresent(itemHandler -> itemHandler.deserializeNBT(tag));
        energyStorageOptional.ifPresent(energyStorage -> energyStorage.deserializeNBT(tag));
        processTime = tag.getInt("process_time");
        recipeProcessTime = tag.getInt("recipe_process_time");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        itemHandlerOptional.ifPresent(itemHandler -> tag.put(SynchromaItemHandler.TAG_KEY, itemHandler.serializeNBT()));
        energyStorageOptional.ifPresent(energyStorage -> tag.put(SynchromaEnergyStorage.TAG_KEY, energyStorage.serializeNBT()));
        tag.putInt("process_time", processTime);
        tag.putInt("recipe_process_time", recipeProcessTime);
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "recipe_controller", 0, animationState -> PlayState.STOP)
                .triggerableAnim("recipe", RECIPE_ANIM)
                .triggerableAnim("interrupt", INTERRUPT_ANIM)
                .setAnimationSpeedHandler(blockEntity -> 320D/blockEntity.getRecipeProcessTime()));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
