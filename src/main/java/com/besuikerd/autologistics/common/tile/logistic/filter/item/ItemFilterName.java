package com.besuikerd.autologistics.common.tile.logistic.filter.item;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValues;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StringValue;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class ItemFilterName extends AbstractItemFilter{
    private String name;

    public ItemFilterName(StackValue value, String name) {
        super(value);
        this.name = name;
    }

    @Override
    public boolean passesFilterImpl(ItemStack stack) {
        if(!stack.getDisplayName().toLowerCase().matches(name)){
            GameRegistry.UniqueIdentifier itemId = GameRegistry.findUniqueIdentifierFor(stack.getItem());
            return (itemId.modId + ":" + itemId.name).matches(name);
        }
        return true;
    }

    public static class ItemFilterProvider implements IItemFilterProvider{
        public static final ItemFilterProvider instance = new ItemFilterProvider();

        @Override
        public IItemFilter provide(StackValue value) {
            ObjectValue obj;
            StringValue name;
            if(
                   (obj = StackValues.tryGetObjectOfType("name", value)) != null
                && (name = StackValues.tryExtractField(StringValue.class, "name", obj)) != null
            ){
                return new ItemFilterName(value, name.value);
            }
            return null;
        }
    }
}
