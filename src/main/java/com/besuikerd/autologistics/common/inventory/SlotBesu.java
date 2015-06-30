package com.besuikerd.autologistics.common.inventory;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBesu extends Slot {
	
	protected Inventory inventory;
	
	public SlotBesu(Inventory inventory, int slotIndex, int xDisplay, int yDisplay){
		super(inventory, slotIndex, xDisplay, yDisplay);
		this.inventory = inventory;
		this.xDisplayPosition = -1000;
		this.yDisplayPosition = -1000;
	}
	
	public SlotBesu(Inventory inventory, int slotIndex){
		this(inventory, slotIndex, 0, 0);
	}

	@Override
	public void onSlotChanged() {
		inventory.markDirty();
	}
}
