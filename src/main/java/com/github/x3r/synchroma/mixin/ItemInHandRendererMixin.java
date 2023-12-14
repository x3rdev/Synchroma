package com.github.x3r.synchroma.mixin;

import com.github.x3r.synchroma.common.capability.CyberwareCapability;
import com.github.x3r.synchroma.common.item.cyberware.ImplantLocation;
import com.github.x3r.synchroma.common.packet.SyncCyberwarePacket;
import com.github.x3r.synchroma.common.registry.ItemRegistry;
import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Shadow protected abstract void renderPlayerArm(PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, float pEquippedProgress, float pSwingProgress, HumanoidArm pSide);

    @Shadow public abstract void renderItem(LivingEntity pEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, boolean pLeftHand, PoseStack pPoseStack, MultiBufferSource pBuffer, int pSeed);

    @Shadow protected abstract void applyItemArmTransform(PoseStack pPoseStack, HumanoidArm pHand, float pEquippedProg);

    @Shadow protected abstract void applyItemArmAttackTransform(PoseStack pPoseStack, HumanoidArm pHand, float pSwingProgress);

    @Shadow protected abstract void renderArmWithItem(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight);

    @Shadow private float oMainHandHeight;

    @Shadow private float mainHandHeight;

    @Inject(method = "renderHandsWithItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endBatch()V"))
    public void renderHandsWithItems(float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight, CallbackInfo ci) {
        float f = pPlayerEntity.getAttackAnim(pPartialTicks);
        InteractionHand interactionhand = MoreObjects.firstNonNull(pPlayerEntity.swingingArm, InteractionHand.MAIN_HAND);
        float f1 = Mth.lerp(pPartialTicks, pPlayerEntity.xRotO, pPlayerEntity.getXRot());
        float f4 = interactionhand == InteractionHand.MAIN_HAND ? f : 0.0F;
        float f5 = 1.0F - Mth.lerp(pPartialTicks, this.oMainHandHeight, this.mainHandHeight);
        pPlayerEntity.getCapability(CyberwareCapability.INSTANCE).ifPresent(cyberwareCapability -> {
            for (ImplantLocation location : ImplantLocation.values()) {
                ItemStack[] cyberware = cyberwareCapability.getImplants(location);
                for (ItemStack stack : cyberware) {
                    if(!stack.isEmpty()) {
                        this.renderArmWithItem(pPlayerEntity, pPartialTicks, f1, InteractionHand.MAIN_HAND, f4, stack, f5, pPoseStack, pBuffer, pCombinedLight);
                    }
                }
            }
        });
    }

}
