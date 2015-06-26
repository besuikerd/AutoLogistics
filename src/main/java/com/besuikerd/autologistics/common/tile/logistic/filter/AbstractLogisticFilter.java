package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.tile.logistic.StringFacing;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public abstract class AbstractLogisticFilter implements LogisticFilter{

    protected int meta;
    protected int amount;
    protected EnumFacing[] validSides;
    protected ItemFilter[] itemFilters;

    public AbstractLogisticFilter(int meta, int amount, EnumFacing[] validSides, ItemFilter[] itemFilters) {
        this.meta = meta;
        this.amount = amount;
        this.validSides = validSides;
        this.itemFilters = itemFilters;
    }

    public AbstractLogisticFilter(ObjectValue obj){
        extractAttributes(obj);
    }

    protected void extractAttributes(ObjectValue obj){
        int meta = -1;
        IntegerValue optMeta;
        if ((optMeta = StackValues.tryExtractField(IntegerValue.class, "meta", obj)) != null) {
            meta = optMeta.value;
        }
        this.meta = meta;

        int amount = -1;
        EnumFacing[] validSides = EnumFacing.values();
        ItemFilter[] itemFilters = new ItemFilter[0];
        ObjectValue filter;
        if((filter = StackValues.tryExtractField(ObjectValue.class, "filter", obj)) != null){
            IntegerValue optAmount;
            if((optAmount = StackValues.tryExtractField(IntegerValue.class, "amount", filter)) != null){
                amount = optAmount.value;
            }
            this.amount = amount;

            ListValue sides;
            if((sides = StackValues.tryExtractField(ListValue.class, "sides", filter)) != null){
                if(!sides.value.isEmpty()) {
                    EnumFacing[] optValidSides = new EnumFacing[sides.value.size()];
                    int offset = 0;
                    for(StackValue value: sides.value){
                        StringValue side;
                        if((side = StackValues.tryExpectType(StringValue.class, value)) != null){
                            StringFacing stringFacing = StringFacing.fromString(side.value);
                            if(stringFacing != null){
                                optValidSides[offset++] = stringFacing.facing;
                            } else{
                                optValidSides = validSides;
                                break;
                            }
                        } else{
                            optValidSides = validSides;
                            break;
                        }
                    }
                    validSides = optValidSides;
                }
            }

            ListValue items;
            if((items = StackValues.tryExtractField(ListValue.class, "items", filter)) != null){
                if(!items.value.isEmpty()){
                    ItemFilter[] optItems = new ItemFilter[items.value.size()];
                    int offset = 0;
                    for(StackValue value : items.value){
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
                                optItems[offset++] = new ItemFilter(mod.value, name.value, itemMeta);
                            } else{
                                optItems = itemFilters;
                                break;
                            }
                        }
                    }
                    itemFilters = optItems;
                }
            }
        }
        this.amount = amount;
        this.validSides = validSides;
        this.itemFilters = itemFilters;
    }

    @Override
    public boolean passesItemFilter(ItemStack stack) {
        for(ItemFilter obj : itemFilters){
            if(obj.passesFilter(stack)){
                return true;
            }
        }
        return itemFilters.length == 0;
    }

    @Override
    public int getMeta() {
        return meta;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    public EnumFacing[] getValidSides() {
        return validSides;
    }
}
