package com.besuikerd.autologistics.client.lib.gui.event;


import com.besuikerd.autologistics.common.lib.util.INamed;

public enum Event implements INamed {
	ADD("add"),
	REMOVE("remove"),
	RESET("reset"),
	
	NULL("")
	;
	
	private String name;
	
	private Event(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public String prefix(String prefix){
		return prefix + name;
	}

	public String suffix(String suffix){
		return suffix + name;
	}
	
	public String wrap(String prefix, String suffix){
		return prefix + name + suffix;
	}
	
	public String getPrefix(String name){
		int index = name.indexOf(getName());
		return index == -1 ? "" : name.substring(0, index);
	}
	
	public String getSuffix(String name){
		int index = name.indexOf(getName());
		return index == -1 ? "" : name.substring(index + getName().length(), name.length());
	}
	
	public boolean hasPrefix(String name){
		return !getPrefix(name).equals("");
	}
	
	public boolean hasSuffix(String name){
		return !getSuffix(name).equals("");
	}
	
	public static Event lookup(String name){
		for(Event e : values()){
			if(name.contains(e.getName())){
				return e;
			}
		}
		return NULL;
	}
}
