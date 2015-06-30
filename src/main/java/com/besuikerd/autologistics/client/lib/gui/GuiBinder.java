package com.besuikerd.autologistics.client.lib.gui;

import java.util.Set;

import com.besuikerd.autologistics.client.lib.gui.event.IEventHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.besuikerd.autologistics.common.inventory.ContainerBesu;
import com.besuikerd.autologistics.common.inventory.TileEntityInventory;
import com.google.common.collect.Sets;

public enum GuiBinder implements IGuiBinder{
	
	DEFAULT{
		@Override
		public GuiScreen bind(GuiBase g, Container c, EntityPlayer player, World world, int x, int y, int z) {
			if(g != null){
				if(g instanceof GuiBaseInventory && c instanceof ContainerBesu){
					((GuiBaseInventory) g).bindInventory(((ContainerBesu) c).inventory());
				}
				g.init();
			}
			return super.bind(g, c, player, world, x, y, z);
		}
	},
	
	TILE_ENTITY(DEFAULT){
		@Override
		public GuiScreen bind(GuiBase g, Container c, EntityPlayer player, World world, int x, int y, int z) {
			TileEntity tile;
			if(g != null && (tile =  world.getTileEntity(x, y, z)) != null){
				if(tile instanceof IEventHandler){
					g.setEventHandler((IEventHandler) tile);
				} else{
					g.bindEventHandler(tile);
				}
				if(c instanceof ContainerBesu && tile instanceof TileEntityInventory){
					((ContainerBesu) c).bindInventory(((TileEntityInventory) tile).getInventory(), player);
				}
			}
			return super.bind(g, c, player, world, x, y, z);
		}
	},
	
	
	;

	private Set<IGuiBinder> binders;
	
	private GuiBinder(IGuiBinder... binders){
		this.binders = Sets.newHashSet(binders);
	}
	
	private GuiBinder() {
		this(new IGuiBinder[0]);
	}

	@Override
	public GuiScreen bind(GuiBase g, Container c, EntityPlayer player, World world, int x, int y, int z) {
		for(IGuiBinder binder : binders){
			binder.bind(g, c, player, world, x, y, z);
		}

		return g instanceof GuiBaseInventory ? new GuiContainerBesu(c, (GuiBaseInventory) g) : new GuiScreenBesu(g);
	}
}
