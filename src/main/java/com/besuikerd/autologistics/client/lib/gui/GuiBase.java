package com.besuikerd.autologistics.client.lib.gui;
import com.besuikerd.autologistics.client.lib.gui.event.CompositeEventHandler;
import com.besuikerd.autologistics.client.lib.gui.event.EventHandle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

import com.besuikerd.autologistics.client.lib.gui.element.Element;
import com.besuikerd.autologistics.client.lib.gui.element.ElementRootContainer;
import com.besuikerd.autologistics.client.lib.gui.event.EventHandler;
import com.besuikerd.autologistics.client.lib.gui.event.IEventHandler;
import com.besuikerd.autologistics.client.lib.gui.layout.VerticalLayout;

public class GuiBase implements IEventHandler{
	
	protected ElementRootContainer root;
	protected CompositeEventHandler eventHandler;

	public GuiBase() {
		this.root = new ElementRootContainer();
		root.setEventHandler(this);
		this.eventHandler = new CompositeEventHandler();
		bindEventHandler(new EventHandler(this));
		root.layout(new VerticalLayout()).padding(5);
	}

	private static final ResourceLocation bg = new ResourceLocation("textures/gui/demo_background.png");

	/**
	 * Override this method to attach Elements to the {@link #root} container
	 */
	public void init(){}
	
	public void handleMouseInput(int mouseX, int mouseY){
		root.handleMouseInput(mouseX, mouseY);
	}
	
	public ElementRootContainer getRoot() {
		return root;
	}
	
	public boolean handleKeyboardInput(){
		try { //TODO remove this
			return root.handleKeyboardInput() && Keyboard.getEventKey() != Keyboard.KEY_ESCAPE; //if the root element consumes input, do not let others handle keyboard input
		} catch(Exception e){
			e.printStackTrace();
			Minecraft.getMinecraft().thePlayer.closeScreen();
			return false;
		}
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
		try { //TODO remove this
			root.draw();
		} catch(Exception e){
			Minecraft.getMinecraft().thePlayer.closeScreen();
			e.printStackTrace();
		}
	}

	@Override
	public void post(String name, Element e, Object... args) {
		root.onEvent(name, args, e);
		eventHandler.post(name, e, args);
		onEvent(name, e, args);
	}
	
	public void onEvent(String name, Element e, Object... args){
		
	}

	public void bindEventHandler(IEventHandler handler){
		eventHandler.register(handler);
	}
}
