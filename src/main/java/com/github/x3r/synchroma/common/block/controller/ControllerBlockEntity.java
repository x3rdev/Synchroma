package com.github.x3r.synchroma.common.block.controller;

import com.github.x3r.synchroma.common.block.multiblock.MultiBlockPartEntity;
import com.github.x3r.synchroma.common.item.circuit.CircuitItem;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.common.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.ContainerSingleItem;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;

public class ControllerBlockEntity extends BlockEntity implements GeoBlockEntity, Clearable, ContainerSingleItem {
    private final NonNullList<ItemStack> items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Set<BlockEntity> cachedParts = new HashSet<>();

    public ControllerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.CONTROLLER.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ControllerBlockEntity pBlockEntity) {
        if(pBlockEntity.getFirstItem().getItem() instanceof CircuitItem circuit) {
            circuit.tickMB(pLevel, pPos, pState, pBlockEntity);
        }
    }

    public void use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(this.getFirstItem().getItem() instanceof CircuitItem circuit) {
            circuit.useMB(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.items.clear();
        ContainerHelper.loadAllItems(pTag, this.items);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ContainerHelper.saveAllItems(pTag, this.items, true);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = super.getUpdateTag();
        saveAdditional(compoundtag);
        return compoundtag;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return this.items.get(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        ItemStack itemstack = Objects.requireNonNullElse(this.items.get(pSlot), ItemStack.EMPTY);
        this.items.set(pSlot, ItemStack.EMPTY);
        return itemstack;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        if(pStack.getItem() instanceof CircuitItem) {
            this.items.set(pSlot, pStack);
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }
    public void popOutCircuit() {
        markUpdated();
        if (this.level != null && !this.level.isClientSide) {
            BlockPos blockpos = this.getBlockPos();
            ItemStack itemstack = this.getFirstItem();
            if (!itemstack.isEmpty()) {
                this.removeFirstItem();
                Vec3 vec3 = Vec3.atLowerCornerWithOffset(blockpos, 0.5D, 1.01D, 0.5D).offsetRandom(this.level.random, 0.35F);
                ItemStack itemstack1 = itemstack.copy();
                ItemEntity itementity = new ItemEntity(this.level, vec3.x(), vec3.y(), vec3.z(), itemstack1);
                itementity.setDefaultPickUpDelay();
                this.level.addFreshEntity(itementity);
            }
        }
    }

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }
    @Nullable
    private BlockPattern getBlockPattern() {
        ItemStack stack = this.getFirstItem();
        if(!stack.isEmpty() && stack.getItem() instanceof CircuitItem circuit) {
            return circuit.getPattern();
        }
        return null;
    }

    @Override
    public void onLoad() {
        validateMultiBlock();
        super.onLoad();
    }

    public boolean isAssembled() {
        return this.getBlockState().getValue(ControllerBlock.ASSEMBLED);
    }

    public boolean validateMultiBlock() {
        BlockPattern pattern = getBlockPattern();
        if(pattern != null) {
            BlockPattern.BlockPatternMatch match = pattern.matches(getBlockPos(), this.getBlockState().getValue(ControllerBlock.FACING).getOpposite(), Direction.UP, BlockPattern.createLevelCache(getLevel(), false));
            if (match != null) {
                assemble();
                return true;
            }
        }
        disassemble();
        return false;
    }

    private void assemble() {
        if(!this.isAssembled()){
            getMultiBlockParts().forEach(pos -> {
                BlockState state = this.level.getBlockState(pos);
                if(!(state.getBlock() instanceof ControllerBlock || state.isAir())) {
                    this.level.setBlockAndUpdate(pos, BlockRegistry.MULTI_BLOCK_PART.get().defaultBlockState());
                    MultiBlockPartEntity partEntity = ((MultiBlockPartEntity) this.level.getBlockEntity(pos));
                    partEntity.setControllerPos(this.getBlockPos());
                    partEntity.setOriginalState(state);
                }
            });
            if(this.level.getBlockState(getBlockPos()).is(this.getBlockState().getBlock())) {
                this.level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(ControllerBlock.ASSEMBLED, true));
            }
        }
        markUpdated();
    }

    public void disassemble() {
        if(this.isAssembled()) {
            getMultiBlockParts().forEach(pos -> {
                if(this.level.getBlockEntity(pos) instanceof MultiBlockPartEntity partEntity) {
                    this.level.setBlockAndUpdate(pos, partEntity.getOriginalState());
                }
            });
            if(this.level.getBlockState(getBlockPos()).is(this.getBlockState().getBlock())) {
                this.level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(ControllerBlock.ASSEMBLED, false));
            }
        }
        cachedParts.clear();
        markUpdated();
    }

    //TODO check if useful
    private Set<BlockEntity> getMultiBlockBlockEntities() {
        if(cachedParts.isEmpty()) {
            getMultiBlockParts().forEach(pos -> {
                if(!(level.getBlockEntity(pos) instanceof ControllerBlockEntity)) cachedParts.add(level.getBlockEntity(pos));
            });
        }
        return cachedParts;
    }

    private Set<BlockPos> getMultiBlockParts() {
        BlockPattern pattern = getBlockPattern();
        Set<BlockPos> set = new HashSet<>();
        if(pattern != null) {
            for (int i = 0; i < pattern.getWidth(); i++) {
                for (int j = 0; j < pattern.getHeight(); j++) {
                    for (int k = 0; k < pattern.getDepth(); k++) {
                        double rot = Math.toRadians(this.getBlockState().getValue(ControllerBlock.FACING).toYRot());
                        set.add(getBlockPos().offset(new Vec3i((int) Math.round(Math.cos(rot)*i-Math.sin(rot)*-k), -j, (int) Math.round(Math.sin(rot)*i+Math.cos(rot)*-k))));
                    }
                }
            }
        }
        return set;
    }

    //TODO make this more accurate to pattern
    @Override
    public AABB getRenderBoundingBox() {
        return super.getRenderBoundingBox().inflate(4);
    }
}
