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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;

import java.util.HashSet;
import java.util.Set;

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

    public abstract BlockState[][][] getBlockPattern();

    @Override
    public void onLoad() {
        super.onLoad();
        validateMultiBlock(null);
    }

    public boolean isAssembled() {
        return this.getBlockState().getValue(PartBlock.ASSEMBLED);
    }

    public boolean validateMultiBlock(@Nullable ServerPlayer player) {
        BlockState[][][] pattern = getBlockPattern();
        if(pattern != null) {
            BlockPos incorrectPositions = validatePattern(pattern, getLevel(), getBlockPos(), getRotation());
            if (incorrectPositions == null) {
                if(!isAssembled()) {
                    assemble(player);
                }
                return true;
            }
        }
        if(isAssembled()) {
            disassemble();
        }
        return false;
    }

    private static @Nullable BlockPos validatePattern(BlockState[][][] pattern, Level level, BlockPos controllerPos, Rotation rot) {
        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[0].length; j++) {
                for (int k = 0; k < pattern[0][0].length; k++) {
                    BlockPos pos = new BlockPos(getControllerOffset(pattern).offset(i, j, k)).rotate(rot);
                    if(!validBlock(level, pattern[i][j][k], controllerPos.offset(pos))) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    public static Vec3i getControllerOffset(BlockState[][][] pattern) {
        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[0].length; j++) {
                for (int k = 0; k < pattern[0][0].length; k++) {
                    if(pattern[i][j][k] != null && pattern[i][j][k].getBlock() instanceof ControllerBlock) {
                        return new Vec3i(-i, -j, -k);
                    }
                }
            }
        }
        throw new RuntimeException("Can not find controller in pattern");
    }

    private static boolean validBlock(Level level, BlockState expected, BlockPos pos) {
        if(expected == null) {
            return true;
        }
        BlockState actual = level.getBlockState(pos);
        if(level.getBlockEntity(pos) instanceof MimicBlockEntity mimicBlock) {
            actual = mimicBlock.getOriginalState();
        }
        if(actual.equals(expected)) {
            return true;
        }
        if(expected.equals(expected.getBlock().defaultBlockState())) {
            return actual.getBlock().equals(expected.getBlock());
        }
        return false;
    }

    private static Set<BlockPos> getPatternPositions(BlockState[][][] pattern, BlockPos controllerPos, Rotation rot) {
        Set<BlockPos> set = new HashSet<>();
        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[0].length; j++) {
                for (int k = 0; k < pattern[0][0].length; k++) {
                    if(pattern[i][j][k] != null) {
                        BlockPos pos = new BlockPos(getControllerOffset(pattern).offset(i, j, k)).rotate(rot);
                        set.add(controllerPos.offset(pos));
                    }
                }
            }
        }
        return set;
    }

    public void assemble(ServerPlayer player) {
        if(!this.isAssembled()){
            getPatternPositions(getBlockPattern(), getBlockPos(), getRotation()).forEach(pos -> {
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
            getPatternPositions(getBlockPattern(), getBlockPos(), getRotation()).forEach(pos -> {
                if(this.level.getBlockEntity(pos) instanceof PartBlockEntity partEntity) {
                    partEntity.disassemble();
                }
            });
            this.level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(PartBlock.ASSEMBLED, false));
        }
        cachedParts.clear();
        markUpdated();
    }

    //TODO make this more accurate to pattern
    @Override
    public AABB getRenderBoundingBox() {
        return super.getRenderBoundingBox().inflate(4);
    }

    private Rotation getRotation() {
        Direction direction = getBlockState().getValue(ControllerBlock.FACING);
        switch (direction) {
            case NORTH -> {
                return Rotation.NONE;
            }
            case EAST -> {
                return Rotation.CLOCKWISE_90;
            }
            case SOUTH -> {
                return Rotation.CLOCKWISE_180;
            }
            case WEST -> {
                return Rotation.COUNTERCLOCKWISE_90;
            }
        }
        return Rotation.NONE;
    }
}
