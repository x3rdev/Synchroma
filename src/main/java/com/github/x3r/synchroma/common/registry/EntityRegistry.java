package com.github.x3r.synchroma.common.registry;

import com.github.x3r.synchroma.Synchroma;
import com.github.x3r.synchroma.common.entity.BulletEntity;
import com.github.x3r.synchroma.common.entity.RideableEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Synchroma.MOD_ID);

    public static final RegistryObject<EntityType<BulletEntity>> BULLET_PROJECTILE = ENTITIES.register("bullet_projectile",
            () -> EntityType.Builder.<BulletEntity>of(BulletEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(1)
                    .noSave()
                    .build(new ResourceLocation(Synchroma.MOD_ID, "bullet_projectile").toString()));

    public static final RegistryObject<EntityType<RideableEntity>> RIDEABLE = ENTITIES.register("rideable",
            () -> EntityType.Builder.<RideableEntity>of(RideableEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .noSave()
                    .build(new ResourceLocation(Synchroma.MOD_ID, "rideable").toString()));
}
