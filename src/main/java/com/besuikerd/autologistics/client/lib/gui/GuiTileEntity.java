package com.besuikerd.autologistics.client.lib.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.besuikerd.autologistics.common.inventory.ContainerBesu;
import com.besuikerd.autologistics.common.inventory.TileEntityInventory;

public abstract class GuiTileEntity<T> extends GuiBase{

	private Class<T> cls;
	protected T tile;
	protected EntityPlayer player;
	protected World world;
	
	public GuiTileEntity(Class<T> cls) {
		this.cls = cls;
	}

	protected void bindTileEntity(T entity, EntityPlayer player, World world){
		this.tile = entity;
		this.player = player;
		this.world = world;
	}

	public Class<T> getTileClass() {
		return cls;
	}
}
