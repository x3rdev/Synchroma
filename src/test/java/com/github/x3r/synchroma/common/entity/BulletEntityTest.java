package com.github.x3r.synchroma.common.entity;

import com.github.x3r.synchroma.util.Physics;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.junit.Test;

import static org.junit.Assert.*;

public class BulletEntityTest {


    @Test
    public void rayBoxCollides() {
        Vec3 origin = new Vec3(0, 0, 0);
        Vec3 direction = new Vec3(0, 1, 0);
        AABB box = new AABB(-1, -1, -1, 1, 1, 1);
        assertTrue(Physics.rayBoxCollides(origin, direction, box));
    }

}