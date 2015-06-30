package com.besuikerd.autologistics.client.gui.texture;


import com.besuikerd.autologistics.common.lib.util.tuple.Vector4;

public interface IBorderTexture {
	Vector4 edgeTop();
	Vector4 edgeRight();
	Vector4 edgeBottom();
	Vector4 edgeLeft();
	
	Vector4 cornerTL();
	Vector4 cornerTR();
	Vector4 cornerBR();
	Vector4 cornerBL();
}
