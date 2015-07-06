package com.besuikerd.autologistics.client.lib.gui;

import org.lwjgl.input.Mouse;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public class GuiContainerBesu extends GuiContainer{
	
	protected GuiBaseInventory gui;

	public GuiContainerBesu(Container par1Container, GuiBaseInventory gui) {
		super(par1Container);
		this.gui = gui;
	}
	
	@Override
	public void initGui() {
		
		gui.dimension(this);
		
		//recalculate size of the root container
		xSize = gui.getRoot().getWidth();
		ySize = gui.getRoot().getHeight();
		
		gui.center(this);
		
		super.initGui();
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		gui.dimension(this);
		gui.center(this);
		super.drawScreen(par1, par2, par3);
		
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		this.xSize = gui.getRoot().getWidth();
		this.ySize = gui.getRoot().getHeight();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		
		
		gui.alignContainers(inventorySlots);
		gui.draw();
	}
	
	@Override
	protected void keyTyped(char key, int code) {
		super.keyTyped(key, code);
	}
	
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		
        int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        
        //delegate mouse input to root container
        gui.handleMouseInput(x, y);
    }
	
	@Override
	public void handleKeyboardInput() {
		if(!gui.handleKeyboardInput()){
			super.handleKeyboardInput();
		}
	}

	public void onGuiClosed(){

	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
