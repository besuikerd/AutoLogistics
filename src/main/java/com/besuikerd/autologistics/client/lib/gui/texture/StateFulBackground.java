package com.besuikerd.autologistics.client.lib.gui.texture;

import com.besuikerd.autologistics.client.lib.gui.texture.scalable.IScalableTexture;
import com.besuikerd.autologistics.client.lib.gui.texture.scalable.ScalableBackgroundHorizontalScroller;
import com.besuikerd.autologistics.client.lib.gui.texture.scalable.ScalableTextureButton;
import com.besuikerd.autologistics.client.lib.gui.texture.scalable.ScalableTextureSolidButton;
import com.besuikerd.autologistics.client.lib.gui.texture.scalable.ScalableTextureVerticalScroller;


public enum StateFulBackground implements IStateFulBackground<ElementState>{
	BUTTON{
		@Override
		public IScalableTexture backgroundForState(ElementState state) {
			switch(state){
				case DISABLED:
					return ScalableTextureButton.DISABLED;
				case HOVERING:
					return ScalableTextureButton.HOVERING;
				case ACTIVATED:
					return ScalableTextureButton.ACTIVATED;
				default:
					return ScalableTextureButton.NORMAL;
			}
		}
	},
	
	SOLID_BUTTON{
		@Override
		public IScalableTexture backgroundForState(ElementState state) {
			switch(state){
				case DISABLED:
					return ScalableTextureSolidButton.DISABLED;
				case ACTIVATED:
					return ScalableTextureSolidButton.ACTIVATED;
				default:
					return ScalableTextureSolidButton.NORMAL;
			}
		}
	},
	
	SCROLLER_VERTICAL{
		@Override
		public IScalableTexture backgroundForState(ElementState state) {
			switch(state){
				case DISABLED:
					return ScalableTextureVerticalScroller.DISABLED;
				case ACTIVATED:
					return ScalableTextureVerticalScroller.ACTIVATED;
				default:
					return ScalableTextureVerticalScroller.NORMAL;
			}
		}
	},
	
	SCROLLER_HORIZONTAL{
		public IScalableTexture backgroundForState(ElementState state) {
			switch(state){
				case DISABLED:
					return ScalableBackgroundHorizontalScroller.DISABLED;
				case ACTIVATED:
					return ScalableBackgroundHorizontalScroller.ACTIVATED;
				default:
					return ScalableBackgroundHorizontalScroller.NORMAL;
			}
		}
	}
	;

	@Override
	public IScalableTexture backgroundForState(ElementState state) {
		throw new UnsupportedOperationException("inner classes should provide an implementation");
	}
}
