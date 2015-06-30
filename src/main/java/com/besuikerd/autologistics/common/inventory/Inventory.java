package com.besuikerd.autologistics.common.inventory;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.besuikerd.autologistics.common.lib.collection.IntList;
import com.besuikerd.autologistics.common.lib.data.INBTDeserializable;
import com.besuikerd.autologistics.common.lib.data.INBTSerializable;
import com.besuikerd.autologistics.common.lib.util.ArrayUtil;
import com.besuikerd.autologistics.common.lib.util.INamed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;


public class Inventory implements INamed, ISidedInventory, INBTSerializable, INBTDeserializable{
	
	protected List<InventoryStack> stacks;

	protected Map<String, InventoryGroup> groups;

	protected IntList[] accessibleSides;

	protected String name;

	public Inventory() {
		this.name = "besu.inv";
		this.stacks = new ArrayList<InventoryStack>();
		this.groups = new LinkedHashMap<String, InventoryGroup>();
		this.accessibleSides = new IntList[6];
		for(int i = 0 ; i < accessibleSides.length ; i++){
			accessibleSides[i] = IntList.rawIntList();
		}
	}

	@Override
	public String getName() {
		return name;
	}

	public Inventory addGroup(InventoryGroup group){
		group.slotOffset = stacks.size();
		addStacks(group.getStacks());
		groups.put(group.getName(), group);
		return this;
	}
	
	public Inventory addGroups(InventoryGroup... groups){
		for(InventoryGroup group : groups){
			addGroup(group);
		}
		return this;
	}
	
	private void addStacks(Collection<InventoryStack> stacks){
		for(InventoryStack stack : stacks){
			addStack(stack);
		}
	}
	
	private void addStack(InventoryStack stack){
		stacks.add(stack);
		for(EnumFacing facing : stack.getBlockSides()){
			accessibleSides[facing.ordinal()].add(stacks.size() - 1);
		}
	}
	
	public InventoryGroup getGroup(String name){
		return groups.get(name);
	}
	
	public Collection<InventoryGroup> getGroups(){
		return groups.values();
	}
	
	public Set<InventoryGroup> getGroups(String... names){
		Set<InventoryGroup> foundGroups = new HashSet<InventoryGroup>();
		for(String name : names){
			InventoryGroup group = getGroup(name);
			if(name != null){
				foundGroups.add(group);
			}
		}
		return foundGroups;
	}
	
	@Override
	public int getSizeInventory() {
		return stacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return stacks.get(i).stack;
	}
	
	public InventoryStack getInventoryStackAt(int index){
		return stacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int i, int amount) {
		InventoryStack invStack = stacks.get(i);
		ItemStack stack = invStack.stack;
		int toRemove = Math.min(stack.stackSize, amount);
		ItemStack decrStack = stack.copy();
		stack.stackSize -= toRemove;
		decrStack.stackSize = toRemove;
		if(stack.stackSize == 0){
			invStack.stack = null;
		}
		return decrStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(stacks.size() > i){
			ItemStack stack = stacks.get(i).stack;
			if(stack != null){
				stacks.set(i, null);
				return stack;
			}
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		InventoryStack invStack = stacks.get(i);
		if(invStack != null){
			invStack.stack = itemstack;
		}
	}

	@Override
	public String getInventoryName() {
		return name;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void markDirty() {

	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}


	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if(i < stacks.size()){
			InventoryStack invStack = stacks.get(i);
			return invStack.stack == null || invStack.isReal && itemstack.isItemEqual(invStack.stack);
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return accessibleSides[side].rawIntArray();
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int side) {
		if(i < stacks.size()){
			InventoryStack invStack = stacks.get(i);
			return ArrayUtil.contains(invStack.sides, EnumFacing.values()[side]) && (invStack.stack == null || invStack.isReal && itemStack.isItemEqual(invStack.stack));
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int side) {
		if(i < stacks.size()){
			InventoryStack invStack = stacks.get(i);

			//TODO fix
			return ArrayUtil.contains(invStack.sides, EnumFacing.values()[side]) && (invStack.stack == null || invStack.isReal && itemStack.isItemEqual(invStack.stack));
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList items = compound.getTagList("items", net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);
		for(int i = 0 ; i < items.tagCount() ; i++){
			stacks.get(i).readFromNBT(items.getCompoundTagAt(i));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		NBTTagList items = new NBTTagList();
		for(int i = 0 ; i < items.tagCount() ; i++){
			NBTTagCompound item = new NBTTagCompound();
			stacks.get(i).writeToNBT(item);
			items.appendTag(item);
		}
	}
}
