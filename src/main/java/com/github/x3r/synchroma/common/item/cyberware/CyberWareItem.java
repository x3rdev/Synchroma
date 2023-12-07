package com.github.x3r.synchroma.common.item.cyberware;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class CyberWareItem extends Item {

    public static final String POS_X = "PosX";
    public static final String POS_Y = "PosY";
    public static final String POS_Z = "PosZ";
    public static final String SCALE = "Scale";
    public static final String ROTATION_X = "RotationX";
    public static final String ROTATION_Y = "RotationY";
    public static final String ROTATION_Z = "RotationZ";

    protected CyberWareItem(Properties pProperties) {
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
}
