package com.github.x3r.synchroma.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class BlockUtils {

    //TODO Implement for 6 directions
    public static VoxelShape rotate(VoxelShape shape, Direction newDir) {
        return rotate(shape, Direction.NORTH, newDir);
    }
    public static VoxelShape rotate(VoxelShape shape, Direction originalDir, Direction newDir) {
        if (originalDir != newDir) {
            VoxelShape[] newShape = new VoxelShape[] { Shapes.empty() };
            shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                double i = 1 - maxZ;
                double j = 1 - minZ;
                newShape[0] = Shapes.or(newShape[0], Shapes.box(Math.min(i, j), minY, minX, Math.max(i, j), maxY, maxX));
            });
            return rotate(newShape[0], originalDir.getClockWise(), newDir);
        }
        return shape;
    }
}
