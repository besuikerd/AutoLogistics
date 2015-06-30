package com.besuikerd.autologistics.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.besuikerd.autologistics.client.gui.element.Element;
import com.besuikerd.autologistics.client.gui.element.ElementContainer;
import com.besuikerd.autologistics.client.gui.element.ElementRootContainer;
import com.besuikerd.autologistics.client.gui.event.EventHandler;
import com.besuikerd.autologistics.client.gui.event.IEventHandler;
import com.besuikerd.autologistics.client.gui.layout.VerticalLayout;

public class GuiBase implements IEventHandler{
	
	protected ElementRootContainer root;
	protected EventHandler eventHandler;
	
	public GuiBase() {
		this.root = new ElementRootContainer();
		root.setEventHandler(this);
		this.eventHandler = new EventHandler(this);
		root.layout(new VerticalLayout())
		.padding(5);
	}
	
	public void bindEventHandler(Object o){
		eventHandler.setHandlerObject(o);
	}

	private static final ResourceLocation bg = new ResourceLocation("textures/gui/demo_background.png");

	/**
	 * Override this method to attach Elements to the {@link #root} container
	 */
	public void init(){}
	
	public void handleMouseInput(int mouseX, int mouseY){
		root.handleMouseInput(mouseX, mouseY);
	}
	
	public ElementContainer getRoot() {
		return root;
	}
	
	public boolean handleKeyboardInput(){
		return root.handleKeyboardInput() && Keyboard.getEventKey() != Keyboard.KEY_ESCAPE; //if the root element consumes input, do not let others handle keyboard input
		
	}
	
	public void dimension(GuiScreen gui){
		//update all elements before rendering
		root.update();
		
		//dimension all elements in the root container
		root.dimension();
	}
	
	public void center(GuiScreen gui){
		//center the root container
		root.x((gui.width - root.getWidth()) / 2);
		root.y((gui.height - root.getHeight()) / 2);
	}
	
	public void draw(){
		root.draw();
	}

	@Override
	public void post(String name, Element e, Object... args) {
		root.onEvent(name, args, e);
		eventHandler.post(name, e, args);
		onEvent(name, e, args);
	}
	
	public void onEvent(String name, Element e, Object... args){
		
	}
}
