package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.lib.util.OreDictionaryUtil;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class LogisticFilterItem extends AbstractLogisticFilter{
    private String mod;
    private String name;

    public LogisticFilterItem(int meta, int amount, EnumFacing[] validSides, String mod, String name) {
        super(meta, amount, validSides);
        this.mod = mod;
        this.name = name;
    }

    public LogisticFilterItem(ObjectValue obj, String mod, String name) {
        super(obj);
        this.mod = mod;
        this.name = name;
    }

    public static LogisticFilterItem fromObjectValue(ObjectValue obj){
        StringValue mod;
        StringValue name;
        if(
               (mod = StackValues.tryExtractField(StringValue.class, "mod", obj)) != null
            && (name = StackValues.tryExtractField(StringValue.class, "name", obj)) != null
        ){
            return new LogisticFilterItem(obj, mod.value, name.value);
        }
        return null;
    }

    @Override
    public boolean passesItemFilter(ItemStack stack) {
        if(meta != 1 && stack.getItemDamage() != meta){
            return false;
        }

        if(mod.equals("ore")){
            return OreDictionaryUtil.matchesOreDictionary(name, stack);
        } else{
            Item item = GameRegistry.findItem(mod, name);
            return item == stack.getItem();
        }
    }

    @Override
    public boolean passesBlockFilter(TileEntity tile) {
        if(meta != 1 && tile.getBlockMetadata() == meta){
            return false;
        }
        if(mod.equals("ore")){
            return OreDictionaryUtil.matchesOreDictionary(name, tile.getBlockType());
        } else{
            return tile.getBlockMetadata() == meta;
        }
    }
}
