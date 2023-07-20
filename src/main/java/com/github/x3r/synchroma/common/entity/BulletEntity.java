package com.github.x3r.synchroma.common.entity;

import com.github.x3r.synchroma.common.item.bullets.SynchromaBullet;
import com.github.x3r.synchroma.common.item.guns.BaseGunItem;
import com.github.x3r.synchroma.common.registry.EntityRegistry;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BulletEntity extends Projectile implements ItemSupplier {
    private BaseGunItem gun;
    private SynchromaBullet bullet;
    private float health;

    public BulletEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BulletEntity(Level pLevel, Entity owner, BaseGunItem gun, SynchromaBullet bullet) {
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
            if(!bulletViable()) {
                this.remove(RemovalReason.KILLED);
                return;
            }
            handleCollisions();
            Vec3 nextPos = this.position().add(this.getDeltaMovement());
            this.lookAt(EntityAnchorArgument.Anchor.EYES, nextPos);
            this.setPos(nextPos);
        }
    }

    //TODO add block and entity piercing
    private void handleCollisions() {
        Vec3 startVec = this.position();
        Vec3 endVec = this.position().add(this.getDeltaMovement());
        List<BlockHitResult> blockCollisions = traceBlockCollisions(startVec, endVec);
        List<EntityHitResult> entityCollisions = traceEntityCollisions(startVec, endVec, this.level().getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1)));

        List<HitResult> collisions = new ArrayList<>();
        collisions.addAll(blockCollisions);
        collisions.addAll(entityCollisions);
        collisions.sort(Comparator.comparingDouble(o -> o.getLocation().distanceToSqr(startVec)));
        for(HitResult hitResult : collisions) {
            if(hitResult instanceof EntityHitResult entityHitResult) {
                handleEntityCollision(entityHitResult);
            }
            if(hitResult instanceof BlockHitResult blockHitResult) {
                handleBlockCollision(blockHitResult);
            }
            if(!bulletViable()) {
                this.remove(RemovalReason.KILLED);
                return;
            }
        }
//        for (LivingEntity livingEntity : entityCollisions) {
//            livingEntity.hurt(this.damageSources().generic(), this.bullet.getDamage());
//            this.remove(RemovalReason.KILLED);
//            return;
//        }
//        for (BlockPos blockPos : blockCollisions) {
//            float strength = level().getBlockState(blockPos).getBlock().getExplosionResistance();
//            if (strength < 0.5F) {
//                level().destroyBlock(blockPos, false);
//            }
//            level().playSound(null, blockPos, level().getBlockState(blockPos).getSoundType().getBreakSound(), SoundSource.BLOCKS, 1, 0.75F);
//            this.remove(RemovalReason.KILLED);
//            return;
//        }
    }
    private void handleEntityCollision(EntityHitResult hitResult) {

    }

    private void handleBlockCollision(BlockHitResult hitResult) {

    }

    public List<BlockHitResult> traceBlockCollisions(Vec3 startVec, Vec3 endVec) {
        List<BlockHitResult> collisions = new ArrayList<>();
        double scale = this.getBoundingBox().getSize()/endVec.subtract(startVec).length();
        Vec3 increment = endVec.subtract(startVec).scale(scale);
        for(int i = 0; i < 1/scale; i++) {
            Vec3 vPos = startVec.add(increment.scale(i));
            BlockPos pos = new BlockPos(Mth.floor(vPos.x), Mth.floor(vPos.y), Mth.floor(vPos.z));
            BlockState state = level().getBlockState(pos);
            ((ServerLevel) level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), vPos.x, vPos.y, vPos.z, 10, 0, 0, 0, 1);
            if(!state.isAir()) {
                collisions.add(new BlockHitResult(vPos, Direction.getNearest(vPos.x, vPos.y, vPos.z).getOpposite(), pos, true));
            }
        }
        return collisions;
    }

    public List<EntityHitResult> traceEntityCollisions(Vec3 startVec, Vec3 endVec, List<Entity> candidates) {
        List<EntityHitResult> collisions = new ArrayList<>();
        candidates.forEach(entity -> {
            if(entity instanceof LivingEntity livingEntity) {
                AABB box = livingEntity.getBoundingBox();
                Optional<Vec3> hitPos = box.clip(startVec, endVec);
                hitPos.ifPresent(vec3 -> collisions.add(new EntityHitResult(livingEntity, vec3)));
            }
        });
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
