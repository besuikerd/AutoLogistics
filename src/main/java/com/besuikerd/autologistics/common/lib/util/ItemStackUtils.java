package com.besuikerd.autologistics.common.lib.util;

import net.minecraft.item.ItemStack;

public class ItemStackUtils {
    public static boolean areStackable(ItemStack stack1, ItemStack stack2){
        return stack1.getItem() == null && stack2.getItem() == null ||
            stack1.getItem() == stack2.getItem() &&
            stack1.getItemDamage() == stack2.getItemDamage() &&
            (
                stack1.getTagCompound() == null && stack2.getTagCompound() == null ||
                stack1.getTagCompound() != null && stack2.getTagCompound() != null && stack1.getTagCompound().equals(stack2.getTagCompound())
            );
    }
}
