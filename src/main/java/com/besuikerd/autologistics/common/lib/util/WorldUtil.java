package com.besuikerd.autologistics.common.lib.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class WorldUtil {
    @SuppressWarnings("unchecked")
    public static <A extends Entity> List<A> getEntitiesWithinRange(Class<A> cls, World world, int x, int y, int z, int range){
        int halfX = x >> 1;
        int halfZ = y >> 1;

        AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(x - halfX, 0, z - halfZ, x + halfX, 255, z + halfZ);

        List entities = world.getEntitiesWithinAABB(cls, aabb);
        return (List<A>) entities;
    }
}
