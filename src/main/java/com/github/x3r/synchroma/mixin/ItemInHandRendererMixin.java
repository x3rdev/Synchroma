package com.github.x3r.synchroma.mixin;

import com.github.x3r.synchroma.common.capability.CyberwareCapability;
import com.github.x3r.synchroma.common.item.cyberware.CyberwareItem;
import com.github.x3r.synchroma.common.item.cyberware.ImplantLocation;
import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Inject(method = "renderHandsWithItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endBatch()V"))
    public void renderHandsWithItems(float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight, CallbackInfo ci) {
        pPoseStack.pushPose();
        pPlayerEntity.getCapability(CyberwareCapability.INSTANCE).ifPresent(cyberwareCapability -> {
            for (ImplantLocation location : ImplantLocation.values()) {
                ItemStack[] cyberware = cyberwareCapability.getImplants(location);
                for (ItemStack stack : cyberware) {
                    if(stack.getItem() instanceof CyberwareItem cyberwareItem) {
                        cyberwareItem.renderCyberwareFirstPerson((ItemInHandRenderer) ((Object) this), stack, pPartialTicks, pPoseStack, pBuffer, pPlayerEntity, pCombinedLight);
                    }
                }
            }
        });
        pPoseStack.popPose();
    }

}
