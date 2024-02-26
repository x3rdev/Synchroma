package com.github.x3r.synchroma.common.item.cyberware;

import com.github.x3r.synchroma.client.renderer.cyberware.ThermalBladeRenderer;
import com.github.x3r.synchroma.client.renderer.item.CrowbarRenderer;
import com.github.x3r.synchroma.common.capability.CyberwareCapability;
import com.github.x3r.synchroma.common.packet.SyncCyberwarePacket;
import com.github.x3r.synchroma.common.packet.SynchromaPacketHandler;
import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
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

    public static final String EXTENDED = "extended";

    public ThermalBladeItem(Properties pProperties) {
        super(pProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void renderCyberwareFirstPerson(ItemInHandRenderer itemInHandRenderer, ItemStack stack, float partialTicks, PoseStack poseStack, MultiBufferSource.BufferSource buffer, LocalPlayer player, int combinedLight) {
        float f = player.getAttackAnim(partialTicks);
        InteractionHand interactionhand = MoreObjects.firstNonNull(player.swingingArm, InteractionHand.MAIN_HAND);
        float f1 = Mth.lerp(partialTicks, player.xRotO, player.getXRot());
        float f4 = interactionhand == InteractionHand.MAIN_HAND ? f : 0.0F;
        float f5 = 1.0F - Mth.lerp(partialTicks, itemInHandRenderer.oMainHandHeight, itemInHandRenderer.mainHandHeight);
        itemInHandRenderer.renderArmWithItem(player, partialTicks, f1, InteractionHand.MAIN_HAND, f4, stack, f5, poseStack, buffer, combinedLight);
    }

    @Override
    public void renderCyberwareThirdPerson(ItemInHandLayer<?,?> layer, LivingEntity livingEntity, ItemStack stack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        layer.renderArmWithItem(livingEntity, stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, buffer, packedLight);

    }

    @Override
    public void cyberwareTick(ItemStack stack, ServerLevel level, ServerPlayer player, ImplantLocation implantLocation, int slot) {
        if(!isExtended(stack) && player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            setExtended(stack, true);
            player.getCapability(CyberwareCapability.INSTANCE).ifPresent(cyberwareCapability -> cyberwareCapability.onChanged(player));
            triggerAnim(player, GeoItem.getOrAssignId(stack, level), "controller", "extend");
        }
        if(isExtended(stack) && !player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            setExtended(stack, false);
            player.getCapability(CyberwareCapability.INSTANCE).ifPresent(cyberwareCapability -> cyberwareCapability.onChanged(player));
            triggerAnim(player, GeoItem.getOrAssignId(stack, level), "controller", "retract");
        }
    }

    @Override
    public void onLeftClickEmpty(ItemStack stack) {
        super.onLeftClickEmpty(stack);
    }

    @Override
    public ImplantLocation[] getImplantLocation() {
        return new ImplantLocation[]{ImplantLocation.RIGHT_ARM, ImplantLocation.LEFT_ARM};
    }

    public static boolean isExtended(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if(!tag.contains(EXTENDED)) tag.putBoolean(EXTENDED, false);
        return tag.getBoolean(EXTENDED);
    }

    public static void setExtended(ItemStack stack, boolean b) {
        stack.getOrCreateTag().putBoolean(EXTENDED, b);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 3, animationState -> PlayState.STOP)
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
