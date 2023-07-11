package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.item.modification.GunModification;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GunModificationRegistry {
    public static final DeferredRegister<Item> GUN_MODIFICATIONS = DeferredRegister.create(ForgeRegistries.ITEMS, Synchroma.MOD_ID);

    public static final RegistryObject<GunModification> DOT_SCOPE = GUN_MODIFICATIONS.register("dot_scope", GunModification::new);
    public static final RegistryObject<GunModification> MARKING_SCOPE = GUN_MODIFICATIONS.register("marking_scope", GunModification::new);
    public static final RegistryObject<GunModification> SIMPLE_STOCK = GUN_MODIFICATIONS.register("simple_stock", GunModification::new);
    public static final RegistryObject<GunModification> ADVANCED_STOCK = GUN_MODIFICATIONS.register("advanced_stock", GunModification::new);
    public static final RegistryObject<GunModification> BULLET_SPEED_UPGRADE = GUN_MODIFICATIONS.register("bullet_speed_upgrade", GunModification::new);
    public static final RegistryObject<GunModification> FIRE_RATE_UPGRADE = GUN_MODIFICATIONS.register("fire_rate_upgrade", GunModification::new);
    public static final RegistryObject<GunModification> MUZZLE = GUN_MODIFICATIONS.register("muzzle", GunModification::new);
    public static final RegistryObject<GunModification> SILENCER = GUN_MODIFICATIONS.register("silencer", GunModification::new);
    public static final RegistryObject<GunModification> COUNTER = GUN_MODIFICATIONS.register("counter", GunModification::new);
    public static final RegistryObject<GunModification> LASER = GUN_MODIFICATIONS.register("laser", GunModification::new);
    public static final RegistryObject<GunModification> FLASHLIGHT = GUN_MODIFICATIONS.register("flashlight", GunModification::new);


}
