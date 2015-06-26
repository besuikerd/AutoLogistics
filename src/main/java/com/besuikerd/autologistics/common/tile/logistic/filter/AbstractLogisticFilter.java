package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.tile.logistic.StringFacing;
import net.minecraft.util.EnumFacing;

public abstract class AbstractLogisticFilter implements LogisticFilter{

    protected int meta;
    protected int amount;
    protected EnumFacing[] validSides;

    public AbstractLogisticFilter(int meta, int amount, EnumFacing[] validSides) {
        this.meta = meta;
        this.amount = amount;
        this.validSides = validSides;
    }

    public AbstractLogisticFilter(ObjectValue obj){
        extractAttributes(obj);
    }

    protected void extractAttributes(ObjectValue obj){
        int meta = -1;
        IntegerValue optMeta = null;
        if ((optMeta = StackValues.tryExtractField(IntegerValue.class, "meta", obj)) != null) {
            meta = optMeta.value;
        }
        this.meta = meta;

        ObjectValue filter;
        if((filter = StackValues.tryExtractField(ObjectValue.class, "filter", obj)) != null){
            int amount = -1;
            IntegerValue optAmount;
            if((optAmount = StackValues.tryExtractField(IntegerValue.class, "amount", filter)) != null){
                amount = optAmount.value;
            }
            this.amount = amount;

            EnumFacing[] validSides = EnumFacing.values();
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
            this.validSides = validSides;
        }
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
