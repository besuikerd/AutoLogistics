package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.lib.util.OreDictionaryUtil;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class LogisticFilterItem extends AbstractLogisticFilter{
    private String mod;
    private String name;

    public LogisticFilterItem(int meta, int amount, EnumFacing[] validSides, ItemFilter[] itemFilters, String mod, String name) {
        super(meta, amount, validSides, itemFilters);
        this.mod = mod;
        this.name = name;
    }

    public LogisticFilterItem(ObjectValue obj, String mod, String name) {
        super(obj);
        this.mod = mod;
        this.name = name;
    }

    public static LogisticFilterItem fromObjectValue(ObjectValue obj){
        StringValue type;
        StringValue mod;
        StringValue name;
        if(
            (type = StackValues.tryExtractField(StringValue.class, "type", obj)) != null && type.value.equals("item")
            && (mod = StackValues.tryExtractField(StringValue.class, "mod", obj)) != null
            && (name = StackValues.tryExtractField(StringValue.class, "name", obj)) != null
        ){
            return new LogisticFilterItem(obj, mod.value, name.value);
        }
        return null;
    }

    @Override
    public boolean passesBlockFilter(TileEntity from, TileEntity to) {
        if(meta != 1 && to.getBlockMetadata() == meta){
            return false;
        }
        if(mod.equals("ore")){
            return OreDictionaryUtil.matchesOreDictionary(name, to.getBlockType());
        } else{
            Block block = GameRegistry.findBlock(mod, name);
            return to.getBlockType().equals(block);
        }
    }
}
