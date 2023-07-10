package com.github.x3r.synchroma.common.block;

import com.github.x3r.synchroma.common.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class FrameBlock extends Block {

    public static final BooleanProperty WIRES = BooleanProperty.create("wires");
    public static final BooleanProperty PLATES = BooleanProperty.create("plates");

    public FrameBlock(Properties pProperties) {
        super(pProperties.noOcclusion().isViewBlocking((pState, pLevel, pPos) -> false));
        this.registerDefaultState(this.getStateDefinition().any().setValue(WIRES, false).setValue(PLATES, false));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack main = pPlayer.getItemInHand(pHand);
        ItemStack off = pPlayer.getItemInHand(InteractionHand.values()[pHand.ordinal() == 0 ? 1 : 0]);
        boolean wires = pState.getValue(WIRES);
        boolean plates = pState.getValue(PLATES);
        if(main.is(ItemRegistry.WIRES.get()) && !wires && !plates) {
            main.shrink(1);
            pLevel.setBlockAndUpdate(pPos, pState.setValue(WIRES, true));
            return InteractionResult.sidedSuccess(pLevel.isClientSide());
        }
        if(main.is(ItemRegistry.WELDING_GUN.get()) && off.is(ItemRegistry.TITANIUM_PLATES.get()) && wires && !plates) {
            off.shrink(1);
            pLevel.setBlockAndUpdate(pPos, pState.setValue(PLATES, true));
            return InteractionResult.sidedSuccess(pLevel.isClientSide());
        }

        if(main.is(ItemRegistry.WIRE_CUTTERS.get()) && wires && !plates){
            popOutItem(pLevel, pPos, new ItemStack(ItemRegistry.WIRES.get()));
            pLevel.setBlockAndUpdate(pPos, pState.setValue(WIRES, false));
            return InteractionResult.sidedSuccess(pLevel.isClientSide());
        }
        if(main.is(ItemRegistry.CROWBAR.get()) && wires && plates){
            popOutItem(pLevel, pPos, new ItemStack(ItemRegistry.TITANIUM_PLATES.get()));
            pLevel.setBlockAndUpdate(pPos, pState.setValue(PLATES, false));
            return InteractionResult.sidedSuccess(pLevel.isClientSide());
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(WIRES);
        pBuilder.add(PLATES);
    }

    private void popOutItem(Level level, BlockPos pos, ItemStack stack) {
        if (level != null && !level.isClientSide) {
            if (!stack.isEmpty()) {
                Vec3 vec3 = Vec3.atLowerCornerWithOffset(pos, 0.5D, 1.01D, 0.5D).offsetRandom(level.random, 0.35F);
                ItemEntity itementity = new ItemEntity(level, vec3.x(), vec3.y(), vec3.z(), stack);
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            }
        }
    }

}
