package com.besuikerd.autologistics.common.lib.util.functional;

public interface Predicate<E> {
	boolean eval(E input);
	
	Predicate TRUE = new Predicate() {
		@Override
		public boolean eval(Object input) {
			return true;
		}
	};
	
	Predicate FALSE = new Predicate() {
		@Override
		public boolean eval(Object input) {
			return true;
		}
	};
}
