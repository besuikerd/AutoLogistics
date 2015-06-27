package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.tile.logistic.StringFacing;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public abstract class AbstractLogisticFilter implements ILogisticFilter {

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

    public AbstractLogisticFilter(StackValue value){
        extractAttributes(value);
    }

    protected void extractAttributes(StackValue value){
        ObjectValue obj;
        int amount = -1;
        EnumFacing[] validSides = EnumFacing.values();
        ItemFilter[] itemFilters = new ItemFilter[0];

        if((obj = StackValues.tryExpectType(ObjectValue.class, value)) != null) {
            int meta = -1;
            IntegerValue optMeta;
            if ((optMeta = StackValues.tryExtractField(IntegerValue.class, "meta", obj)) != null) {
                meta = optMeta.value;
            }
            this.meta = meta;


            ObjectValue filter;
            if ((filter = StackValues.tryExtractField(ObjectValue.class, "filter", obj)) != null) {
                IntegerValue optAmount;
                if ((optAmount = StackValues.tryExtractField(IntegerValue.class, "amount", filter)) != null) {
                    amount = optAmount.value;
                }
                this.amount = amount;

                ListValue sides;
                if ((sides = StackValues.tryExtractField(ListValue.class, "sides", filter)) != null) {
                    if (!sides.value.isEmpty()) {
                        EnumFacing[] optValidSides = new EnumFacing[sides.value.size()];
                        int offset = 0;
                        for (StackValue sideValue : sides.value) {
                            StringValue side;
                            if ((side = StackValues.tryExpectType(StringValue.class, sideValue)) != null) {
                                StringFacing stringFacing = StringFacing.fromString(side.value);
                                if (stringFacing != null) {
                                    optValidSides[offset++] = stringFacing.facing;
                                } else {
                                    optValidSides = validSides;
                                    break;
                                }
                            } else {
                                optValidSides = validSides;
                                break;
                            }
                        }
                        validSides = optValidSides;
                    }
                }

                ListValue items;
                if ((items = StackValues.tryExtractField(ListValue.class, "items", filter)) != null) {
                    if (!items.value.isEmpty()) {
                        ItemFilter[] optItems = new ItemFilter[items.value.size()];
                        int offset = 0;
                        for (StackValue itemValue : items.value) {
                            ItemFilter itemFilter;
                            if ((itemFilter = ItemFilter.fromStackValue(itemValue)) != null) {
                                optItems[offset++] = itemFilter;
                            } else {
                                optItems = itemFilters;
                                break;
                            }
                        }
                        itemFilters = optItems;
                    }
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
