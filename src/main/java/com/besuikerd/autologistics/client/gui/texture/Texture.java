package com.besuikerd.autologistics.client.gui.texture;
import com.besuikerd.autologistics.common.lib.util.tuple.Vector4;
import com.besuikerd.autologistics.common.lib.util.tuple.Vector4;

public enum Texture implements ITexture{
	ARROW_UP(new Vector4(72, 15, 78, 18)),
	ARROW_DOWN(new Vector4(79, 15, 85, 18)),
	ARROW_RIGHT(new Vector4(86, 15, 89, 21)),
	ARROW_LEFT(new Vector4(90, 15, 93, 21)),
	
	CHECK_MARK(new Vector4(123, 0, 130, 7)),
	
	RADIO_OUTER(new Vector4(99, 0, 114, 15)),
	RADIO_INNER(new Vector4(115, 0, 122, 7)),
	
	PROGRESS_ARROW_BG(new Vector4(0, 36, 21, 50)),
	PROGRESS_ARROW_FULL(new Vector4(0, 51, 21, 66)),
	
	PROGRESS_BURN_BG(new Vector4(22, 36, 35, 48)),
	PROGRESS_BURN_FULL(new Vector4(22, 49, 35, 61)),
	;
	
	private Vector4 texture;
	Texture(Vector4 texture) {
		this.texture = texture;
	}
	
	public Vector4 getTexture() {
		return texture;
	}
}
