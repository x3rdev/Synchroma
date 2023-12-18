package com.github.x3r.synchroma.common.entity;

import com.github.x3r.synchroma.common.registry.EntityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class RideableEntity extends Entity {

    public RideableEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        noPhysics = true;
    }

    @Override
    protected void defineSynchedData() {

    }

    public RideableEntity(Level level) {
        super(EntityRegistry.RIDEABLE.get(), level);
    }

    @Override
    public void tick() {
        super.tick();
        if(!this.level().isClientSide && this.getPassengers().isEmpty()) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected boolean canRide(Entity pVehicle) {
        return true;
    }

    @Override
    protected void addPassenger(Entity pPassenger) {
        super.addPassenger(pPassenger);
        pPassenger.setYRot(this.getYRot());
        pPassenger.setYHeadRot(this.getYRot());
    }

    @Override
    public boolean dismountsUnderwater() {
        return false;
    }

    @Override
    protected void removePassenger(Entity pPassenger) {
//        if(pPassenger instanceof Player player && player.level().isClientSide()) {
//            Minecraft.getInstance().screen.onClose();
//        }
        super.removePassenger(pPassenger);
    }

    @Override
    protected void positionRider(Entity pPassenger, MoveFunction pCallback) {
        super.positionRider(pPassenger, pCallback);
        this.clampYaw(pPassenger);
    }

    @Override
    public void onPassengerTurned(Entity entity) {
        this.clampYaw(entity);
    }

    private void clampYaw(Entity passenger) {
        passenger.setYBodyRot(this.getYRot());
        float wrappedYaw = Mth.wrapDegrees(passenger.getYRot() - this.getYRot());
        float clampedYaw = Mth.clamp(wrappedYaw, -120.0F, 120.0F);
        passenger.yRotO += clampedYaw - wrappedYaw;
        passenger.setYRot(passenger.getYRot() + clampedYaw - wrappedYaw);
        passenger.setYHeadRot(passenger.getYRot());
    }
}
