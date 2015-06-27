package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class LogisticFilterPosition extends AbstractLogisticFilter{
    private String type;
    private int x;
    private int y;
    private int z;

    public LogisticFilterPosition(int meta, int amount, EnumFacing[] validSides, ItemFilter[] itemFilters, String type, int x, int y, int z) {
        super(meta, amount, validSides, itemFilters);
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

    @Override
    public boolean passesBlockFilter(TileEntity from, TileEntity to) {
        if(meta != -1 && to.getBlockMetadata() != meta){
            return false;
        }

        if(type.equals("relative")){
            return
                   from.xCoord + x == to.xCoord
                && from.yCoord + y == to.yCoord
                && from.zCoord + z == to.zCoord;

        } else if(type.equals("absolute")){
            return
                    from.xCoord == x
                && from.yCoord == y
                && from.zCoord == z;
        }
        return false;
    }

    public static class FilterProvider implements IFilterProvider{
        public static final FilterProvider instance = new FilterProvider();

        @Override
        public ILogisticFilter provide(StackValue value) {
            ObjectValue obj;
            StringValue type;
            IntegerValue x;
            IntegerValue y;
            IntegerValue z;
            if(
                   (obj = StackValues.tryExpectType(ObjectValue.class, value)) != null
                && ((type = StackValues.tryExtractField(StringValue.class, "type", obj)) != null && type.value.equals("absolute") || type.value.equals("relative"))
                && (x = StackValues.tryExtractField(IntegerValue.class, "x", obj)) != null
                && (y = StackValues.tryExtractField(IntegerValue.class, "y", obj)) != null
                && (z = StackValues.tryExtractField(IntegerValue.class, "z", obj)) != null
            ){
                return new LogisticFilterPosition(obj, type.value, x.value, y.value, z.value);
            }
            return null;
        }
    }
}
