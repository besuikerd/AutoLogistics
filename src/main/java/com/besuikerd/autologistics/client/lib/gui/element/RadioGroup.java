package com.besuikerd.autologistics.client.lib.gui.element;

import java.util.ArrayList;
import java.util.List;


public class RadioGroup{

	protected int selectedIndex = -1;
	protected List<ElementRadioButton> buttons;
	
	public RadioGroup() {
		buttons = new ArrayList<ElementRadioButton>();
	}
	
	public void radioSelected(ElementRootContainer root, ElementRadioButton radio){
		int radioIndex = -1;
		if((radioIndex = buttons.indexOf(radio)) != -1){
			if(selectedIndex >= 0 && selectedIndex < buttons.size()){
				buttons.get(selectedIndex).toggleOff(root);
			}
			radio.toggleOn(root);
			this.selectedIndex = radioIndex;
		}
	}

	public RadioGroup add(ElementRadioButton button){
		button.group = this;
		buttons.add(button);
		if(selectedIndex == -1){
		}
		return this;
	}
	
	public RadioGroup add(ElementRadioButton... buttons){
		for(ElementRadioButton b : buttons){
			add(b);
		}
		return this;
	}
	
	public void remove(ElementRadioButton radio){
		radio.group = null;
		buttons.remove(radio);
		if(selectedIndex > buttons.size() - 1){
			selectedIndex = buttons.size() - 1;
		}
	}
	
}
