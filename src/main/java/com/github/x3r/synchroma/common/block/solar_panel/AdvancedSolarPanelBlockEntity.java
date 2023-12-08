package com.github.x3r.synchroma.common.block.solar_panel;

import com.github.x3r.synchroma.common.menu.AdvancedSolarPanelMenu;
import com.github.x3r.synchroma.common.block.SynchromaEnergyStorage;
import com.github.x3r.synchroma.common.block.SynchromaItemHandler;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlock;
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

public class AdvancedSolarPanelBlockEntity extends ControllerBlockEntity {

    public static final RawAnimation ASSEMBLE_ANIM = RawAnimation.begin().thenPlay("animation.advanced_solar_panel.assemble");
    public final Map<String, BoneSnapshot> boneSnapshots = new HashMap<>();
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final LazyOptional<SynchromaItemHandler> itemHandlerOptional = LazyOptional.of(() -> new SynchromaItemHandler(3));
    private final LazyOptional<SynchromaEnergyStorage> energyStorageOptional = LazyOptional.of(() -> new SynchromaEnergyStorage(0, 1000, 20000));

    public AdvancedSolarPanelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.ADVANCED_SOLAR_PANEL.get(), pPos, pBlockState);
    }
    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, AdvancedSolarPanelBlockEntity pBlockEntity) {
        if(pBlockEntity.isAssembled()) {
            if (pLevel.canSeeSky(pPos.above())) {
                int time = (int) (pLevel.getDayTime() % 24000);
                float f = 1 - (Math.abs(6000 - Math.max(0, 12000 - time)) / 6000F);
                pBlockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> {
                    ((SynchromaEnergyStorage) iEnergyStorage).forceReceiveEnergy(Math.round(12 * 10 * f), false);
                });
            }
            pBlockEntity.markUpdated();
        }
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

//    @Override
//    protected BlockPattern getBlockPattern() {
//        return BlockPatternBuilder.start()
//                .where('o', ControllerBlockEntity.blockMatch(BlockRegistry.ENERGY_BUFFER.get()))
//                .where('c', ControllerBlockEntity.blockMatch(BlockRegistry.ADVANCED_SOLAR_PANEL.get()))
//                .where('t', ControllerBlockEntity.blockMatch(Blocks.IRON_TRAPDOOR))
//                .where('h', ControllerBlockEntity.blockMatch(Blocks.HOPPER))
//                .where('p', ControllerBlockEntity.blockMatch(BlockRegistry.HEX_SOLAR_PLATE.get()))
//                .where('*', blockInWorld -> blockInWorld.getState().isAir())
//                .aisle("p*p", "t*t", "*t*")
//                .aisle("p*p", "hch", "tot")
//                .aisle("p*p", "t*t", "*t*")
//                .build();
//    }


    @Override
    public BlockState[][][] getBlockPattern() {
        BlockState a = BlockRegistry.ENERGY_BUFFER.get().defaultBlockState();
        BlockState b = BlockRegistry.ADVANCED_SOLAR_PANEL.get().defaultBlockState();
        BlockState c = Blocks.IRON_TRAPDOOR.defaultBlockState();
        BlockState d = Blocks.HOPPER.defaultBlockState();
        BlockState e = BlockRegistry.HEX_SOLAR_PLATE.get().defaultBlockState();
        return new BlockState[][][]{
                {{null, c, null,},{c, d, c},{e, e, e}},
                {{c, a, c},{null, b, null},{null, null, null}},
                {{null, c, null},{c, d, c},{e, e, e}}
        };
    }

    @Override
    public void assemble(ServerPlayer player) {
        triggerAnim("assemble_controller", "assemble");
        if(getBlockState().getValue(ControllerBlock.FACING).toYRot()%180==0) {
            if(player != null) {
                player.sendSystemMessage(Component.literal("machine.solar_panel_assembly_failed"), true);
            }
            return;
        }
        super.assemble(player);
    }



    @Override
    public Component getDisplayName() {
        return Component.literal("machine.advanced_solar_panel");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new AdvancedSolarPanelMenu(pContainerId, pPlayerInventory, this);
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
