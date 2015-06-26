package com.besuikerd.autologistics.common.tile.logistic;

import com.google.common.collect.Maps;
import net.minecraft.util.EnumFacing;

import java.util.HashMap;
import java.util.Map;

public enum StringFacing {
    DOWN("down", EnumFacing.DOWN),
    UP("up", EnumFacing.UP),
    NORTH("north", EnumFacing.NORTH),
    SOUTH("south", EnumFacing.SOUTH),
    EAST("east", EnumFacing.EAST),
    WEST("west", EnumFacing.WEST)
    ;

    public final String name;
    public final EnumFacing facing;

    StringFacing(String name, EnumFacing facing) {
        this.name = name;
        this.facing = facing;
    }

    private static final Map<String, StringFacing> stringMapping = new HashMap<String, StringFacing>();
    private static final Map<EnumFacing, StringFacing> mutableFacingMapping = new HashMap<EnumFacing, StringFacing>();
    private static final Map<EnumFacing, StringFacing> facingMapping;

    static {
        for(StringFacing facing : values()){
            stringMapping.put(facing.name, facing);
            mutableFacingMapping.put(facing.facing, facing);
        }

        facingMapping = Maps.immutableEnumMap(mutableFacingMapping);
    }

    public static StringFacing fromString(String facing){
        return stringMapping.get(facing);
    }

    public static StringFacing fromEnumFacing(EnumFacing facing){
        return facingMapping.get(facing);
    }
}
