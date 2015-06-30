package com.besuikerd.autologistics.common.lib.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryUtil {
    public static boolean matchesOreDictionary(String ore, ItemStack stack){
        for(ItemStack oreStack : OreDictionary.getOres(ore)){
            if(OreDictionary.itemMatches(oreStack, stack, false)){
                return true;
            }
        }
        return false;
    }

    public static boolean matchesOreDictionary(String ore, Block block){
        return matchesOreDictionary(ore, new ItemStack(block, 1));
    }

    public static boolean matchesOreDictionary(String ore, Item item){
        return matchesOreDictionary(ore, new ItemStack(item, 1));
    }
}
