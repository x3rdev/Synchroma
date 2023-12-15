package com.github.x3r.synchroma.common.item;

import com.github.x3r.synchroma.common.cutscene.ServerCutsceneManager;
import com.github.x3r.synchroma.common.cutscene.TestCutscene;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class TestCutsceneItem extends Item {

    public TestCutsceneItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide()) {
            ServerPlayer serverPlayer = (ServerPlayer) pPlayer;
            if (pPlayer.isCrouching()) {
                ServerCutsceneManager.getInstance().startCutscene(serverPlayer, new TestCutscene(serverPlayer));
            } else {
                ServerCutsceneManager.getInstance().exitCutscene(serverPlayer);
            }
        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pUsedHand), pLevel.isClientSide());
    }
}
