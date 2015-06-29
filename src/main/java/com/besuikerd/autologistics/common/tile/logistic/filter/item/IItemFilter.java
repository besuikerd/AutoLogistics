package com.besuikerd.autologistics.common.tile.logistic.filter.item;

import net.minecraft.item.ItemStack;

public interface IItemFilter {
    boolean passesFilter(ItemStack stack);
}
