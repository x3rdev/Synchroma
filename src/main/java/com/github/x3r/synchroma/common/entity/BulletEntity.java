package com.github.x3r.synchroma.common.entity;

import com.github.x3r.synchroma.common.item.bullets.SynchromaBullet;
import com.github.x3r.synchroma.common.item.guns.SynchromaGun;
import com.github.x3r.synchroma.common.registry.DamageTypeRegistry;
import com.github.x3r.synchroma.common.registry.EntityRegistry;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BulletEntity extends Projectile implements ItemSupplier {
    private SynchromaGun gun;
    private SynchromaBullet bullet;
    private float health;

    public BulletEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BulletEntity(Level pLevel, Entity owner, SynchromaGun gun, SynchromaBullet bullet) {
        super(EntityRegistry.BULLET_PROJECTILE.get(), pLevel);
        this.gun = gun;
        this.bullet = bullet;
        setOwner(owner);
    }

    public void shoot() {
        Entity owner = getOwner();
        this.setPos(owner.getPosition(0).add(0, owner.getBbHeight() * 0.8, 0).add(owner.getLookAngle().scale(0.5)));
        this.setRot(owner.yRotO, owner.xRotO);
        this.setDeltaMovement(getBulletSpeed());
    }

    @Override
    public void tick() {
        super.tick();
        if(!this.level().isClientSide()) {
            Vec3 nextPos = this.position().add(this.getDeltaMovement());
            this.lookAt(EntityAnchorArgument.Anchor.EYES, nextPos);
            this.setPos(nextPos);
            if(!bulletViable()) {
                this.remove(RemovalReason.KILLED);
                return;
            }
            Vec3 startVec = this.position();
            Vec3 endVec = this.position().add(this.getDeltaMovement());
            handleEntityCollision(startVec, endVec);
            handleBlockCollision();
        }
    }
    private void handleEntityCollision(Vec3 startVec, Vec3 endVec) {
        List<LivingEntity> entityList = traceEntityCollisions(startVec, endVec, this.level().getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1)));
        entityList.forEach(livingEntity -> {
            livingEntity.hurt(this.damageSources().source(DamageTypeRegistry.BULLET_BODY, this, this.getOwner()), this.bullet.getDamage());
            if(bullet.getEffect() != null) {
                livingEntity.addEffect(bullet.getEffect(), getOwner());
            }
        });
    }

    public List<LivingEntity> traceEntityCollisions(Vec3 startVec, Vec3 endVec, List<Entity> candidates) {
        List<LivingEntity> collisions = new ArrayList<>();
        candidates.forEach(entity -> {
            if(entity instanceof LivingEntity livingEntity) {
                AABB box = livingEntity.getBoundingBox();
                Optional<Vec3> hitPos = box.clip(startVec, endVec);
                if(hitPos.isPresent()) {
                    collisions.add(livingEntity);
                }
            }
        });
        collisions.sort(Comparator.comparingDouble(o -> o.distanceToSqr(startVec)));
        return collisions;
    }

    private boolean bulletViable() {
        return !(this.gun == null || this.bullet == null || this.tickCount > 40 || this.health > 0);
    }

    private Vec3 getBulletSpeed() {
        float sp = bullet.getSpeed();
        double d = getLookAngle().length();
        return new Vec3((sp*getLookAngle().x)/d, (sp*getLookAngle().y)/d, (sp*getLookAngle().z)/d);
    }

    private void handleBlockCollision() {
        BlockState state = this.level().getBlockState(this.blockPosition());
        VoxelShape shape = state.getCollisionShape(this.level(), this.blockPosition());
        if(!shape.isEmpty()) {
//            state.isCollisionShapeFullBlock();
        }
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public ItemStack getItem() {
        return Items.STONE_BUTTON.getDefaultInstance();
    }
}
