package com.besuikerd.autologistics.client.lib.gui.texture.scalable;


import com.besuikerd.autologistics.common.lib.util.tuple.Vector4;

public enum ScalableTextureVerticalScroller implements IScalableTexture{
	NORMAL(new Vector4(74, 1, 81, 2), new Vector4(74, 0, 81, 0), new Vector4(82, 1, 83, 3), new Vector4(74, 3, 81, 4), new Vector4(72, 1, 73, 3), new Vector4(72, 0, 73, 0), new Vector4(82, 0, 83, 0), new Vector4(82, 3, 83, 4), new Vector4(72, 3, 73, 4)),
	ACTIVATED(new Vector4(74, 6, 81, 7), new Vector4(74, 5, 81, 5), new Vector4(82, 6, 83, 8), new Vector4(74, 8, 81, 9), new Vector4(72, 6, 73, 8), new Vector4(72, 5, 73, 5), new Vector4(82, 5, 83, 5), new Vector4(82, 8, 83, 9), new Vector4(72, 8, 73, 9)),
	DISABLED(new Vector4(74, 11, 81, 12), new Vector4(74, 10, 81, 10), new Vector4(82, 11, 83, 13), new Vector4(74, 13, 81, 14), new Vector4(72, 11, 73, 13), new Vector4(72, 10, 73, 10), new Vector4(82, 10, 83, 10), new Vector4(72, 13, 73, 14), new Vector4(82, 13, 83, 14))
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
	
	private ScalableTextureVerticalScroller(Vector4 background, Vector4 edgeTop, Vector4 edgeRight, Vector4 edgeBottom, Vector4 edgeLeft, Vector4 cornerTL, Vector4 cornerTR, Vector4 cornerBR, Vector4 cornerBL) {
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
