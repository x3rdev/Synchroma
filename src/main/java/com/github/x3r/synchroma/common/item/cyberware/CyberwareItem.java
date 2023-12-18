package com.github.x3r.synchroma.common.item.cyberware;

import com.github.x3r.synchroma.common.capability.CyberwareCapability;
import com.github.x3r.synchroma.common.packet.SyncCyberwarePacket;
import com.github.x3r.synchroma.common.packet.SynchromaPacketHandler;
import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class CyberwareItem extends Item {

    public static final String INSTALLED = "Installed";
    public static final String POS_X = "PosX";
    public static final String POS_Y = "PosY";
    public static final String POS_Z = "PosZ";
    public static final String SCALE = "Scale";
    public static final String ROT_X = "RotX";
    public static final String ROT_Y = "RotY";
    public static final String ROT_Z = "RotZ";

    protected CyberwareItem(Properties pProperties) {
        super(pProperties);
    }

    public void renderCyberwareFirstPerson(ItemInHandRenderer itemInHandRenderer, ItemStack stack, float partialTicks, PoseStack poseStack, MultiBufferSource.BufferSource buffer, LocalPlayer player, int combinedLight) {

    }

    public void renderCyberwareThirdPerson(ItemInHandLayer<?,?> layer, LivingEntity livingEntity, ItemStack stack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

    }

    public void renderCyberwareArmor(ItemStack stack) {

    }

    public void cyberwareTick(ItemStack stack, ServerLevel level, ServerPlayer player, ImplantLocation implantLocation, int slot) {

    }

    public static boolean isInstalled(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if(!tag.contains(INSTALLED)) tag.putBoolean(INSTALLED, false);
        return tag.getBoolean(INSTALLED);
    }
    public static float getPosX(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if(!tag.contains(POS_X)) tag.putFloat(POS_X, 0.0F);
        return tag.getFloat(POS_X);
    }

    public static float getPosY(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if(!tag.contains(POS_Y)) tag.putFloat(POS_Y, 0.0F);
        return tag.getFloat(POS_Y);
    }

    public static float getPosZ(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if(!tag.contains(POS_Z)) tag.putFloat(POS_Z, 0.0F);
        return tag.getFloat(POS_Z);
    }

    public static float getScale(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if(!tag.contains(SCALE)) tag.putFloat(SCALE, 1.0F);
        return tag.getFloat(SCALE);
    }

    public static float getRotationX(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if(!tag.contains(ROT_X)) tag.putFloat(ROT_X, 0.0F);
        return tag.getFloat(ROT_X);
    }

    public static float getRotationY(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if(!tag.contains(ROT_Y)) tag.putFloat(ROT_Y, 0.0F);
        return tag.getFloat(ROT_Y);
    }

    public static float getRotationZ(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if(!tag.contains(ROT_Z)) tag.putFloat(ROT_Z, 0.0F);
        return tag.getFloat(ROT_Z);
    }

    public static void setInstalled(ItemStack stack, boolean b) {
        stack.getOrCreateTag().putBoolean(INSTALLED, b);
    }

    public static void setPosX(ItemStack stack, float x) {
        stack.getOrCreateTag().putFloat(POS_X, x);
    }

    public static void setPosY(ItemStack stack, float x) {
        stack.getOrCreateTag().putFloat(POS_Y, x);
    }

    public static void setPosZ(ItemStack stack, float x) {
        stack.getOrCreateTag().putFloat(POS_Z, x);
    }

    public static void setScale(ItemStack stack, float x) {
        stack.getOrCreateTag().putFloat(SCALE, x);
    }

    public static void setRotationX(ItemStack stack, float x) {
        stack.getOrCreateTag().putFloat(ROT_X, x);
    }

    public static void setRotationY(ItemStack stack, float x) {
        stack.getOrCreateTag().putFloat(ROT_Y, x);
    }

    public static void setRotationZ(ItemStack stack, float x) {
        stack.getOrCreateTag().putFloat(ROT_Z, x);
    }
    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    public abstract ImplantLocation[] getImplantLocation();

    public void onInstall(Player player, ItemStack stack, ImplantLocation location, int slot) {
        setInstalled(stack, true);
    }
    public void onRemove(Player player, ItemStack stack, ImplantLocation location, int slot) {
        setInstalled(stack, false);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        MutableComponent c1 = Component.literal("Can be installed on [").withStyle(Style.EMPTY.withColor(0x686d6e));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getImplantLocation().length; i++) {
            if(i != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(getImplantLocation()[i].getName());
        }
        MutableComponent c2 = Component.literal(stringBuilder.toString()).withStyle(Style.EMPTY.withColor(0xFFFFFF));
        MutableComponent c3 = Component.literal("]").withStyle(Style.EMPTY.withColor(0x686d6e));
        pTooltipComponents.add(c1.append(c2).append(c3));
        if(CyberwareItem.isInstalled(pStack)) {
            pTooltipComponents.add(Component.literal("INSTALLED").withStyle(Style.EMPTY.withColor(0xa80019)));
        }
    }
}
