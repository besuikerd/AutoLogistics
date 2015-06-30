package com.besuikerd.autologistics.client.gui.texture;

import com.besuikerd.autologistics.client.gui.texture.scalable.IScalableTexture;

public class StatefulBackgroundWrapper implements IStateFulBackground<ElementState>{

	private IScalableTexture texture;
	
	public StatefulBackgroundWrapper(IScalableTexture texture) {
		this.texture = texture;
	}

	@Override
	public IScalableTexture backgroundForState(ElementState state) {
		return texture;
	}

}
