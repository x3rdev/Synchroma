package com.github.x3r.synchroma.common.item.cyberware;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class CyberwareItem extends Item {

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

    @Override
    public @Nullable CompoundTag getShareTag(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if(tag != null) {
            tag.putFloat(POS_X, tag.get(POS_X) != null ? tag.getFloat(POS_X) : 0.0F);
            tag.putFloat(POS_Y, tag.get(POS_Y) != null ? tag.getFloat(POS_Y) : 0.0F);
            tag.putFloat(POS_Z, tag.get(POS_Z) != null ? tag.getFloat(POS_Z) : 0.0F);
            tag.putFloat(SCALE, tag.get(SCALE) != null ? tag.getFloat(SCALE) : 1.0F);
            tag.putFloat(ROTATION_X, tag.get(ROTATION_X) != null ? tag.getFloat(ROTATION_X) : 0.0F);
            tag.putFloat(ROTATION_Y, tag.get(ROTATION_Y) != null ? tag.getFloat(ROTATION_Y) : 0.0F);
            tag.putFloat(ROTATION_Z, tag.get(ROTATION_Z) != null ? tag.getFloat(ROTATION_Z) : 0.0F);
        }
        return super.getShareTag(stack);
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    public void onInstall(ServerPlayer player, ItemStack stack, ImplantLocation location, int slot) {

    }
    public void onRemove(ServerPlayer player, ItemStack stack, ImplantLocation location, int slot) {

    }
}
