package com.github.x3r.synchroma.common.block.wind_turbine;

import com.github.x3r.synchroma.common.block.centrifuge.CentrifugeBlockEntity;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class WindTurbineBlock extends ControllerBlock {

    public WindTurbineBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void openContainer(Level pLevel, BlockPos pPos, Player pPlayer) {
        NetworkHooks.openScreen((ServerPlayer) pPlayer, (MenuProvider)pLevel.getBlockEntity(pPos), buf -> buf.writeBlockPos(pPos));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new WindTurbineBlockEntity(pPos, pState);
    }
}
