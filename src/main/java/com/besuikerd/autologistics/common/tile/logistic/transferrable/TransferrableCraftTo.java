package com.besuikerd.autologistics.common.tile.logistic.transferrable;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.lib.inventory.DummyContainer;
import com.besuikerd.autologistics.common.lib.util.MathUtil;
import com.besuikerd.autologistics.common.lib.util.tuple.Vector2;
import com.besuikerd.autologistics.common.lib.util.tuple.Vector3;
import com.besuikerd.autologistics.common.lib.util.tuple.Vector4;
import com.besuikerd.autologistics.common.tile.TileLogisticCable;
import com.besuikerd.autologistics.common.tile.logistic.LazyTileFinder;
import com.besuikerd.autologistics.common.tile.logistic.filter.ILogisticFilter;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterItemFilter;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterRegistry;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.InventoryItemCounterInsert;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.ItemCounterInnerLogisticFilter;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.ItemCounterInnerLogisticFilterExtract;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;

import java.util.*;

public class TransferrableCraftTo extends AbstractTransferrable<ObjectValue, ObjectValue>{
    public static TransferrableCraftTo instance = new TransferrableCraftTo();

    @Override
    public StackValue transferTo(TileEntity source, ObjectValue craftFrom, ObjectValue to) {
        ObjectValue from;
        ListValue recipe;
        ILogisticFilter fromFilter;
        ILogisticFilter toFilter;
        if(
                (from = StackValues.tryExtractField(ObjectValue.class, "input", craftFrom)) != null
                && (recipe = StackValues.tryExtractField(ListValue.class, "recipe", craftFrom)) != null
                && (fromFilter = LogisticFilterRegistry.instance.getFilter(from)) != null
                && (toFilter = LogisticFilterRegistry.instance.getFilter(to)) != null
        ){
            List<IInventory> allInventories = new LazyTileFinder<IInventory>(IInventory.class, TileLogisticCable.class, source).allValues();
            List<IInventory> fromInventories = filterInventories(source, allInventories, fromFilter);
            List<IInventory> toInventories = filterInventories(source, allInventories, toFilter);

            int[] fromInventoryIndexes = new int[9];
            Arrays.fill(fromInventoryIndexes, -1);

            int[] fromInventorySlotIndexes = new int[9];
            Arrays.fill(fromInventorySlotIndexes, -1);

            int row = 0;
            for(StackValue rowValue : recipe.value){

                int column = 0;
                ListValue rowList;
                if((rowList = StackValues.tryExpectType(ListValue.class, rowValue)) != null){

                    craftSlot:
                    for(StackValue craftSlotValue : rowList.value){

                        if(craftSlotValue instanceof NilValue) {
                            column++;
                        } else {
                            ILogisticFilter craftSlotFilter;
                            if((craftSlotFilter = LogisticFilterItemFilter.FilterProvider.instance.provide(craftSlotValue)) != null){
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
                                                int craftSlotIndex = row * 3 + column;
                                                fromInventoryIndexes[craftSlotIndex] = fromInventoryIndex;
                                                fromInventorySlotIndexes[craftSlotIndex] = fromSlotIndex;
                                                System.out.println(fromSlotIndex);
                                                column++;
                                                continue craftSlot;
                                            }
                                        }
                                    }
                                }
                                //we haven't found any inventory that contains the item we want, so we cannot craft!
                                System.out.println("could not find all items needed");
                                return NilValue.instance;
                            }
                        }
                    }
                }
                row++;
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
            if(craftResult != null){
                System.out.println("Craft result: " + craftResult);
                System.out.println("Remainder: " + remainder);

                remainder.add(craftResult);

                System.out.println("Now we need to insert these items: " + remainder);

                // (stackIndex, toInventoryIndex, toSlotIndex, amount)
                List<Vector4> insertions = new ArrayList<Vector4>();

                //Map<(toSlotIndex, toStackIndex), ItemStack>
                Map<Vector2, ItemStack> virtualInsertions = new HashMap<Vector2, ItemStack>();

                toInsertLoop:
                for(int stackIndex = 0; stackIndex < remainder.size() ; stackIndex++) {
                    ItemStack toInsert = remainder.get(stackIndex);

                    toInventoryLoop:
                    for (int toInventoryIndex = 0; toInventoryIndex < toInventories.size(); toInventoryIndex++) {
                        IInventory toInventory = toInventories.get(toInventoryIndex);

                        int toItemsCounted = InventoryItemCounterInsert.instance.count(toInventory, toFilter);
                        int toLimit = toFilter.getAmount() == -1 ? Integer.MAX_VALUE : toFilter.getAmount() - toItemsCounted;
                        if(toLimit > 0) { //we are allowed to insert #fromLimit items

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

                                if ((toStack == null || toFilter.passesItemFilter(toStack) && toInsert.isItemEqual(toStack) && ItemStack.areItemStackTagsEqual(toInsert, toStack)) && InventoryItemCounterInsert.instance.canUseSlot(toInventory, toSlotIndex, toInsert, toFilter)) {
                                    //we found a slot for this stack (:
                                    int toTransfer = MathUtil.min(toLimit, toInsert.stackSize, toStack != null ? toStack.getMaxStackSize() - toStack.stackSize : Integer.MAX_VALUE);

                                    if(toTransfer > 0){
                                        insertions.add(new Vector4(stackIndex, toInventoryIndex, toSlotIndex, toTransfer));
                                        toLimit -= toTransfer;

                                        if(toStack == null){
                                            toStack = toInsert.copy();
                                            virtualInsertions.put(stackPosition, toStack);
                                        }

                                        toStack.stackSize += toTransfer;

                                        if(toLimit == 0) { //we are done with this inventory; it has enough items
                                            continue toInventoryLoop;
                                        }

                                        if(toTransfer == toInsert.stackSize) { // we have inserted the whole stack!
                                            continue toInsertLoop;
                                        }
                                    }

                                }
                            }
                        }
                    }
                    //we have not found inventories to dump this stack in
                    System.out.println("Could not store craft result in an inventory");
                    return NilValue.instance;
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
                    ItemStack stack = remainder.get(insertion.x);

                    IInventory inventory = toInventories.get(insertion.y);
                    ItemStack existingStack = inventory.getStackInSlot(insertion.z);
                    if(existingStack == null){
                        inventory.setInventorySlotContents(insertion.z, stack);
                    } else{
                        existingStack.stackSize += insertion.u;
                        inventory.setInventorySlotContents(insertion.z, existingStack);
                    }
                }

            } else{
                System.out.println("No matching recipe found");
            }
        }
        return NilValue.instance;
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

        public static class TransferrableProvider implements ITransferrableProvider<ObjectValue, ObjectValue>{
        public static final TransferrableProvider instance = new TransferrableProvider();

        @Override
        public ITransferrable<ObjectValue, ObjectValue> provide(StackValue from, StackValue to) {
            return
                   StackValues.tryGetObjectOfType("craftFrom", from) != null
                && TransferrableMoveItems.TransferrableProvider.isItemFilterType(to)
                ? TransferrableCraftTo.instance : null;
        }
    }
}
