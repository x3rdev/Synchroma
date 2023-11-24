package com.github.x3r.synchroma.common.block.surgeon;

import com.github.x3r.synchroma.common.block.multiblock.ControllerBlock;
import com.github.x3r.synchroma.common.block.multiblock.PartBlock;
import com.github.x3r.synchroma.common.entity.RideableEntity;
import com.github.x3r.synchroma.common.registry.BlockEntityRegistry;
import com.github.x3r.synchroma.common.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class SurgeonBlock extends ControllerBlock {

    public SurgeonBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void openContainer(Level pLevel, BlockPos pPos, Player pPlayer) {
        NetworkHooks.openScreen((ServerPlayer) pPlayer, (MenuProvider)pLevel.getBlockEntity(pPos), buf -> buf.writeBlockPos(pPos));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if( pState.getValue(PartBlock.ASSEMBLED)) {
            RideableEntity rideable = new RideableEntity(pLevel);
            SurgeonBlockEntity surgeonBlockEntity = (SurgeonBlockEntity) pLevel.getBlockEntity(pPos);
            if(surgeonBlockEntity != null) {
                if(!pLevel.isClientSide()) {
                    BlockState state = surgeonBlockEntity.getBlockState();
                    rideable.setPos(Vec3.atCenterOf(pPos).add(offset(state)));
                    rideable.setYRot(state.getValue(ControllerBlock.FACING).toYRot());
                    pLevel.addFreshEntity(rideable);
                    pPlayer.setPos(rideable.getPosition(0));
                    pPlayer.startRiding(rideable, true);
                    surgeonBlockEntity.triggerAnim("controller", "setup");
                } else {
                    pPlayer.startRiding(rideable);
                }
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    protected static Vec3 offset(BlockState state) {
        if(state.is(BlockRegistry.SURGEON.get())) {
            return new Vec3(1, 0, 0.34).yRot((float) Math.toRadians(180-state.getValue(ControllerBlock.FACING).toYRot()));
        }
        return Vec3.ZERO;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null : createTickerHelper(pBlockEntityType, BlockEntityRegistry.SURGEON.get(), SurgeonBlockEntity::serverTick);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SurgeonBlockEntity(pPos, pState);
    }
}
