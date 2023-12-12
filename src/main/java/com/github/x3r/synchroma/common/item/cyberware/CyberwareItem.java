package com.github.x3r.synchroma.common.item.cyberware;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
    public static final String ROTATION_X = "RotationX";
    public static final String ROTATION_Y = "RotationY";
    public static final String ROTATION_Z = "RotationZ";

    protected CyberwareItem(Properties pProperties) {
        super(pProperties);
    }


    public static boolean getInstalled(ItemStack stack) {
        if(!stack.hasTag()) createTags(stack);
        return stack.getTag().getBoolean(INSTALLED);
    }
    public static float getPosX(ItemStack stack) {
        if(!stack.hasTag()) createTags(stack);
        return stack.getTag().getFloat(POS_X);
    }

    public static float getPosY(ItemStack stack) {
        if(!stack.hasTag()) createTags(stack);
        return stack.getTag().getFloat(POS_Y);
    }

    public static float getPosZ(ItemStack stack) {
        if(!stack.hasTag()) createTags(stack);
        return stack.getTag().getFloat(POS_Z);
    }

    public static float getScale(ItemStack stack) {
        if(!stack.hasTag()) createTags(stack);
        return stack.getTag().getFloat(SCALE);
    }

    public static float getRotationX(ItemStack stack) {
        if(!stack.hasTag()) createTags(stack);
        return stack.getTag().getFloat(ROTATION_X);
    }

    public static float getRotationY(ItemStack stack) {
        if(!stack.hasTag()) createTags(stack);
        return stack.getTag().getFloat(ROTATION_Y);
    }

    public static float getRotationZ(ItemStack stack) {
        if(!stack.hasTag()) createTags(stack);
        return stack.getTag().getFloat(ROTATION_Z);
    }

    public static void setInstalled(ItemStack stack, boolean b) {
        if(!stack.hasTag()) createTags(stack);
        stack.getTag().putBoolean(INSTALLED, b);
    }

    public static void setPosX(ItemStack stack, float x) {
        if(!stack.hasTag()) createTags(stack);
        stack.getTag().putFloat(POS_X, x);
    }

    public static void setPosY(ItemStack stack, float x) {
        if(!stack.hasTag()) createTags(stack);
        stack.getTag().putFloat(POS_Y, x);
    }

    public static void setPosZ(ItemStack stack, float x) {
        if(!stack.hasTag()) createTags(stack);
        stack.getTag().putFloat(POS_Z, x);
    }

    public static void setScale(ItemStack stack, float x) {
        if(!stack.hasTag()) createTags(stack);
        stack.getTag().putFloat(SCALE, x);
    }

    public static void setRotationX(ItemStack stack, float x) {
        if(!stack.hasTag()) createTags(stack);
        stack.getTag().putFloat(ROTATION_X, x);
    }

    public static void setRotationY(ItemStack stack, float x) {
        if(!stack.hasTag()) createTags(stack);
        stack.getTag().putFloat(ROTATION_Y, x);
    }

    public static void setRotationZ(ItemStack stack, float x) {
        if(!stack.hasTag()) createTags(stack);
        stack.getTag().putFloat(ROTATION_Z, x);
    }

    public static void createTags(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putBoolean(INSTALLED, false);
        tag.putFloat(POS_X, 0.0F);
        tag.putFloat(POS_Y, 0.0F);
        tag.putFloat(POS_Z, 0.0F);
        tag.putFloat(SCALE, 1.0F);
        tag.putFloat(ROTATION_X, 0.0F);
        tag.putFloat(ROTATION_Y, 0.0F);
        tag.putFloat(ROTATION_Z, 0.0F);
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
        if(CyberwareItem.getInstalled(pStack)) {
            pTooltipComponents.add(Component.literal("INSTALLED").withStyle(Style.EMPTY.withColor(0xa80019)));
        }
    }
}
