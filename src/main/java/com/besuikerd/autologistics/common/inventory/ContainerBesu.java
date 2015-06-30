package com.besuikerd.autologistics.common.inventory;

import java.util.Iterator;

import com.besuikerd.autologistics.common.BLogger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * container that has automated container logic for a
 * {@link TileEntityInventory}. This container will be bound to a
 * TileEntityInventory upon creation from which it can extract information
 * regarding the inventory slots that are stored in the TileEntity.
 * 
 * @author Besuikerd
 * 
 */
public class ContainerBesu extends Container {

	protected Inventory inventory;
	
	/**
	 * binds the given TileEntity to this container, from which it will extract
	 * information regarding inventory slots
	 * 
	 * @param inventory
	 * @param player
	 */
	public void bindInventory(Inventory inventory, EntityPlayer player) {
		this.inventory = inventory;
		
		int counter = 0;
		int playerCounterInv = 9;
		int playerCounterHotbar = 0;
		for(InventoryGroup group : inventory.getGroups()){
			for(InventoryStack stack : group.getStacks()){
				if(group.getName().equals(InventoryGroup.PLAYER_INVENTORY)){
					addSlotToContainer(new Slot(player.inventory, playerCounterInv++, 0, 0));
				} else if(group.getName().equals(InventoryGroup.PLAYER_HOTBAR)){
					addSlotToContainer(new Slot(player.inventory, playerCounterHotbar++, 0, 0));
				} else {
					addSlotToContainer(new SlotBesu(inventory, counter++));
				}
			}
		}
		return;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotIndex) {
		Slot slot = (Slot) inventorySlots.get(slotIndex);
		ItemStack stack = slot.getStack();

		if (stack == null) {
			return null;
		}

		ItemStack oldStack = stack.copy();
		if (!tryMerge(stack, slotIndex)) {
			return null;
		}
		if (stack.stackSize == 0) {

			slot.putStack(null);
		}

		if (oldStack.stackSize != stack.stackSize) {
			return null;
		}

		slot.onPickupFromSlot(par1EntityPlayer, stack);

		return stack;
	}

	/**
	 * try to merge the given stack somewhere in the inventory
	 * 
	 * @param stack
	 * @param slotIndex
	 * @return
	 */
	protected boolean tryMerge(ItemStack stack, int slotIndex) {
		boolean merged = false;
		InventoryStack invStack = inventory.getInventoryStackAt(slotIndex);
		
		Iterator<String> iterator = invStack.getGroup().getShiftGroups().iterator();
		BLogger.debug(invStack.getGroup().getShiftGroups());
		while(!merged && iterator.hasNext()){
			InventoryGroup group = inventory.getGroup(iterator.next());
			merged = mergeItemStack(stack, group.getSlotOffset(), group.getSlotOffset() + group.getSize(), group.getShiftStart() == StartPosition.END);
		}
		return merged;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	public Inventory inventory(){
		return inventory;
	}
}
