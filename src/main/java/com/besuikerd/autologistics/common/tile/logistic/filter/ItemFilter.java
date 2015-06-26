package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.util.OreDictionaryUtil;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemFilter {
    public final String mod;
    public final String name;
    public final int meta;

    public ItemFilter(String mod, String name, int meta) {
        this.mod = mod;
        this.name = name;
        this.meta = meta;
    }

    public boolean passesFilter(ItemStack stack){
        if(meta != -1 && stack.getItemDamage() != meta){
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
    public String toString() {
        return "ItemFilter(" +
                "mod='" + mod + '\'' +
                ", name='" + name + '\'' +
                ", meta=" + meta +
                ')';
    }
}
