package com.besuikerd.autologistics.client.lib.gui.texture.scalable;


import com.besuikerd.autologistics.common.lib.util.tuple.Vector4;

public enum ScalableBackgroundHorizontalScroller implements IScalableTexture{
	NORMAL(new Vector4(85, 2, 86, 9), new Vector4(85, 0, 87, 1), new Vector4(87, 2, 88, 9), new Vector4(85, 10, 87, 11), new Vector4(84, 2, 84, 9), new Vector4(84, 0, 84, 1), new Vector4(87, 0, 88, 1), new Vector4(87, 10, 88, 11), new Vector4(84, 10, 84, 11)),
	ACTIVATED(new Vector4(90, 2, 91, 9), new Vector4(90, 0, 92, 1), new Vector4(92, 2, 93, 9), new Vector4(90, 10, 92, 11), new Vector4(89, 2, 89, 9), new Vector4(89, 0, 89, 1), new Vector4(92, 0, 93, 1), new Vector4(92, 10, 93, 11), new Vector4(89, 10, 89, 11)),
	DISABLED(new Vector4(95, 2, 96, 9), new Vector4(95, 0, 97, 1), new Vector4(97, 2, 98, 9), new Vector4(95, 10, 97, 11), new Vector4(94, 2, 94, 9), new Vector4(94, 0, 94, 1), new Vector4(97, 0, 98, 1), new Vector4(97, 10, 98, 11), new Vector4(94, 10, 94, 11))
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
	
	private ScalableBackgroundHorizontalScroller(Vector4 background, Vector4 edgeTop, Vector4 edgeRight, Vector4 edgeBottom, Vector4 edgeLeft, Vector4 cornerTL, Vector4 cornerTR, Vector4 cornerBR, Vector4 cornerBL) {
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
