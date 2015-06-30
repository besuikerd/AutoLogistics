package com.besuikerd.autologistics.client.lib.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.besuikerd.autologistics.common.inventory.ContainerBesu;
import com.besuikerd.autologistics.common.inventory.TileEntityInventory;

public abstract class GuiTileEntity<T extends TileEntity> extends GuiBase{

	protected T tile;
	protected EntityPlayer player;
	protected World world;
	
	public GuiTileEntity() {
	}

	protected void bindTileEntity(T entity, EntityPlayer player, World world){
		this.tile = entity;
		this.player = player;
		this.world = world;
	}
}
