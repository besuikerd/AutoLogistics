package com.besuikerd.autologistics.client.lib.gui.element.adapter;

import java.util.ArrayList;
import java.util.Arrays;

import com.besuikerd.autologistics.client.lib.gui.element.Element;
import com.besuikerd.autologistics.client.lib.gui.element.ElementButton;
import com.besuikerd.autologistics.client.lib.gui.layout.Alignment;

public class ButtonElementAdapter extends BaseElementAdapter<String>{

	public ButtonElementAdapter(String... buttons){
		super(new ArrayList<String>(Arrays.asList(buttons)));
	}
	
	@Override
	public Element createElementAt(String data, int index) {
		return new ElementButton(data).align(Alignment.CENTER);
	}

}
