package com.github.x3r.synchroma.common.item;

import com.github.x3r.synchroma.client.renderer.item.CrowbarRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class GeckoBlockItem extends BlockItem implements GeoItem {

    private final BlockEntityWithoutLevelRenderer renderer;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public GeckoBlockItem(Block pBlock, BlockEntityWithoutLevelRenderer renderer, Properties pProperties) {
        super(pBlock, pProperties);
        this.renderer = renderer;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {

        consumer.accept(new IClientItemExtensions() {

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
