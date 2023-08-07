package com.github.x3r.synchroma.common.block.multiblock;

import com.github.x3r.synchroma.common.block.SynchromaBlockEntity;
import com.github.x3r.synchroma.common.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public abstract class ControllerBlockEntity extends SynchromaBlockEntity implements GeoBlockEntity {
    private final Set<BlockEntity> cachedParts = new HashSet<>();

    protected ControllerBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    protected abstract BlockPattern getBlockPattern();
    protected abstract Vec3i getPatternOffset();

    @Override
    public void onLoad() {
        validateMultiBlock(null);
        super.onLoad();
    }

    public boolean isAssembled() {
        return this.getBlockState().getValue(PartBlock.ASSEMBLED);
    }

    public boolean validateMultiBlock(@Nullable ServerPlayer player) {
        BlockPattern pattern = getBlockPattern();
        if(pattern != null) {
            BlockPattern.BlockPatternMatch match = pattern.matches(getBlockPos().offset(rotateVector(getPatternOffset())), this.getBlockState().getValue(ControllerBlock.FACING).getOpposite(), Direction.UP, BlockPattern.createLevelCache(getLevel(), false));
            if (match != null) {
                if(!isAssembled()) {
                    assemble(player);
                }
                return true;
            }
        }
        if(this.isAssembled()) {
            disassemble();
        }
        return false;
    }

    public void assemble(ServerPlayer player) {
        if(!this.isAssembled()){
            getMultiBlockParts().forEach(pos -> {
                BlockState state = this.level.getBlockState(pos);
                if(state.isAir() || state.getBlock() instanceof ControllerBlock) {
                    return;
                }
                if(level.getBlockEntity(pos) instanceof PartBlockEntity partBlockEntity) {
                    partBlockEntity.disassemble();
                    partBlockEntity.assemble(this.getBlockPos());
                    return;
                }
                this.level.setBlockAndUpdate(pos, BlockRegistry.MIMIC.get().defaultBlockState());
                MimicBlockEntity mimicBlockEntity = ((MimicBlockEntity) this.level.getBlockEntity(pos));
                mimicBlockEntity.setOriginalState(state);
                mimicBlockEntity.assemble(this.getBlockPos());
            });
            if(this.level.getBlockState(getBlockPos()).is(this.getBlockState().getBlock())) {
                this.level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(PartBlock.ASSEMBLED, true));
            }
        }
        markUpdated();
    }

    public void disassemble() {
        if(this.isAssembled()) {
            getMultiBlockParts().forEach(pos -> {
                if(this.level.getBlockEntity(pos) instanceof PartBlockEntity partEntity) {
                    partEntity.disassemble();
                }
            });
            this.level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(PartBlock.ASSEMBLED, false));
        }
        cachedParts.clear();
        markUpdated();
    }

    //TODO check if useful
    protected Set<BlockEntity> getMultiBlockBlockEntities() {
        if(cachedParts.isEmpty()) {
            getMultiBlockParts().forEach(pos -> {
                if(!(level.getBlockEntity(pos) instanceof ControllerBlockEntity)) cachedParts.add(level.getBlockEntity(pos));
            });
        }
        return cachedParts;
    }

    protected Set<BlockPos> getMultiBlockParts() {
        BlockPattern pattern = getBlockPattern();
        Set<BlockPos> set = new HashSet<>();
        if(pattern != null) {
            for (int i = 0; i < pattern.getWidth(); i++) {
                for (int j = 0; j < pattern.getHeight(); j++) {
                    for (int k = 0; k < pattern.getDepth(); k++) {
                        set.add(getBlockPos()
                                .offset(rotateVector(getPatternOffset()))
                                .offset(rotateVector(new Vec3i(i, -j, -k))));
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

    public Vec3i rotateVector(Vec3i vec) {
        double rot = Math.toRadians(this.getBlockState().getValue(ControllerBlock.FACING).toYRot());
        return new Vec3i((int) Math.round(Math.cos(rot)*vec.getX()-Math.sin(rot)*vec.getZ()), vec.getY(), (int) Math.round(Math.sin(rot)*vec.getX()+Math.cos(rot)*vec.getZ()));
    }

    public static Predicate<BlockInWorld> blockMatch(Block block) {
        return blockInWorld -> {
            if(blockInWorld.getState().is(block)) return true;
            if(blockInWorld.getEntity() instanceof MimicBlockEntity partEntity) {
                return partEntity.getOriginalState().is(block);
            }
            return false;
        };
    }

    public static Predicate<BlockInWorld> stateMatch(BlockState state) {
        return blockInWorld -> {
            if(blockInWorld.getState().equals(state)) return true;
            if(blockInWorld.getEntity() instanceof MimicBlockEntity partEntity) {
                return partEntity.getOriginalState().equals(state);
            }
            return false;
        };
    }
}
