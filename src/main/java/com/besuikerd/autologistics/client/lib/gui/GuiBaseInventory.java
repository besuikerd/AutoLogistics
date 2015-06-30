package com.besuikerd.autologistics.client.lib.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

import com.besuikerd.autologistics.client.lib.gui.element.ElementItemContainerArray;
import com.besuikerd.autologistics.common.inventory.Inventory;
import com.besuikerd.autologistics.common.inventory.InventoryGroup;

/**
 * gui base for a gui with a container
 * @author Besuikerd
 *
 */
public class GuiBaseInventory extends GuiBase{
	protected Inventory inventory;
	
	protected Map<String, ElementItemContainerArray> containers;
	
	public void bindInventory(Inventory inventory){
		this.inventory = inventory;
	}
	
	public GuiBaseInventory() {
		containers = new HashMap<String, ElementItemContainerArray>();
	}
	
	
	public void alignContainers(Container c){
		int counter = 0;
		for(InventoryGroup group : inventory.getGroups()){
			List<Slot> slots = c.inventorySlots.subList(counter, (counter += group.getSize()));
			containers.get(group.getName()).alignSlots(slots);
		}
	}
	
	public void registerGroup(InventoryGroup group, ElementItemContainerArray array){
		containers.put(group.getName(), array);
	}
}
