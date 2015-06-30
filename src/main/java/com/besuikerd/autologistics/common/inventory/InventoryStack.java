package com.besuikerd.autologistics.common.inventory;

import com.besuikerd.autologistics.common.lib.data.INBTDeserializable;
import com.besuikerd.autologistics.common.lib.data.INBTSerializable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.util.EnumFacing;

public class InventoryStack implements INBTDeserializable, INBTSerializable{
	protected ItemStack stack;
	protected int stackLimit;
	protected EnumFacing[] sides;
	protected boolean isReal;
	
	protected InventoryGroup group;
	
	public ItemStack getStack() {
		return stack;
	}
	
	public EnumFacing[] getBlockSides() {
		return sides;
	}

	public int getStackLimit() {
		return stackLimit;
	}
	
	public boolean isReal() {
		return isReal;
	}
	
	public InventoryGroup getGroup() {
		return group;
	}


	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.getBoolean("hasStack")) {
			this.stack = ItemStack.loadItemStackFromNBT(compound);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("hasStack", stack != null);
		if(stack != null){
			stack.writeToNBT(compound);
		}
	}
	
	public static class StackBuilder{
		protected boolean isReal;
		protected EnumFacing[] sides;
		protected int stackLimit;
		
		public StackBuilder() {
			this.isReal = true;
			this.sides = EnumFacing.values();
			this.stackLimit = 64;
		}
		
		public StackBuilder real(boolean isReal){
			this.isReal = isReal;
			return this;
		}
		
		public StackBuilder sides(EnumFacing... sides){
			this.sides = sides;
			return this;
		}
		
		public StackBuilder stackLimit(int stackLimit){
			this.stackLimit = stackLimit;
			return this;
		}
		
		public InventoryStack build(){
			InventoryStack inv = new InventoryStack();
			inv.isReal = this.isReal;
			inv.sides = sides;
			inv.stackLimit = this.stackLimit;
			return inv;
		}
	}
}
