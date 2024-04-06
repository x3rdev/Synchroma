package com.github.x3r.synchroma.common.entity;

import com.github.x3r.synchroma.common.spaceship.SpaceshipSavedData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;

public abstract class SpaceshipEntity extends Entity implements GeoEntity {

    private int regionIndex = -1;

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

    private int getOrCreateInteriorIndex(ServerLevel level) {
        SpaceshipSavedData data = level.getDataStorage().computeIfAbsent(SpaceshipSavedData::load, SpaceshipSavedData::new, "spaceship");
        if (regionIndex == -1) {
            regionIndex = data.getNextFreeRegion();
            data.reserveRegion(regionIndex);
        }
        return ;
    }

    @Override
    public void load(CompoundTag tag) {
        this.regionIndex = tag.getInt("regionIndex");
        super.load(tag);
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putInt("regionIndex", regionIndex);
        return super.save(tag);
    }
}
