package com.github.x3r.synchroma.common.block.wind_turbine;

import com.github.x3r.synchroma.client.menu.CentrifugeMenu;
import com.github.x3r.synchroma.client.menu.WindTurbineMenu;
import com.github.x3r.synchroma.common.block.FrameBlock;
import com.github.x3r.synchroma.common.block.SynchromaEnergyStorage;
import com.github.x3r.synchroma.common.block.SynchromaItemHandler;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlockEntity;
import com.github.x3r.synchroma.common.block.solar_panel.AdvancedSolarPanelBlockEntity;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
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

public class WindTurbineBlockEntity extends ControllerBlockEntity {

    public static final RawAnimation ASSEMBLE_ANIM = RawAnimation.begin().thenPlay("animation.wind_turbine.assemble");
    public final Map<String, BoneSnapshot> boneSnapshots = new HashMap<>();
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final LazyOptional<SynchromaItemHandler> itemHandlerOptional = LazyOptional.of(() -> new SynchromaItemHandler(3));
    private final LazyOptional<SynchromaEnergyStorage> energyStorageOptional = LazyOptional.of(() -> new SynchromaEnergyStorage(1000, 0, 20000));


    public WindTurbineBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.WIND_TURBINE.get(), pPos, pBlockState);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, WindTurbineBlockEntity pBlockEntity) {
        if(pBlockEntity.isAssembled()) {
            if (pLevel.canSeeSky(pPos.above())) {
                pBlockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> {
                    ((SynchromaEnergyStorage) iEnergyStorage).forceReceiveEnergy(200 * pPos.getY()/pLevel.getMaxBuildHeight(), false);
                });
            }
            pBlockEntity.markUpdated();
        }
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
        BlockState a = BlockRegistry.WIND_TURBINE.get().defaultBlockState();
        BlockState b = BlockRegistry.ENERGY_BUFFER.get().defaultBlockState();
        BlockState c = BlockRegistry.FRAME.get().defaultBlockState().setValue(FrameBlock.PLATES, true).setValue(FrameBlock.WIRES, true);
        BlockState d = BlockRegistry.SHAFT.get().defaultBlockState();
        BlockState e = Blocks.STONE_BRICK_WALL.defaultBlockState();
        BlockState f = Blocks.SMOOTH_STONE_SLAB.defaultBlockState();
        return new BlockState[][][]{
                {{null, null, null, null, null},{null, null, null, null, null},{null, null, null, null, null},{null, null, f, null, null},   {null, null, c, null, null},{null, null, f, null, null}},
                {{null, null, null, null, null},{null, null, null, null, null},{null, null, null, null, null},{null, null, null, null, null},{null, null, e, null, null},{null, null, null, null, null}},
                {{null, null, b, null, null},   {null, null, a, null, null},   {null, null, c, null, null},   {f, null, c, null, f},         {c, e, d, e, c},            {f, null, c, null, f}},
                {{null, null, null, null, null},{null, null, null, null, null},{null, null, null, null, null},{null, null, null, null, null},{null, null, e, null, null},{null, null, null, null, null}},
                {{null, null, null, null, null},{null, null, null, null, null},{null, null, null, null, null},{null, null, f, null, null},   {null, null, c, null, null},{null, null, f, null, null}}
        };
    }

    @Override
    public void assemble(ServerPlayer player) {
        super.assemble(player);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("machine.wind_turbine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new WindTurbineMenu(pContainerId, pPlayerInventory, this);
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
