package com.besuikerd.autologistics.client.gui;

import java.util.Set;

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
		public void bind(GuiBase g, Container c, EntityPlayer player, World world, int x, int y, int z) {
			super.bind(g, c, player, world, x, y, z);
			if(g != null){
				
				if(g instanceof GuiBaseInventory && c instanceof ContainerBesu){
					((GuiBaseInventory) g).bindInventory(((ContainerBesu) c).inventory());
				}
				
				g.init();
			}
			
			
		}
	},
	
	TILE_ENTITY(DEFAULT){
		@Override
		public void bind(GuiBase g, Container c, EntityPlayer player, World world, int x, int y, int z) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile != null){
				if(g instanceof GuiBase){
					((GuiBase) g).bindEventHandler(tile);
				}
				if(c instanceof ContainerBesu && tile instanceof TileEntityInventory){
					((ContainerBesu) c).bindInventory(((TileEntityInventory) tile).getInventory(), player);
					
				}
			}
			super.bind(g, c, player, world, x, y, z);
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
	public void bind(GuiBase g, Container c, EntityPlayer player, World world, int x, int y, int z) {
		for(IGuiBinder binder : binders){
			binder.bind(g, c, player, world, x, y, z);
		}
	}

	
}
