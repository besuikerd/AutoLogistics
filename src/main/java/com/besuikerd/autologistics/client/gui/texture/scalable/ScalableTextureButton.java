package com.besuikerd.autologistics.client.gui.texture.scalable;


import com.besuikerd.autologistics.common.lib.util.tuple.Vector4;

public enum ScalableTextureButton implements IScalableTexture{
	DISABLED(new Vector4(1, 1, 16, 16), new Vector4(1, 0, 16, 0), new Vector4(17, 1, 17, 16), new Vector4(1, 17, 16, 17), new Vector4(0, 1, 0, 16), new Vector4(0, 0, 0, 0), new Vector4(17, 0, 17, 0), new Vector4(17, 17, 17, 17), new Vector4(0, 17, 0, 17)),
	NORMAL(new Vector4(2, 20, 15, 32), new Vector4(2, 18, 15, 19), new Vector4(16, 20, 17, 32), new Vector4(2, 33, 15, 35), new Vector4(0, 20, 1, 32), new Vector4(0, 18, 1, 19), new Vector4(16, 18, 17, 19), new Vector4(16, 33, 17, 35), new Vector4(0, 33, 1, 35)),
	HOVERING(new Vector4(20, 2, 33, 14), new Vector4(20, 0, 33, 1), new Vector4(34, 2, 35, 14), new Vector4(20, 15, 33, 17), new Vector4(18, 2, 19, 14), new Vector4(18, 0, 19, 1), new Vector4(34, 0, 35, 1), new Vector4(34, 15, 35, 17), new Vector4(18, 15, 19, 17)),
	ACTIVATED(new Vector4(20, 21, 33, 33), new Vector4(20, 18, 33, 20), new Vector4(34, 21, 35, 33), new Vector4(20, 34, 33, 35), new Vector4(18, 21, 19, 33), new Vector4(18, 18, 19, 20), new Vector4(34, 18, 35, 20), new Vector4(34, 34, 35, 35), new Vector4(18, 34, 19, 35));
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
	
	private ScalableTextureButton(Vector4 background, Vector4 edgeTop, Vector4 edgeRight, Vector4 edgeBottom, Vector4 edgeLeft, Vector4 cornerTL, Vector4 cornerTR, Vector4 cornerBR, Vector4 cornerBL) {
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
