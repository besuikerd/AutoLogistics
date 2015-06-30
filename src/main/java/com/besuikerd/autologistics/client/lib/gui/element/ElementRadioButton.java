package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.client.lib.gui.event.Trigger;
import com.besuikerd.autologistics.client.lib.gui.styler.ElementStylerTexture;
import com.besuikerd.autologistics.client.lib.gui.texture.Texture;

public class ElementRadioButton extends Element{
	protected boolean toggled;
	protected RadioGroup group;
	
	public ElementRadioButton(RadioGroup group) {
		super(16, 16);
		group.add(this);
		this.group = group;
		this.styler = new ElementStylerTexture(Texture.RADIO_INNER){
			public void style(Element e) {
				if(toggled){
					super.style(e);
				}
			};
		};
	}
	
	@Override
	public void draw() {
		super.draw();
		drawTextureCentered(Texture.RADIO_OUTER);
	}
	
	public void toggle(ElementRootContainer root, boolean toggle){
		this.toggled = toggle;
		onToggled(root, toggle);
	}
	
	public void toggleOn(ElementRootContainer root){
		toggle(root, true);
	}
	
	public void toggleOff(ElementRootContainer root){
		toggle(root, false);
	}
	
	protected void onToggled(ElementRootContainer root, boolean toggle){
		doTrigger(Trigger.TOGGLED, toggle);
	}
	
	@Override
	protected void onReleased(int x, int y, int which) {
		super.onReleased(x, y, which);
		group.radioSelected(getRoot(), this);
	}
	
	@Override
	protected void onRemoved() {
		super.onRemoved();
		group.remove(this);
	}
}
