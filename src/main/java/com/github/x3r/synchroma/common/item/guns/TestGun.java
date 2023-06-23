package com.github.x3r.synchroma.common.item.guns;

import com.github.x3r.synchroma.common.entity.BulletEntity;
import com.github.x3r.synchroma.common.item.bullets.SynchromaBullet;
import com.github.x3r.synchroma.common.registry.ItemRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TestGun extends Item implements SynchromaGun {

    public TestGun(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        ItemStack ammoStack = new ItemStack(ItemRegistry.TEST_BULLET.get(), 1);
        BulletEntity bullet = new BulletEntity(pLevel, pPlayer, (SynchromaGun) itemStack.getItem(), (SynchromaBullet) ammoStack.getItem());
        if(!pLevel.isClientSide()) {
            bullet.shoot();
            pLevel.addFreshEntity(bullet);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

}
