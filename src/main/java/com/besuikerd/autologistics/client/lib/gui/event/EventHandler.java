package com.besuikerd.autologistics.client.lib.gui.event;

import com.besuikerd.autologistics.client.lib.gui.element.Element;
import com.besuikerd.autologistics.common.lib.util.ArrayUtil;
import com.besuikerd.autologistics.common.lib.util.ReflectUtils;
import com.besuikerd.autologistics.common.lib.util.functional.Predicate;

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
		ReflectUtils.Invokable i = ReflectUtils.getPartialMatchingInvokable(handlerObject, ReflectUtils.getAnnotatedMethods(handlerObject, EventHandle.class, new Predicate<EventHandle>() {
			@Override
			public boolean eval(EventHandle input) {
				return input.value() != null && input.value().equals(name);
			}
		}), args);
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
