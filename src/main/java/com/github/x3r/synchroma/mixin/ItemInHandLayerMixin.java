package com.github.x3r.synchroma.mixin;

import com.github.x3r.synchroma.common.capability.CyberwareCapability;
import com.github.x3r.synchroma.common.item.cyberware.ImplantLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
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

@Mixin(ItemInHandLayer.class)
public abstract class ItemInHandLayerMixin {


    @Shadow protected abstract void renderArmWithItem(LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight);

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"))
    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, LivingEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        if(pLivingEntity instanceof Player) {
            pMatrixStack.pushPose();
            pLivingEntity.getCapability(CyberwareCapability.INSTANCE).ifPresent(cyberwareCapability -> {
                for (ImplantLocation location : ImplantLocation.values()) {
                    ItemStack[] cyberware = cyberwareCapability.getImplants(location);
                    for (ItemStack stack : cyberware) {
                        if (!stack.isEmpty()) {
                            this.renderArmWithItem(pLivingEntity, stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, pMatrixStack, pBuffer, pPackedLight);
                        }
                    }
                }
            });
            pMatrixStack.popPose();
        }
    }
}
