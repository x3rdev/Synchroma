package com.github.x3r.synchroma.common.block.centrifuge;

import com.github.x3r.synchroma.common.menu.CentrifugeMenu;
import com.github.x3r.synchroma.common.block.FrameBlock;
import com.github.x3r.synchroma.common.block.SynchromaEnergyStorage;
import com.github.x3r.synchroma.common.block.SynchromaItemHandler;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlockEntity;
import com.github.x3r.synchroma.common.recipe.CentrifugeRecipe;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.common.registry.BlockRegistry;
import com.github.x3r.synchroma.common.registry.RecipeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CentrifugeBlockEntity extends ControllerBlockEntity {

    public static final RawAnimation ASSEMBLE_ANIM = RawAnimation.begin().thenPlay("animation.centrifuge.assemble");

    public final Map<String, BoneSnapshot> boneSnapshots = new HashMap<>();
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final LazyOptional<SynchromaItemHandler> itemHandlerOptional = LazyOptional.of(() -> new SynchromaItemHandler(5));
    private final LazyOptional<SynchromaEnergyStorage> energyStorageOptional = LazyOptional.of(() -> new SynchromaEnergyStorage(1000, 0, 20000));

    int processTime = 0;
    int recipeProcessTime = 0;
    float speedRatio = 0;
    public CentrifugeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.CENTRIFUGE.get(), pPos, pBlockState);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, CentrifugeBlockEntity blockEntity) {
        SynchromaItemHandler itemHandler = blockEntity.itemHandlerOptional.orElse(null);
        SynchromaEnergyStorage energyStorage = blockEntity.energyStorageOptional.orElse(null);
        Optional<CentrifugeRecipe> recipe = pLevel.getRecipeManager().getRecipeFor(RecipeRegistry.CENTRIFUGE.get(), blockEntity, pLevel);
        if (recipe.isPresent() && energyStorage.getEnergyStored() >= 20) {
            blockEntity.processTime++;
            if (blockEntity.speedRatio < 1) {
                blockEntity.speedRatio += 0.05;
            } else blockEntity.speedRatio = 1;
            energyStorage.forceExtractEnergy(20, false);
            blockEntity.recipeProcessTime = recipe.get().getProcessingTime();
            if (blockEntity.processTime >= blockEntity.recipeProcessTime) {
                blockEntity.processTime = 0;
                itemHandler.getStackInSlot(0).shrink(recipe.get().getInputItems()[0].getCount());
                for (int i = 0; i < recipe.get().getOutputItems().length; i++) {
                    ItemStack result = recipe.get().getOutputItems()[i].copy();
                    itemHandler.insertItem(i + 1, result, false);
                }
            }
        } else {
            if (blockEntity.processTime > 0) {
                blockEntity.processTime--;
            }
            blockEntity.speedRatio /= 1.05;
        }
        blockEntity.markUpdated();
    }

    public int getProcessTime() {
        return processTime;
    }

    public int getRecipeProcessTime() {
        return recipeProcessTime;
    }

    public float getSpeedRatio() {
        return speedRatio;
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
        BlockState a = BlockRegistry.CENTRIFUGE.get().defaultBlockState();
        BlockState b = BlockRegistry.ENERGY_BUFFER.get().defaultBlockState();
        BlockState c = BlockRegistry.ITEM_BUFFER.get().defaultBlockState();
        BlockState d = BlockRegistry.SHAFT.get().defaultBlockState();
        BlockState e = Blocks.IRON_BARS.defaultBlockState();
        BlockState f = Blocks.GLASS_PANE.defaultBlockState();
        BlockState g = Blocks.IRON_BLOCK.defaultBlockState();
        BlockState h = BlockRegistry.FRAME.get().defaultBlockState().setValue(FrameBlock.PLATES, true).setValue(FrameBlock.WIRES, true);
        return new BlockState[][][] {
                {{h, c, h},{g, f, g},{h, h, h}},
                {{a, d, b},{f, e, f},{h, d, h}},
                {{h, c, h},{g, f, g},{h, h, h}}
        };
    }

    @Override
    public void assemble(ServerPlayer player) {
        triggerAnim("assemble_controller", "assemble");
        super.assemble(player);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("machine.centrifuge");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CentrifugeMenu(pContainerId, pPlayerInventory, this);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandlerOptional.ifPresent(itemHandler -> itemHandler.deserializeNBT(tag));
        energyStorageOptional.ifPresent(energyStorage -> energyStorage.deserializeNBT(tag));
        processTime = tag.getInt("process_time");
        recipeProcessTime = tag.getInt("recipe_process_time");
        speedRatio = tag.getFloat("speed_ratio");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        itemHandlerOptional.ifPresent(itemHandler -> tag.put(SynchromaItemHandler.TAG_KEY, itemHandler.serializeNBT()));
        energyStorageOptional.ifPresent(energyStorage -> tag.put(SynchromaEnergyStorage.TAG_KEY, energyStorage.serializeNBT()));
        tag.putInt("process_time", processTime);
        tag.putInt("recipe_process_time", recipeProcessTime);
        tag.putFloat("speed_ratio", speedRatio);
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
        controllerRegistrar.add(new AnimationController<>(this, "assemble_controller", 0, animationState -> PlayState.STOP)
                .triggerableAnim("assemble", ASSEMBLE_ANIM));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
