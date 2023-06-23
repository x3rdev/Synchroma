package com.github.x3r.synchroma.util;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class Physics {

    private Physics(){}

    public static boolean rayBoxCollides(Vec3 origin, Vec3 dir, AABB aabb) {
        Vec3 invD = new Vec3(1/dir.x, 1/dir.y, 1/dir.z);
        Vec3 t0s = new Vec3(aabb.minX - origin.x, aabb.minY - origin.y, aabb.minZ - origin.z).multiply(invD);
        Vec3 t1s = new Vec3(aabb.maxX - origin.x, aabb.maxY - origin.y, aabb.maxZ - origin.z).multiply(invD);

        Vec3 mins = new Vec3(Math.min(t0s.x, t1s.x), Math.min(t0s.y, t1s.y), Math.min(t0s.z, t1s.z));
        Vec3 maxs = new Vec3(Math.max(t0s.x, t1s.x), Math.max(t0s.y, t1s.y), Math.max(t0s.z, t1s.z));

        //TODO figure these out, probably min and max parametrics but not 100% sure, one example just uses infinity, in this case 100 should be enough
        double tmin = -100;
        double tmax = 100;

        tmin = Math.max(tmin, Math.max(mins.x, Math.max(mins.y, mins.z)));
        tmax = Math.min(tmax, Math.min(maxs.x, Math.min(maxs.y, maxs.z)));

        return tmin < tmax;
    }
}
