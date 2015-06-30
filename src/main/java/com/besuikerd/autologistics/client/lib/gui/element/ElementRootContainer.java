package com.besuikerd.autologistics.client.lib.gui.element;

import org.lwjgl.input.Mouse;

import com.besuikerd.autologistics.client.lib.gui.event.IEventHandler;
import com.besuikerd.autologistics.client.lib.gui.layout.LayoutDimension;
import com.besuikerd.autologistics.client.lib.gui.texture.scalable.ScalableTexture;

public class ElementRootContainer extends ElementStyledContainer{
	protected Element focusedElement;
	protected int scrollMovement;
	protected IEventHandler eventHandler;
	
	public ElementRootContainer() {
		super(LayoutDimension.WRAP_CONTENT, LayoutDimension.WRAP_CONTENT, ScalableTexture.STYLED_CONTAINER);
	}
	
	public ElementRootContainer(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public ElementRootContainer(int width, int height) {
		this(0, 0, width, height);
	}

	public boolean requestFocus(Element element){
		boolean canFocus = true;
		if(focusedElement != null){
			canFocus = focusedElement.onReleaseFocus();
		}
		if(canFocus){
			if(focusedElement != null){
				focusedElement.toggleOff(Element.FOCUSED);
			}
			this.focusedElement = element;
			element.onFocus();
			element.toggleOn(Element.FOCUSED);
		}
		return canFocus;
	}
	
	public void releaseFocus(Element element){
		if(focusedElement != null && focusedElement.equals(element)){
			focusedElement.onReleaseFocus();
			focusedElement.toggleOff(Element.FOCUSED);
			focusedElement = null;
		}
	}
	
	public Element getFocusedElement() {
		return focusedElement;
	}
	
	@Override
	public boolean handleMouseInput(int mouseX, int mouseY) {
		
		this.scrollMovement = Mouse.getDWheel();
		
		boolean consumeMouseInput = false;
		if(focusedElement != null){
			consumeMouseInput = focusedElement.handleMouseInput(mouseX - focusedElement.absX(), mouseY - focusedElement.absY());
		}
		
		if(!consumeMouseInput){
			super.handleMouseInput(mouseX - absX(), mouseY - absY());
		}
		return true; //root element always consumes mouse input
	}
	
	@Override
	public boolean handleKeyboardInput() {
		boolean consumeKeyboardInput = false;
		
		if(focusedElement != null){
			consumeKeyboardInput = focusedElement.handleKeyboardInput();
		}
		if(!consumeKeyboardInput){
			consumeKeyboardInput = super.handleKeyboardInput();
		}
		
		return consumeKeyboardInput;
	}
	
	public void setEventHandler(IEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}
	
	public IEventHandler getEventHandler() {
		return eventHandler;
	}
	
	@Override
	public ElementRootContainer getRoot() {
		return this;
	}
}
