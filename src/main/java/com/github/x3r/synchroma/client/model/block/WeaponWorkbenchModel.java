package com.github.x3r.synchroma.client.model.block;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.block.weapon_workbench.WeaponWorkbenchBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class WeaponWorkbenchModel extends DefaultedBlockGeoModel<WeaponWorkbenchBlockEntity> {

    public WeaponWorkbenchModel() {
        super(new ResourceLocation(Synchroma.MOD_ID, "weapon_workbench"));
    }
}
