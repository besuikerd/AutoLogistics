package com.besuikerd.autologistics.common.tile.logistic;

import com.besuikerd.autologistics.common.block.BlockLogisticCable;
import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.AbstractNativeFunction;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.IntegerValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StringValue;
import com.besuikerd.autologistics.common.tile.traits.TileCable;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class NativeFunctionItemTransfer extends AbstractNativeFunction {
    public static final NativeFunctionItemTransfer instance = new NativeFunctionItemTransfer();

    private TileEntity tile;

    @Override
    public StackValue call(VirtualMachine vm, List<StackValue> args) {
        return null;
    }

    public void transferTo(ObjectValue from, ObjectValue to){
        List<IInventory> allInventories = new LazyTileFinder<IInventory>(IInventory.class, TileCable.class, tile).allValues();
        List<IInventory> toInventories = filterInventories(allInventories, to);
    }

    private List<IInventory> filterInventories(List<IInventory> inventories, ObjectValue filter){
        List<IInventory> filteredInventories = new ArrayList<IInventory>();
        for(IInventory inv : inventories){
            TileEntity tile = (TileEntity) inv;
            if(filterByName(tile, filter) || filterByPosition(tile, filter)){
                filteredInventories.add(inv);
            }
        }
        return filteredInventories;
    }


    public boolean filterByPosition(TileEntity tile, ObjectValue filter){
        StackValue optMod = filter.mapping.get("mod");
        if(optMod != null && optMod instanceof StringValue){

            StackValue optName = filter.mapping.get("name");
            if(optName != null && optName instanceof StringValue){
                StringValue mod = (StringValue) optMod;
                StringValue name = (StringValue) optName;

                Block block = Block.getBlockFromName(mod.value + ":" + name.value);
                //TODO ore dictionary

                if(tile.getBlockType().equals(block)){
                    StackValue optMeta = filter.mapping.get("meta");
                    if(optMeta != null && optMeta instanceof IntegerValue){
                        IntegerValue meta = (IntegerValue) optMeta;
                        if(tile.getBlockMetadata() != meta.value){
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean filterByName(TileEntity tile, ObjectValue filter){
        StackValue optType = filter.mapping.get("type");
        if(optType != null && optType instanceof StringValue){
            StackValue optX = filter.mapping.get("x");
            if(optX != null && optX instanceof IntegerValue){
                StackValue optY = filter.mapping.get("y");
                if(optY != null && optY instanceof IntegerValue){
                    StackValue optZ = filter.mapping.get("z");
                    if(optZ != null && optZ instanceof IntegerValue){
                        StringValue type = (StringValue) optType;
                        IntegerValue x = (IntegerValue) optX;
                        IntegerValue y = (IntegerValue) optY;
                        IntegerValue z = (IntegerValue) optZ;

                        if(type.value.equals("relative")){
                            return
                                this.tile.xCoord + x.value == tile.xCoord &&
                                this.tile.yCoord + y.value == tile.yCoord &&
                                this.tile.zCoord + z.value == tile.zCoord;
                        } else if(type.value.equals("absolute")){
                            return
                                tile.xCoord == x.value &&
                                tile.yCoord == y.value &&
                                tile.zCoord == z.value;
                        }
                    }
                }
            }
        }
        return false;
    }
}
