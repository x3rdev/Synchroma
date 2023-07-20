package com.github.x3r.synchroma.common.item.guns;

import com.github.x3r.synchroma.common.entity.BulletEntity;
import com.github.x3r.synchroma.common.item.bullets.SynchromaBullet;
import com.github.x3r.synchroma.common.item.modification.GunModification;
import com.github.x3r.synchroma.common.registry.ItemRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseGunItem extends Item {
    protected BaseGunItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        ItemStack ammoStack = new ItemStack(ItemRegistry.TEST_BULLET.get(), 1);
        BulletEntity bullet = new BulletEntity(pLevel, pPlayer, (BaseGunItem) itemStack.getItem(), (SynchromaBullet) ammoStack.getItem());
        if(!pLevel.isClientSide()) {
            pPlayer.getCooldowns().addCooldown(this, getDelay());
            bullet.shoot();
            pLevel.addFreshEntity(bullet);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    public abstract int getDelay();

    public abstract String[] getDefaultBones();

    /**
     * Returns all possible modifications for this weapon
     */
    public abstract GunModification[] getModifications();
    public Map<String, ResourceLocation> getTextureMap() {
        HashMap<String, ResourceLocation> map = new HashMap<>();
        Arrays.stream(getModifications()).forEach((mod -> map.put(mod.getName().getPath(), new ResourceLocation(getName().getNamespace(),
                String.format("textures/item/%s/%s.png", ForgeRegistries.ITEMS.getKey(this).getPath(), mod.getName().getPath())))));
        Arrays.stream(getDefaultBones()).forEach(bone -> map.put(bone, new ResourceLocation(getName().getNamespace(),
                String.format("textures/item/%s/%s.png", ForgeRegistries.ITEMS.getKey(this).getPath(), bone))));
        return map;
    }

    public ResourceLocation getName() {
        return ForgeRegistries.ITEMS.getKey(this);
    }

    @Override
    public @Nullable CompoundTag getShareTag(ItemStack stack) {

        return super.getShareTag(stack);
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
    }
}
