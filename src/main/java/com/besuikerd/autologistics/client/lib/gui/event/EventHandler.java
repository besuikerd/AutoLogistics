package com.besuikerd.autologistics.client.lib.gui.event;

import com.besuikerd.autologistics.client.lib.gui.element.Element;
import com.besuikerd.autologistics.common.lib.util.ArrayUtil;
import com.besuikerd.autologistics.common.lib.util.ReflectUtils;
import com.besuikerd.autologistics.common.lib.util.functional.Predicate;
import com.google.common.collect.Sets;

import java.lang.reflect.Method;
import java.util.HashSet;

public class EventHandler implements IEventHandler{
	
	/**
	 * object handling incoming events
	 */
	protected Object handlerObject;
	
	public EventHandler(Object handlerObject) {
		this.handlerObject = handlerObject;
	}

	@Override
	public void post(final String name, Element e, Object... args) {
		args = ArrayUtil.prepend(e, args); //add e to the arguments
		ReflectUtils.Invokable i = ReflectUtils.getPartialMatchingInvokable(handlerObject, ReflectUtils.getMatchingMethods(handlerObject, name), args);
		if(i != null){
			i.invoke();
		} else{
			ReflectUtils.invokePartialMatchingMethod(handlerObject, name, args);
		}
	}

	public void setHandlerObject(Object handlerObject) {
		this.handlerObject = handlerObject;
	}
	
	public Object getHandlerObject() {
		return handlerObject;
	}
}
