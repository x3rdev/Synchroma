package com.github.x3r.synchroma.common.item.guns;

import com.github.x3r.synchroma.client.renderer.item.HunterRifleRenderer;
import com.github.x3r.synchroma.common.item.modification.GunModification;
import com.github.x3r.synchroma.common.registry.GunModificationRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ItemStack;
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
