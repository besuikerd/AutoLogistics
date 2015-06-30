package com.besuikerd.autologistics.common.inventory;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import com.besuikerd.autologistics.common.inventory.InventoryStack.StackBuilder;
import com.besuikerd.autologistics.common.lib.data.INBTDeserializable;
import com.besuikerd.autologistics.common.lib.data.INBTSerializable;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class InventoryGroup {
	
	/**
	 * name of the group for a player inventory
	 * @see {@link #playerInventory()}
	 */
	public static final String PLAYER_INVENTORY = "player_inventory";
	public static final String PLAYER_HOTBAR = "player_hotbar";
	public static final String[] PLAYER_INVENTORY_SHIFTGROUPS = {PLAYER_HOTBAR, PLAYER_INVENTORY};
	
	protected String name;
	
	protected int slotOffset;
	
	protected Set<InventoryStack> stacks;
	protected Set<String> shiftGroups;
	protected StartPosition shiftStart;
	
	public InventoryGroup(){
		this(null);
	}
	
	public InventoryGroup(String name) {
		this(name, new LinkedHashSet<InventoryStack>());
	}
	
	public InventoryGroup(String name, int amount, InventoryStack.StackBuilder builder){
		this(name);
		addStacks(amount, builder);
	}
	
	public InventoryGroup(String name, int amount){
		this(name, amount, new StackBuilder());
	}
	
	public InventoryGroup(String name, Set<InventoryStack> stacks){
		this.name = name;
		this.stacks = stacks;
		this.shiftGroups = new LinkedHashSet<String>();
	}	
	
	public void addStack(InventoryStack stack){
		stack.group = this;
		stacks.add(stack);
	}
	
	public void addStacks(int amount, InventoryStack.StackBuilder builder){
		for(int i = 0 ; i < amount ; i++){
			addStack(builder.build());
		}
	}
	
	public void removeStack(InventoryStack stack){
		if(stacks.remove(stack)){
			stack.group = null;
		}
	}
	
	public void clear(){
		Iterator<InventoryStack> iterator = stacks.iterator();
		while(iterator.hasNext()){
			InventoryStack stack = iterator.next();
			stack.group = null;
			iterator.remove();
		}
	}
	
	public Set<InventoryStack> getStacks() {
		return stacks;
	}

	public Set<String> getShiftGroups() {
		return shiftGroups;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSize(){
		return stacks.size();
	}
	
	public StartPosition getShiftStart() {
		return shiftStart;
	}
	
	public InventoryGroup setShiftStart(StartPosition shiftStart) {
		this.shiftStart = shiftStart;
		return this;
	}
	
	public InventoryGroup shiftGroups(String... groups){
		for(String name : groups){
			shiftGroups.add(name);
		}
		return this;
	}
	
	public static InventoryGroup playerInventory(){
		return new InventoryGroup(PLAYER_INVENTORY, 27, new StackBuilder());
	}
	
	public static InventoryGroup playerInventoryHotbar(){
		return new InventoryGroup(PLAYER_HOTBAR, 9, new StackBuilder());
	}
	
	public int getSlotOffset() {
		return slotOffset;
	}
}
