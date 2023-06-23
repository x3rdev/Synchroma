package com.github.x3r.synchroma.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CapacitorItem extends Item {

    private final int capacitance;

    public CapacitorItem(Properties pProperties, int capacitance) {
        super(pProperties);
        this.capacitance = capacitance;
    }

    //TODO convert `TextComponent` to Translatable
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.literal("Capacitance " + this.capacitance + " pF").withStyle(ChatFormatting.GRAY));
    }
}
