package com.besuikerd.autologistics.client.lib.gui.element;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.besuikerd.autologistics.client.lib.gui.layout.DefaultLayout;
import com.besuikerd.autologistics.client.lib.gui.layout.Layout;
import com.besuikerd.autologistics.client.lib.gui.layout.LayoutDimension;

public class ElementContainer extends Element{
	
	protected List<Element> elements;
	
	/**
	 * used to remove elements from this container. Removals are buffered to prevent ConcurrentModificationExceptions
	 */
	protected List<Element> pendingRemovals;
	
	/**
	 * used to add elements to this container. Additions are buffered to prevent ConcurrentModificationExceptions
	 */
	protected List<Element> pendingAdditions;
	
	protected Layout layout;
	
	public ElementContainer(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.elements = new ArrayList<Element>();
		this.pendingRemovals = new ArrayList<Element>();
		this.pendingAdditions = new ArrayList<Element>();
		this.layout = new DefaultLayout();
	}
	
	public ElementContainer(int width, int height){
		this(0, 0, width, height);
	}
	
	public ElementContainer(LayoutDimension width, LayoutDimension height){
		this(0, 0, 0, 0);
		this.widthDimension = width;
		this.heightDimension = height;
	}
	
	public ElementContainer(){
		this(LayoutDimension.WRAP_CONTENT, LayoutDimension.WRAP_CONTENT);
	}

	public ElementContainer add(Element... elements){
		for(Element e : elements){
			e.index = this.elements.size();
			e.parent = this;
			pendingAdditions.add(e);
		}
		return this;
	}
	
	public void clear(){
		elements.clear();
	}
	
	public ElementContainer remove(Element e){
		pendingRemovals.add(e);
		return this;
	}
	
	public ElementContainer remove(int index){
		pendingRemovals.add(elements.get(index));
		return this;
	}
	
	public int getElementCount(){
		return elements.size();
	}
	
	public int indexOf(Element e){
		return elements.indexOf(e);
	}
	
	public Element elementAt(int index){
		return elements.get(index);
	}
	
	@Override
	public void draw() {
		
		for(Element e : elements){
			e.dx = absX();
			e.dy = absY();
		}
		
		super.draw();
		
		//render last element to first element
		for(int i = elements.size() - 1;  i >= 0 ; i--){
			Element e = elements.get(i);
			e.draw();
			e.style();
		}
		
		
	}
	
	@Override
	public void dimension() {
		layout.init(this);
		
		//dimension elements
		for(int i = 0 ; i < elements.size() ; i++){
			Element e = elements.get(i);
			//increment relative coordinates
			e.dimension();
		}
		
		//lay out elements
		for(int i = 0 ; i < elements.size() ; i++){
			Element e = elements.get(i);
			layout.layout(e, i);
		}
		
		if(widthDimension == LayoutDimension.WRAP_CONTENT){
 			this.width = layout.getLaidOutWidth();
		}
		
		if(heightDimension == LayoutDimension.WRAP_CONTENT){
			this.height = layout.getLaidOutHeight();
		}
		
		//align elements
		for(Element e : elements){
			if(e.getAlignment() != null){
				layout.align(e);
			}
			
			
		}
		
		
		
		super.dimension();
	}
	
	@Override
	public void update() {
		super.update();
		
		for(Element e : pendingRemovals){
			e.onRemoved();
			elements.remove(e);
		}
		
		for(Element e : pendingAdditions){			
			elements.add(e);
			e.onAdded();
		}
		
		pendingRemovals.clear();
		pendingAdditions.clear();
		
		for(Element e : elements){
			e.update();
		}
	}
	
	@Override
	public boolean handleMouseInput(int mouseX, int mouseY) {
		boolean consumeMouseInput = super.handleMouseInput(mouseX, mouseY);
		
		for(int i = 0 ; i < elements.size() && !consumeMouseInput ; i++){
			Element e = elements.get(i);
			consumeMouseInput = e.handleMouseInput(mouseX - e.x - getPaddingLeft(), mouseY - e.y - getPaddingTop()) || consumeMouseInput;
		}
		return consumeMouseInput;
	}
	
	@Override
	public boolean handleKeyboardInput() {
		boolean consumeKeyboardInput = super.handleKeyboardInput();
		
		if(!consumeKeyboardInput){
			for(Element e : elements){
				if((consumeKeyboardInput = e.handleKeyboardInput())){
					break;
				}
			}
		}
		
		return consumeKeyboardInput;
	}
	
	@Override
	public void onEvent(String name, Object[] args, Element e) {
		super.onEvent(name, args, e);
		for(Element el : elements){ //delegate events to children
			el.onEvent(name, args, e);
		}
	}
	
	
	
	public ElementContainer layout(Layout layout) {
		this.layout = layout;
		return this;
	}
}
