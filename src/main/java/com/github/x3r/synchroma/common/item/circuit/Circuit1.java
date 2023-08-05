package com.github.x3r.synchroma.common.item.circuit;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.FrameBlock;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlockEntity;
import com.github.x3r.synchroma.common.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.phys.BlockHitResult;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class Circuit1 extends CircuitItem {
//    private static BlockPattern pattern;
//    private static MultiBlockRenderer multiBlockRenderer;
//
    public Circuit1(Properties pProperties) {
        super(pProperties);
    }
//    @Override
//    public BlockPattern getPattern() {
//        if(pattern == null) {
//            pattern = BlockPatternBuilder.start()
//                    .where('f', CircuitItem.stateMatchesMB(BlockRegistry.FRAME.get().defaultBlockState()
//                            .setValue(FrameBlock.WIRES, true)
//                            .setValue(FrameBlock.PLATES, true)))
//                    .where('c', CircuitItem.blockMatchesMB(BlockRegistry.CONTROLLER.get()))
//                    .where('*', blockInWorld -> blockInWorld.getState().isAir())
//                    .aisle("c***", "ffff")
//                    .aisle("****", "*fff")
//                    .build();
//        }
//        return pattern;
//    }
//
//    @Override
//    public Vec3i getPatternOffset() {
//        return Vec3i.ZERO;
//    }
//
//    @Override
//    public MultiBlockRenderer getRenderer() {
//        if(multiBlockRenderer == null) {
//            multiBlockRenderer = new MultiBlockRenderer(new DefaultedBlockGeoModel(new ResourceLocation(Synchroma.MOD_ID, "weapon_workbench")));
//        }
//        return multiBlockRenderer;
//    }
//
//    @Override
//    public void useMB(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
//
//    }
//    @Override
//    public void tickMB(Level pLevel, BlockPos pPos, BlockState pState, ControllerBlockEntity pBlockEntity) {
//
//    }


}
