package com.github.x3r.synchroma.common.spaceship;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class SpaceshipSavedData extends SavedData {

    final IntList reservedRegion = new IntArrayList();
    public SpaceshipSavedData() {

    }

    public static SpaceshipSavedData load(CompoundTag tag) {
        SpaceshipSavedData data = new SpaceshipSavedData();
        for (int i = 0; i < tag.getInt("reservedRegionSize"); i++) {
            data.reservedRegion.add(tag.getInt("reservedRegion" + i));
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("reservedRegionSize", reservedRegion.size());
        for (int i = 0; i < reservedRegion.size(); i++) {
            tag.putInt("reservedRegion" + i, reservedRegion.getInt(i));
        }
        return null;
    }

    public int getNextFreeRegion() {
        for (int i = 0; i < reservedRegion.size(); i++) {
            if (reservedRegion.getInt(i) != i) {
                return i;
            }
        }
        return reservedRegion.size();
    }

    public void reserveRegion(int index) {
        reservedRegion.add(index);
        reservedRegion.sort(Integer::compare);
    }

    public void unReserveRegion(int index) {
        reservedRegion.removeInt(index);
    }
}
