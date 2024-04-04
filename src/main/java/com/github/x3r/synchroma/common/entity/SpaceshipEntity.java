package com.github.x3r.synchroma.common.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;

public abstract class SpaceshipEntity extends Entity implements GeoEntity {

    protected SpaceshipEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        if(pPlayer.level().isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        return super.interact(pPlayer, pHand);
    }
}
