package com.github.x3r.synchroma.client.renderer.armor;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.client.renderer.DyeableGeoLayer;
import com.github.x3r.synchroma.common.item.armor.BasicSpaceSuitItem;
import com.github.x3r.synchroma.common.item.armor.EngineerSuitItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class EngineerSuitRenderer extends GeoArmorRenderer<EngineerSuitItem> {
    public EngineerSuitRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(Synchroma.MOD_ID, "armor/engineer_suit")));

    }
}
