package com.besuikerd.autologistics.client.lib.gui.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandle {
	String value();
}