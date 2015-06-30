package com.besuikerd.autologistics.client.lib.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

/**
 * responsible of glueing together the different Gui components
 * 
 * @author Besuikerd
 * 
 */
public interface IGuiBinder{
	public GuiScreen bind(GuiBase g, Container c, EntityPlayer player, World world, int x, int y, int z);
}