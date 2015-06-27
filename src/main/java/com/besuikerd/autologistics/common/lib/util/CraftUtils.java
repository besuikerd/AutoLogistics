package com.besuikerd.autologistics.common.lib.util;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.List;

public class CraftUtils {
    public static IRecipe findMatchingRecipe(InventoryCrafting inventoryCrafting, World world){
        List recipes = CraftingManager.getInstance().getRecipeList();

        int i = 0;
        ItemStack itemstack = null;
        ItemStack itemstack1 = null;
        int j;

        for (j = 0; j < inventoryCrafting.getSizeInventory(); ++j)
        {
            ItemStack itemstack2 = inventoryCrafting.getStackInSlot(j);

            if (itemstack2 != null)
            {
                if (i == 0)
                {
                    itemstack = itemstack2;
                }

                if (i == 1)
                {
                    itemstack1 = itemstack2;
                }

                ++i;
            }
        }

        if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && itemstack.getItem().isRepairable())
        {
            Item item = itemstack.getItem();
            int j1 = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
            int k = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
            int l = j1 + k + item.getMaxDamage() * 5 / 100;
            int i1 = item.getMaxDamage() - l;

            if (i1 < 0)
            {
                i1 = 0;
            }

            return new ItemStackRecipe(new ItemStack(itemstack.getItem(), 1, i1));
        }
        else
        {
            for (j = 0; j < recipes.size(); ++j)
            {
                IRecipe irecipe = (IRecipe)recipes.get(j);

                if (irecipe.matches(inventoryCrafting, world))
                {
                    return irecipe;
                }
            }

            return null;
        }
    }

    public static class ItemStackRecipe implements IRecipe{

        private ItemStack result;

        public ItemStackRecipe(ItemStack result) {
            this.result = result;
        }

        @Override
        public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
            return false;
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
            return result;
        }

        @Override
        public int getRecipeSize() {
            return 0;
        }

        @Override
        public ItemStack getRecipeOutput() {
            return null;
        }
    }
}
