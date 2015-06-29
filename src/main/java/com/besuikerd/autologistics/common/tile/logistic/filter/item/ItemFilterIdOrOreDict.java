package com.besuikerd.autologistics.common.tile.logistic.filter.item;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.lib.util.OreDictionaryUtil;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemFilterIdOrOreDict implements IItemFilter{
    public final String mod;
    public final String name;
    public final int meta;

    public ItemFilterIdOrOreDict(String mod, String name, int meta) {
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

    public static class ItemFilterProvider implements IItemFilterProvider{
        public static final IItemFilterProvider instance = new ItemFilterProvider();

        @Override
        public IItemFilter provide(StackValue value) {
            ObjectValue item;
            if((item = StackValues.tryExpectType(ObjectValue.class, value)) != null){
                StringValue mod;
                StringValue name;
                if(
                        (mod = StackValues.tryExtractField(StringValue.class, "mod", item)) != null
                                && (name = StackValues.tryExtractField(StringValue.class, "name", item)) != null
                        ){
                    int itemMeta = -1;
                    IntegerValue optItemMeta;
                    if ((optItemMeta = StackValues.tryExtractField(IntegerValue.class, "meta", item)) != null) {
                        itemMeta = optItemMeta.value;
                    }
                    return new ItemFilterIdOrOreDict(mod.value, name.value, itemMeta);
                }
            }
            return null;
        }
    }
}
