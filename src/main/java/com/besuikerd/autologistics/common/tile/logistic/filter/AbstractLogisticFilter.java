package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.tile.logistic.StringFacing;
import com.besuikerd.autologistics.common.tile.logistic.filter.item.IItemFilter;
import com.besuikerd.autologistics.common.tile.logistic.filter.item.ItemFilterRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public abstract class AbstractLogisticFilter implements ILogisticFilter {

    protected int amount;
    protected EnumFacing[] validSides;
    protected IItemFilter[] itemFilters;

    public AbstractLogisticFilter(int amount, EnumFacing[] validSides, IItemFilter[] itemFilters) {
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
        IItemFilter[] itemFilters = new IItemFilter[0];

        if((obj = StackValues.tryExpectType(ObjectValue.class, value)) != null) {

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
                        IItemFilter[] optItems = new IItemFilter[items.value.size()];
                        int offset = 0;
                        for (StackValue itemValue : items.value) {
                            IItemFilter itemFilter;
                            if ((itemFilter = ItemFilterRegistry.instance.getFilter(itemValue)) != null) {
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
        for(IItemFilter obj : itemFilters){
            if(obj.passesFilter(stack)){
                return true;
            }
        }
        return itemFilters.length == 0;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    public EnumFacing[] getValidSides() {
        return validSides;
    }
}
