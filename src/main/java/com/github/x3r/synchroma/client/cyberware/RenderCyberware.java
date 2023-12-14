package com.github.x3r.synchroma.client.cyberware;

import com.github.x3r.synchroma.common.capability.CyberwareCapability;
import com.github.x3r.synchroma.common.item.cyberware.CyberwareItem;
import com.github.x3r.synchroma.common.item.cyberware.ImplantLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class RenderCyberware {

    private RenderCyberware() {

    }

    @SubscribeEvent
    public static void renderPlayerEvent(RenderPlayerEvent event) {
        Player player = event.getEntity();
        player.getCapability(CyberwareCapability.INSTANCE).ifPresent(cyberwareCapability -> {
            for (ImplantLocation location : ImplantLocation.values()) {
                ItemStack[] cyberware = cyberwareCapability.getImplants(location);
                for (ItemStack stack : cyberware) {
                    if(!stack.isEmpty()) {

                    }
                }
            }
        });
    }
}
