package com.besuikerd.autologistics.common.lib.util.tuple;

import static com.besuikerd.autologistics.common.lib.util.tuple.Tuple2.*;

public class Tuple4<A, B, C, D> {
	public final A _1;
	public final B _2;
	public final C _3;
	public final D _4;
	
	public Tuple4(A _1, B _2, C _3, D _4) {
		this._1 = _1;
		this._2 = _2;
		this._3 = _3;
		this._4 = _4;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Tuple4){
			Tuple4 tuple = (Tuple4) obj;
			return tuple._1.equals(_1) && tuple._2.equals(_2) && tuple._3.equals(_3) && tuple._4.equals(_4);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return _1.hashCode() * PRIME_1 ^ _2.hashCode() * PRIME_2 ^ _3.hashCode() * PRIME_3 ^ _4.hashCode() * PRIME_4;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s, %s, %s)", _1, _2, _3, _4);
	}
}
