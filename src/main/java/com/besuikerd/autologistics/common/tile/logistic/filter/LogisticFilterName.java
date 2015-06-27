package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValues;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StringValue;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class LogisticFilterName extends AbstractLogisticFilter{

    private String name;

    public LogisticFilterName(int meta, int amount, EnumFacing[] validSides, ItemFilter[] itemFilters, String name) {
        super(meta, amount, validSides, itemFilters);
        this.name = name;
    }

    public LogisticFilterName(StackValue value, String name) {
        super(value);
        this.name = name;
    }

    @Override
    public boolean passesBlockFilter(TileEntity from, TileEntity to) {
        //TODO wildcards/regexes maybe?
        return getName(to.getWorldObj(), to.xCoord, to.yCoord, to.zCoord).equals(name);
    }

    public static String getName(World world, int x, int y, int z){
        Block block = world.getBlock(x, y, z);
        if(block != null) {
            String name = block.getLocalizedName();

            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile != null) {
                try {
                    NBTTagCompound tag = new NBTTagCompound();
                    tile.writeToNBT(tag);
                    String id = tag.getString("id");
                    if (id != null) {
                        name = id;
                    }
                } catch (RuntimeException e) {
                }
            }
            return name;
        } else{
            return Blocks.air.getLocalizedName();
        }

    }

    public static class FilterProvider implements IFilterProvider{
        public static final FilterProvider instance = new FilterProvider();

        @Override
        public ILogisticFilter provide(StackValue value) {
            ObjectValue obj;
            StringValue type;
            StringValue name;
            if(
                (obj = StackValues.tryExpectType(ObjectValue.class, value)) != null
                && (type = StackValues.tryExtractField(StringValue.class, "type", obj)) != null && type.value.equals("name")
                && (name = StackValues.tryExtractField(StringValue.class, "name", obj)) != null
            ){
                return new LogisticFilterName(obj, name.value);
            }
            return null;
        }
    }
}
