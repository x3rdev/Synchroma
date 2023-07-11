package com.github.x3r.synchroma.common.item.guns;

import com.github.x3r.synchroma.client.renderer.item.HunterRifleRenderer;
import com.github.x3r.synchroma.common.entity.BulletEntity;
import com.github.x3r.synchroma.common.item.bullets.SynchromaBullet;
import com.github.x3r.synchroma.common.item.modification.GunModification;
import com.github.x3r.synchroma.common.registry.GunModificationRegistry;
import com.github.x3r.synchroma.common.registry.ItemRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class HunterRifle extends BaseGunItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public HunterRifle(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        ItemStack ammoStack = new ItemStack(ItemRegistry.TEST_BULLET.get(), 1);
        BulletEntity bullet = new BulletEntity(pLevel, pPlayer, (BaseGunItem) itemStack.getItem(), (SynchromaBullet) ammoStack.getItem());
        if(!pLevel.isClientSide()) {
            pPlayer.getCooldowns().addCooldown(this, getDelay());
            bullet.shoot();
            pLevel.addFreshEntity(bullet);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private HunterRifleRenderer renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new HunterRifleRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    @Override
    public int getDelay() {
        return 1;
    }

    @Override
    public String[] getDefaultBones() {
        return new String[]{"default_mode", "base", "bolt", "mag"};
    }
    @Override
    public GunModification[] getModifications() {
        return new GunModification[]{
                GunModificationRegistry.DOT_SCOPE.get(),
                GunModificationRegistry.MARKING_SCOPE.get(),
                GunModificationRegistry.SIMPLE_STOCK.get(),
                GunModificationRegistry.ADVANCED_STOCK.get(),
                GunModificationRegistry.BULLET_SPEED_UPGRADE.get(),
                GunModificationRegistry.FIRE_RATE_UPGRADE.get(),
                GunModificationRegistry.MUZZLE.get(),
                GunModificationRegistry.SILENCER.get(),
                GunModificationRegistry.COUNTER.get(),
                GunModificationRegistry.LASER.get(),
                GunModificationRegistry.FLASHLIGHT.get()
        };
    }
}
