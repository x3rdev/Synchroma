package com.github.x3r.synchroma.common.block.pipes.energy_pipe;

import com.github.x3r.synchroma.common.block.pipes.BasePipeBlockEntity;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.common.tag.SynchromaBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import oshi.util.tuples.Pair;

import java.util.function.Predicate;

public class EnergyPipeBlockEntity extends BasePipeBlockEntity {
    public EnergyPipeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.ENERGY_PIPE.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, EnergyPipeBlockEntity pBlockEntity) {
        Pair<Direction, BlockPos> source = pBlockEntity.findSource();
        Pair<Direction, BlockPos> endpoint = pBlockEntity.findEndpoint();
        if(source != null && endpoint != null) {
            pLevel.getBlockEntity(source.getB()).getCapability(ForgeCapabilities.ENERGY, source.getA().getOpposite()).ifPresent(sourceStorage -> {
                pLevel.getBlockEntity(endpoint.getB()).getCapability(ForgeCapabilities.ENERGY, endpoint.getA().getOpposite()).ifPresent(endpointStorage -> {
                    endpointStorage.receiveEnergy(sourceStorage.extractEnergy(1000, false), false);
                    System.out.println(endpointStorage.getEnergyStored());
                });
            });
        }
    }


    @Override
    public Predicate<Direction> isValidSource() {
        return direction -> {
            BlockEntity be = level.getBlockEntity(getBlockPos().relative(direction));
            if(be != null && be.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).isPresent()) {
                IEnergyStorage storage = be.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).orElse(null);
                return storage.canExtract() && storage.getEnergyStored() > 0;
            }
            return false;
        };
    }

    @Override
    public Predicate<Direction> isValidEndpoint() {
        return direction -> {
            BlockEntity be = level.getBlockEntity(getBlockPos().relative(direction));
            if(be != null && be.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).isPresent()) {
                IEnergyStorage storage = be.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).orElse(null);
                return storage.canReceive() && storage.getEnergyStored() < storage.getMaxEnergyStored();
            }
            return false;
        };
    }

    @Override
    public Predicate<Direction> isValidPipe() {
        return direction -> level.getBlockState(getBlockPos().relative(direction)).is(SynchromaBlockTags.ENERGY_PIPE);
    }
}
