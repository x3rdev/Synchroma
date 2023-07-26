package com.github.x3r.synchroma.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MachineUpgradeItem extends Item {

    private final int tier;
    private final MutableComponent description;

    public MachineUpgradeItem(Properties pProperties, int tier, MutableComponent description) {
        super(pProperties);
        this.tier = tier;
        this.description = description;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        switch (tier) {
            case 1 -> description.setStyle(Style.EMPTY.withColor(0xc49123));
            case 2 -> description.setStyle(Style.EMPTY.withColor(0x9abf1f));
            case 3 -> description.setStyle(Style.EMPTY.withColor(0x8b10b0));
            default -> description.setStyle(Style.EMPTY);
        }
        pTooltipComponents.add(description);
    }
}
