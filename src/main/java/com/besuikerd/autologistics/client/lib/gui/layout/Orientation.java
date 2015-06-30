package com.besuikerd.autologistics.client.lib.gui.layout;

public enum Orientation {
	HORIZONTAL,
	VERTICAL
	
	;
	
	public boolean isVertical(){
		return this == VERTICAL;
	}
	
	public boolean isHorizontal(){
		return this == HORIZONTAL;
	}
}
