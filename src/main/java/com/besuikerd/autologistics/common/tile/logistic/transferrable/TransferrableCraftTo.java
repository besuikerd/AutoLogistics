package com.besuikerd.autologistics.common.tile.logistic.transferrable;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.lib.inventory.DummyContainer;
import com.besuikerd.autologistics.common.lib.util.MathUtil;
import com.besuikerd.autologistics.common.lib.util.tuple.Tuple2;
import com.besuikerd.autologistics.common.lib.util.tuple.Vector2;
import com.besuikerd.autologistics.common.lib.util.tuple.Vector4;
import com.besuikerd.autologistics.common.tile.TileLogisticCable;
import com.besuikerd.autologistics.common.tile.logistic.LazyTileFinder;
import com.besuikerd.autologistics.common.tile.logistic.filter.ILogisticFilter;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterItemFilter;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterRegistry;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.InventoryItemCounterInsert;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.ItemCounterInnerLogisticFilter;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.ItemCounterInnerLogisticFilterExtract;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;

import java.util.*;

public class TransferrableCraftTo extends AbstractTransferrable<ObjectValue, StackValue>{

    private ILogisticFilter[] fromFilters;
    private ILogisticFilter[][] recipe;
    private ILogisticFilter[] toFilters;

    public TransferrableCraftTo(ILogisticFilter[] fromFilters, ILogisticFilter[][] recipe, ILogisticFilter[] toFilters) {
        this.fromFilters = fromFilters;
        this.recipe = recipe;
        this.toFilters = toFilters;
    }

    @Override
    public StackValue transferTo(TileEntity source, ObjectValue from, StackValue to) {
        boolean success = false;
        for(ILogisticFilter fromFilter : fromFilters){
            if((success = tryTransferTo(source, fromFilter))){
                break;
            }
        }
        return resultValue(success);
    }


    public StackValue resultValue(boolean success){
        ObjectValue result = new ObjectValue();
        result.put("success", success ? BooleanValue.trueValue : BooleanValue.falseValue);
        return result;
    }

    public boolean tryTransferTo(TileEntity source, ILogisticFilter fromFilter){
        List<IInventory> allInventories = new LazyTileFinder<IInventory>(IInventory.class, TileLogisticCable.class, source).allValues();
        List<IInventory> fromInventories = filterInventories(source, allInventories, fromFilter);

        int[] fromInventoryIndexes = new int[9];
        Arrays.fill(fromInventoryIndexes, -1);

        int[] fromInventorySlotIndexes = new int[9];
        Arrays.fill(fromInventorySlotIndexes, -1);


        for(int craftRowIndex = 0; craftRowIndex < recipe.length ; craftRowIndex++){
            ILogisticFilter[] craftRow = recipe[craftRowIndex];

            craftColIndex:
            for(int craftColIndex = 0 ; craftColIndex < craftRow.length ; craftColIndex++){
                ILogisticFilter craftSlotFilter = craftRow[craftColIndex];
                if(craftSlotFilter == null){
                    continue;
                }



                ItemCounterInnerLogisticFilter fromItemCounter = new ItemCounterInnerLogisticFilterExtract(craftSlotFilter);
                for(int fromInventoryIndex = 0 ; fromInventoryIndex < fromInventories.size() ; fromInventoryIndex++){
                    IInventory fromInventory = fromInventories.get(fromInventoryIndex);
                    int fromItemsCounted = fromItemCounter.count(fromInventory, fromFilter);

                    //TODO if craft slots with the same item have a limit, too many items (at most 8) will be extracted

                    int fromLimit = craftSlotFilter.getAmount() == -1 ? Integer.MAX_VALUE : fromItemsCounted - craftSlotFilter.getAmount();
                    if(fromLimit <= 0){
                        continue; //nope we cannot extract that item from this inventory :(
                    }

                    for(int fromSlotIndex = 0 ; fromSlotIndex < fromInventory.getSizeInventory() ; fromSlotIndex++){
                        ItemStack fromStack = fromInventory.getStackInSlot(fromSlotIndex);
                        if(fromStack != null && craftSlotFilter.passesItemFilter(fromStack)){
                            int previouslyTaken = countPreviouslyTaken(fromInventoryIndexes, fromInventorySlotIndexes, fromInventoryIndex, fromSlotIndex); //maybe earlier slots already took some items from this stack
                            if(fromStack.stackSize - previouslyTaken > 0){ //yay we found a valid stack!
                                int craftSlotIndex = craftRowIndex * 3 + craftColIndex;
                                fromInventoryIndexes[craftSlotIndex] = fromInventoryIndex;
                                fromInventorySlotIndexes[craftSlotIndex] = fromSlotIndex;
                                continue craftColIndex;
                            }
                        }
                    }
                }
                //we haven't found any inventory that contains the item we want, so we cannot craft!
                System.out.println("could not find all items needed");
                return false;

            }

            //if all is well we should now have found valid items for our crafting grid, lets fill it in
            Container dummyContainer = new DummyContainer();
            InventoryCrafting inventoryCrafting = new InventoryCrafting(dummyContainer, 3, 3);

            List<ItemStack> remainder = new ArrayList<ItemStack>();

            for(int i = 0 ; i < 9 ; i++){
                int inventoryIndex = fromInventoryIndexes[i];
                int slotIndex = fromInventorySlotIndexes[i];
                if(inventoryIndex != -1 && slotIndex != -1){
                    IInventory inventory = fromInventories.get(inventoryIndex);
                    ItemStack inventoryStack = inventory.getStackInSlot(slotIndex);

                    Item containerItem = inventoryStack.getItem().getContainerItem();
                    if(containerItem != null){
                        remainder.add(new ItemStack(containerItem, 1));
                    }
                    inventoryCrafting.setInventorySlotContents(i, inventoryStack.copy().splitStack(1));
                }
            }

            ItemStack craftResult = CraftingManager.getInstance().findMatchingRecipe(inventoryCrafting, source.getWorldObj());
            if(craftResult != null){ //we have a valid recipe! now we need to store it somewere..
                remainder.add(craftResult);

                // (stackIndex, toInventoryIndex, toSlotIndex, amount)
                List<Vector4> insertions = new ArrayList<Vector4>();

                //Map<(toSlotIndex, toStackIndex), ItemStack>
                Map<Vector2, ItemStack> virtualInsertions = new HashMap<Vector2, ItemStack>();

                toInsertLoop:
                for(int stackIndex = 0; stackIndex < remainder.size() ; stackIndex++) {
                    ItemStack toInsert = remainder.get(stackIndex).copy();

                    toFilterLoop:
                    for(ILogisticFilter toFilter : toFilters){
                        List<Tuple2<Integer, IInventory>> toInventories = filterInventoriesWithIndex(source, allInventories, toFilter);
                        toInventoryLoop:
                        for (Tuple2<Integer, IInventory> toInventoryWithIndex : toInventories) {
                            int toInventoryIndex = toInventoryWithIndex._1;
                            IInventory toInventory = toInventoryWithIndex._2;
                            int toItemsCounted = InventoryItemCounterInsert.instance.count(toInventory, toFilter);
                            int toLimit = toFilter.getAmount() == -1 ? Integer.MAX_VALUE : toFilter.getAmount() - toItemsCounted;
                            if(toLimit > 0) { //we are allowed to insert #toLimit items

                                for (int toSlotIndex = 0; toSlotIndex < toInventory.getSizeInventory(); toSlotIndex++) {
                                    Vector2 stackPosition = new Vector2(toInventoryIndex, toSlotIndex);
                                    ItemStack toStack = virtualInsertions.get(stackPosition);
                                    if(toStack == null){
                                        ItemStack stackInSlot = toInventory.getStackInSlot(toSlotIndex);
                                        if(stackInSlot != null){
                                            toStack = stackInSlot.copy();
                                            virtualInsertions.put(stackPosition, toStack);
                                        }
                                    }

                                    if ((toStack == null || toInsert.isItemEqual(toStack) && ItemStack.areItemStackTagsEqual(toInsert, toStack)) && toFilter.passesItemFilter(toInsert) && InventoryItemCounterInsert.instance.canUseSlot(toInventory, toSlotIndex, toInsert, toFilter)) {
                                        //we found a slot for this stack (:
                                        int toTransfer = MathUtil.min(toLimit, toInsert.stackSize, toStack != null ? toStack.getMaxStackSize() - toStack.stackSize : Integer.MAX_VALUE);

                                        if(toTransfer > 0){
                                            insertions.add(new Vector4(stackIndex, toInventoryIndex, toSlotIndex, toTransfer));
                                            toLimit -= toTransfer;

                                            if(toStack == null){
                                                toStack = toInsert.copy();
                                                virtualInsertions.put(stackPosition, toStack);
                                            } else{
                                                toStack.stackSize += toTransfer;
                                            }

                                            toInsert.stackSize -= toTransfer;
                                            if(toInsert.stackSize == 0) { // we have inserted the whole stack!
                                                continue toInsertLoop;
                                            }

                                            if(toLimit == 0) { //we are done with this inventory; it has enough items
                                                continue toInventoryLoop;
                                            }

                                        }

                                    }
                                }
                            }
                        }

                    }
                    //we have not found inventories to dump this stack in
                    return false;

                }

                //we now have a spot for all craft result itemstacks, now we need to commit the transaction

                //remove the items from the fromInventories
                for(int i = 0 ; i < 9 ; i++){
                    int fromInventoryIndex = fromInventoryIndexes[i];
                    int fromSlotIndex = fromInventorySlotIndexes[i];
                    if(fromInventoryIndex != -1 && fromSlotIndex != -1){
                        IInventory fromInventory = fromInventories.get(fromInventoryIndex);
                        fromInventory.decrStackSize(fromSlotIndex, 1);
                    }
                }

                //insert the result stacks into the toInventories
                for(Vector4 insertion : insertions){
                    ItemStack stack = remainder.get(insertion.x).splitStack(insertion.u);

                    IInventory inventory = allInventories.get(insertion.y);
                    ItemStack existingStack = inventory.getStackInSlot(insertion.z);
                    if(existingStack == null){
                        inventory.setInventorySlotContents(insertion.z, stack);
                    } else{
                        existingStack.stackSize += insertion.u;
                        inventory.setInventorySlotContents(insertion.z, existingStack);
                    }
                }
                //all done! lets confirm we succeeded
                return true;
            }
        }
        return false;
    }

    private int countPreviouslyTaken(int[] fromInventoryIndexes, int[] fromSlotIndexes, int inventoryIndex, int slotIndex) {
        int count = 0;
        for(int i = 0 ; i < 9 ; i++){
            if(fromInventoryIndexes[i] == inventoryIndex && fromSlotIndexes[i] == slotIndex){
                count++;
            }
        }
        return count;
    }

        public static class TransferrableProvider implements ITransferrableProvider<ObjectValue, StackValue>{
        public static final TransferrableProvider instance = new TransferrableProvider();

        @Override
        public ITransferrable<ObjectValue, StackValue> provide(StackValue from, StackValue to) {
            ObjectValue craftFrom;
            StackValue fromFilterObj;
            ListValue recipeObj;
            if(
                   (craftFrom = StackValues.tryGetObjectOfType("craftFrom", from)) != null
                && (fromFilterObj = StackValues.tryExtractField(StackValue.class, "input", craftFrom)) != null
                && (recipeObj = StackValues.tryExtractField(ListValue.class, "recipe", craftFrom)) != null
            ){
                ILogisticFilter[] fromFilters;
                ILogisticFilter[] toFilters;
                ILogisticFilter[][] recipe;
                ILogisticFilter filter;

                if((filter = LogisticFilterRegistry.instance.getFilter(fromFilterObj)) != null){
                    fromFilters = new ILogisticFilter[]{filter};
                } else if((fromFilters = LogisticFilterRegistry.instance.tryGetFilterList(fromFilterObj)) == null){
                    return null;
                }

                recipe = new ILogisticFilter[recipeObj.value.size()][];
                for(int rowIndex = 0 ; rowIndex < recipe.length ; rowIndex++){
                    StackValue rowValue = recipeObj.value.get(rowIndex);
                    ListValue row;
                    if((row = StackValues.tryExpectType(ListValue.class, rowValue)) != null){
                        ILogisticFilter[] rowFilters = new ILogisticFilter[row.value.size()];
                        recipe[rowIndex] = rowFilters;
                        for(int colIndex = 0 ; colIndex < rowFilters.length ; colIndex++){
                            StackValue elem = row.value.get(colIndex);
                            if(!(elem instanceof NilValue)){ //skip the empty fields in the recipe
                                rowFilters[colIndex] = LogisticFilterItemFilter.FilterProvider.instance.provide(elem);
                                if(rowFilters[colIndex] == null){
                                    return null;
                                }
                            }
                        }
                    } else{
                        return null;
                    }
                }

                if((filter = LogisticFilterRegistry.instance.getFilter(to)) != null){
                    toFilters = new ILogisticFilter[]{filter};
                } else if((toFilters = LogisticFilterRegistry.instance.tryGetFilterList(to)) == null){
                    return null;
                }

                return new TransferrableCraftTo(fromFilters, recipe, toFilters);
            }

            return null;
        }
    }
}
