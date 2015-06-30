package com.besuikerd.autologistics.client.gui.styler;

import java.util.ArrayList;
import java.util.List;

import com.besuikerd.autologistics.client.gui.element.Element;

public class ElementStyler implements IElementStyler{
	
	private List<IElementStyler> stylers;
	
	private ElementStyler(List<IElementStyler> stylers){
		this.stylers = stylers;
	}
	
	@Override
	public void style(Element e) {
		for(IElementStyler styler : stylers){
			styler.style(e);
		}
	}
	
	public static class Builder{
		
		private List<IElementStyler> stylers;
		

		public Builder() {
			this.stylers = new ArrayList<IElementStyler>();
		}
		
		public Builder(IElementStyler initial){
			this();
			stylers.add(initial);
		}
		
		public Builder addStyle(IElementStyler styler){
			stylers.add(styler);
			return this;
		}
		
		public IElementStyler build(){
			return new ElementStyler(stylers);
		}
	}
}
