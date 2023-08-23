package com.github.x3r.synchroma.common.block.centrifuge;

import com.github.x3r.synchroma.client.menu.CentrifugeMenu;
import com.github.x3r.synchroma.common.block.FrameBlock;
import com.github.x3r.synchroma.common.block.SynchromaEnergyStorage;
import com.github.x3r.synchroma.common.block.SynchromaItemHandler;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlockEntity;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.common.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
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
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
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

public class CentrifugeBlockEntity extends ControllerBlockEntity {

    public static final RawAnimation ASSEMBLE_ANIM = RawAnimation.begin().thenPlay("animation.centrifuge.assemble");

    public final Map<String, BoneSnapshot> boneSnapshots = new HashMap<>();
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final LazyOptional<SynchromaItemHandler> itemHandlerOptional = LazyOptional.of(() -> new SynchromaItemHandler(3));
    private final LazyOptional<SynchromaEnergyStorage> energyStorageOptional = LazyOptional.of(() -> new SynchromaEnergyStorage(1000, 0, 20000));
    public CentrifugeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.CENTRIFUGE.get(), pPos, pBlockState);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, ControllerBlockEntity pBlockEntity) {

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
    protected BlockPattern getBlockPattern() {
        return BlockPatternBuilder.start()
                .where('a', ControllerBlockEntity.blockMatch(BlockRegistry.CENTRIFUGE.get()))
                .where('b', ControllerBlockEntity.blockMatch(BlockRegistry.ENERGY_BUFFER.get()))
                .where('c', ControllerBlockEntity.blockMatch(BlockRegistry.ITEM_BUFFER.get()))
                .where('d', ControllerBlockEntity.blockMatch(BlockRegistry.SHAFT.get()))
                .where('e', ControllerBlockEntity.blockMatch(Blocks.IRON_BARS))
                .where('f', ControllerBlockEntity.blockMatch(Blocks.GLASS_PANE))
                .where('g', ControllerBlockEntity.blockMatch(Blocks.IRON_BLOCK))
                .where('h', ControllerBlockEntity.stateMatch(BlockRegistry.FRAME.get().defaultBlockState().setValue(FrameBlock.PLATES, true).setValue(FrameBlock.WIRES, true)))
                .where('*', blockInWorld -> blockInWorld.getState().isAir())
                .aisle("hhh", "gfg", "hah")
                .aisle("hdh", "fef", "cdc")
                .aisle("hhh", "gfg", "hbh")
                .build();
    }

    @Override
    protected Vec3i getPatternOffset() {
        return new Vec3i(-1, 2, 0);
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
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandlerOptional.ifPresent(itemHandler -> itemHandler.deserializeNBT(pTag));
        energyStorageOptional.ifPresent(energyStorage -> energyStorage.deserializeNBT(pTag));

    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        itemHandlerOptional.ifPresent(itemHandler -> tag.put(SynchromaItemHandler.TAG_KEY, itemHandler.serializeNBT()));
        energyStorageOptional.ifPresent(energyStorage -> tag.put(SynchromaEnergyStorage.TAG_KEY, energyStorage.serializeNBT()));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap.equals(ForgeCapabilities.ITEM_HANDLER) && (side == null || side == Direction.DOWN)) {
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
