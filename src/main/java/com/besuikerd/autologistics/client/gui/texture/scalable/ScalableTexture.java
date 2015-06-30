package com.besuikerd.autologistics.client.gui.texture.scalable;


import com.besuikerd.autologistics.common.lib.util.tuple.Vector4;

public enum ScalableTexture implements IScalableTexture{
	STYLED_CONTAINER(new Vector4(232, 6, 249, 23), new Vector4(186, 0, 249, 2), new Vector4(253, 0, 255, 63), new Vector4(186, 3, 249, 5), new Vector4(250, 0, 252, 63), new Vector4(242, 24, 245, 27), new Vector4(246, 24, 249, 27), new Vector4(246, 28, 249, 31), new Vector4(242, 28, 245, 31)),
	SLOT(new Vector4(55, 1, 70, 16), new Vector4(55, 0, 70, 0), new Vector4(71, 1, 71, 16), new Vector4(55, 17, 70, 17), new Vector4(54, 1, 54, 16), new Vector4(54, 0, 54, 0), new Vector4(71, 0, 71, 0), new Vector4(71, 17, 71, 17), new Vector4(54, 17, 54, 17)),
	SLOT_INVERSE(new Vector4(37, 1, 52, 16), new Vector4(37, 0, 52, 0), new Vector4(53, 1, 53, 16), new Vector4(37, 17, 52, 17), new Vector4(36, 1, 36, 16), new Vector4(36, 0, 36, 0), new Vector4(53, 0, 53, 0), new Vector4(53, 17, 53, 17), new Vector4(36, 17, 36, 17)),
	
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
	
	private ScalableTexture(Vector4 background, Vector4 edgeTop, Vector4 edgeRight, Vector4 edgeBottom, Vector4 edgeLeft, Vector4 cornerTL, Vector4 cornerTR, Vector4 cornerBR, Vector4 cornerBL) {
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
