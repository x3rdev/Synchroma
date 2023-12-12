package com.github.x3r.synchroma.common.item.cyberware;

import com.github.x3r.synchroma.client.renderer.cyberware.ThermalBladeRenderer;
import com.github.x3r.synchroma.client.renderer.item.CrowbarRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class ThermalBladeItem extends CyberwareItem implements GeoItem {

    public static final RawAnimation EXTEND_ANIM = RawAnimation.begin().thenPlay("animation.thermal_blade.extend");
    public static final RawAnimation RETRACT_ANIM = RawAnimation.begin().thenPlay("animation.thermal_blade.retract");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ThermalBladeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ImplantLocation[] getImplantLocation() {
        return new ImplantLocation[]{ImplantLocation.RIGHT_ARM, ImplantLocation.LEFT_ARM};
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, animationState -> PlayState.STOP)
                .triggerableAnim("extend", EXTEND_ANIM)
                .triggerableAnim("retract", RETRACT_ANIM));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            private ThermalBladeRenderer<ThermalBladeItem> renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new ThermalBladeRenderer<>();

                return this.renderer;
            }
        });
    }
}
