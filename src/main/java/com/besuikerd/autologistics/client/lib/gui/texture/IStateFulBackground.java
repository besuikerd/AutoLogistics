package com.besuikerd.autologistics.client.lib.gui.texture;

import com.besuikerd.autologistics.client.lib.gui.texture.scalable.IScalableTexture;

/**
 * texture that changes depending on state
 * @author Besuikerd
 *
 */
public interface IStateFulBackground<E> {
	IScalableTexture backgroundForState(E state);
}
