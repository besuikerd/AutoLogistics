package com.besuikerd.autologistics.common.lib.collection;

import java.util.AbstractList;

/**
 * List containing int values. Backed by an int[]. Can retrieve the int[] data in constant time. Tradeoff is that the insertion and deletion of elements are more expensive (O(n))
 * @author Nicker
 *
 */
public class IntList extends AbstractList<Integer>{

	private int[] list;
	private int size;
	
	private int expansionSize = 64;
	
	public IntList(int[] list) {
		this.list = list;
		this.size = list.length;
	}
	
	public IntList() {
		this(64);
	}
	
	public IntList(int initialCapacity) {
		this.list = new int[initialCapacity];
	}
	
	public void setExpansionSize(int expansionSize) {
		this.expansionSize = expansionSize;
	}

	@Override
	public Integer get(int index) {
		return list[index];
	}
	
	@Override
	public void add(int index, Integer element) {
		ensureCapacity(Math.max(0, index - size) + 1);
		if(index == size - 1){
			list[index] = element;
		} else{
			System.out.println("size " + size);
			System.arraycopy(list, index, list, index + 1, size - index);
			list[index] = element;
		}
	}
	
	@Override
	public Integer remove(int index) {
		--size;
		int toRemove = list[index];
		if(expansionSize == 1){
			int[] newList = new int[size];
			System.arraycopy(list, 0, newList, 0, index);
			System.arraycopy(list, index + 1, newList, index, newList.length - index);
			this.list = newList;
		} else{
			System.arraycopy(list, index + 1, list, index, size - index);
		}
		return toRemove;
	}
	
	@Override
	public boolean remove(Object o) {
		for(int i = 0 ; i < size ; i++){
			if(o.equals(list[i])){
				remove(i);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Integer set(int index, Integer element) {
		int el = list[index];
		list[index] = element;
		return el;
	}
	
	@Override
	public int size() {
		return size;
	}

	public int[] rawIntArray(){
		return list;
	}
	
	public int[] toIntArray(){
		int[] array = new int[size];
		System.arraycopy(list, 0, array, 0, size);
		return array;
	}
	
	private void ensureCapacity(int count){
		if(list.length < size + count){
			int[] newList = new int[list.length + Math.max(count, expansionSize)];
			//System.out.println("increased size " + newList.length);
			System.arraycopy(list, 0, newList, 0, list.length);
			list = newList;
		}
		
		size += count;
	}
	
	/**
	 * IntList backed by an int[] that is only as big as it needs to. Advantageous for constant time acces of the int[]
	 * @return
	 */
	public static IntList rawIntList(){
		IntList list = new IntList(0);
		list.expansionSize = 1;
		return list;
	}
}
