package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class LogisticFilterPosition extends AbstractLogisticFilter{
    private String type;
    private int x;
    private int y;
    private int z;

    public LogisticFilterPosition(int meta, int amount, EnumFacing[] validSides, String type, int x, int y, int z) {
        super(meta, amount, validSides);
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public LogisticFilterPosition(ObjectValue obj ,String type, int x, int y, int z) {
        super(obj);
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static LogisticFilterPosition fromObjectValue(ObjectValue obj){
        StringValue type;
        if((type = StackValues.tryExtractField(StringValue.class, "type", obj)) != null && type.value.equals("absolute") || type.value.equals("relative")) {
            IntegerValue x;
            IntegerValue y;
            IntegerValue z;
            if(
                   (x = StackValues.tryExtractField(IntegerValue.class, "x", obj)) != null
                && (y = StackValues.tryExtractField(IntegerValue.class, "y", obj)) != null
                && (z = StackValues.tryExtractField(IntegerValue.class, "z", obj)) != null
            ){
                return new LogisticFilterPosition(obj, type.value, x.value, y.value, z.value);
            }
        }
        return null;
    }

    @Override
    public boolean passesItemFilter(ItemStack stack) {
        return false;
    }

    @Override
    public boolean passesBlockFilter(TileEntity tile) {
        if(meta != -1 && tile.getBlockMetadata() != meta){
            return false;
        }

        if(type.equals("relative")){
            return
                   tile.xCoord + x == tile.xCoord
                && tile.yCoord + y == tile.yCoord
                && tile.zCoord + z == tile.zCoord;

        } else if(type.equals("absolute")){
            return
                   tile.xCoord == x
                && tile.yCoord == y
                && tile.zCoord == z;
        }
        return false;
    }
}
