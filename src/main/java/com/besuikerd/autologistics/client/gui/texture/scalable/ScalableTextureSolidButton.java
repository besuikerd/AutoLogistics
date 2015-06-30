package com.besuikerd.autologistics.client.gui.texture.scalable;

import com.besuikerd.autologistics.common.lib.util.tuple.Vector4;

public enum ScalableTextureSolidButton implements IScalableTexture{
	NORMAL(ScalableTexture.SLOT_INVERSE),
	ACTIVATED(ScalableTexture.SLOT),
	DISABLED(new Vector4(37, 19, 52, 34), new Vector4(37, 18, 52, 18), new Vector4(53, 19, 53, 34), new Vector4(37, 35, 52, 35), new Vector4(36, 19, 36, 34), new Vector4(36, 18, 36, 18), new Vector4(53, 18, 53, 18), new Vector4(53, 35, 53, 35), new Vector4(36, 35, 36, 35))
	;
	
	protected Vector4 background;
	
	protected Vector4 edgeTop;
	protected Vector4 edgeRight;
	protected Vector4 edgeBottom;
	protected Vector4 edgeLeft;

	protected Vector4 cornerTL;
	protected Vector4 cornerTR;
	protected Vector4 cornerBR;
	protected Vector4 cornerBL;
	
	ScalableTextureSolidButton(Vector4 background, Vector4 edgeTop, Vector4 edgeRight, Vector4 edgeBottom, Vector4 edgeLeft, Vector4 cornerTL, Vector4 cornerTR, Vector4 cornerBR, Vector4 cornerBL) {
		this.background = background;
		this.edgeTop = edgeTop;
		this.edgeRight = edgeRight;
		this.edgeBottom = edgeBottom;
		this.edgeLeft = edgeLeft;
		this.cornerTL = cornerTL;
		this.cornerTR = cornerTR;
		this.cornerBR = cornerBR;
		this.cornerBL = cornerBL;
	}
	
	ScalableTextureSolidButton(IScalableTexture texture){
		this(texture.getTexture(), texture.edgeTop(), texture.edgeRight(), texture.edgeBottom(), texture.edgeLeft(), texture.cornerTL(), texture.cornerTR(), texture.cornerBR(), texture.cornerBL());
	}

	@Override
	public Vector4 getTexture() {
		return background;
	}

	@Override
	public Vector4 edgeTop() {
		return edgeTop;
	}

	@Override
	public Vector4 edgeRight() {
		return edgeRight;
	}

	@Override
	public Vector4 edgeBottom() {
		return edgeBottom;
	}

	@Override
	public Vector4 edgeLeft() {
		return edgeLeft;
	}

	@Override
	public Vector4 cornerTL() {
		return cornerTL;
	}

	@Override
	public Vector4 cornerTR() {
		return cornerTR;
	}

	@Override
	public Vector4 cornerBR() {
		return cornerBR;
	}

	@Override
	public Vector4 cornerBL() {
		return cornerBL;
	}
}
