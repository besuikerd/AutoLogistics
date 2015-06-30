package com.besuikerd.autologistics.client.gui.element.adapter;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.besuikerd.autologistics.client.gui.element.Element;

public abstract class BaseElementAdapter<E> implements IElementAdapter{
	protected List<E> elements;
	protected Set<InvalidationListener> listeners;

	public BaseElementAdapter(List<E> elements) {
		this.elements = new SynchronizedList(elements);
		this.listeners = new HashSet<InvalidationListener>();
	}
	
	public BaseElementAdapter(){
		this(new ArrayList<E>());
	}
	
	@Override
	public int getElementCount() {
		return elements.size();
	}
	
	@Override
	public Element getElementAt(int index) {
		return createElementAt(elements.get(index), index);
	}
	
	public void add(E element){
		elements.add(element);
	}
	
	public void remove(E element){
		elements.remove(element);
	}
	
	public void clear(){
		elements.clear();
	}
	
	private void notifyInvalidation(){
		for(InvalidationListener listener : listeners){
			listener.onInvalidation();
		}
	}
	
	public List<E> synchronizedList(){
		return elements;
	}
	
	@Override
	public void addInvalidationListener(InvalidationListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeInvalidationListener(InvalidationListener listener) {
		listeners.remove(listener);
	}
	
	public abstract Element createElementAt(E data, int index);
	
	/**
	 * notifies all listeners once an element in this list changes
	 * @author Besuikerd
	 *
	 */
	private class SynchronizedList extends AbstractList<E>{

		protected List<E> delegate;
		
		public SynchronizedList(List<E> delegate) {
			this.delegate = delegate;
		}

		@Override
		public E get(int index) {
			return delegate.get(index);
		}

		@Override
		public int size() {
			return delegate.size();
		}
		
		@Override
		public void add(int index, E element) {
			delegate.add(index, element);
			notifyInvalidation();
		}
		
		
		@Override
		public E remove(int index) {
			E removed = delegate.remove(index);
			if(removed != null){
				notifyInvalidation();
			}
			return removed;
		}
		
		@Override
		public void clear() {
			delegate.clear();
			notifyInvalidation();
		}
	}
}
