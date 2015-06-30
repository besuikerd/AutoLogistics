package com.besuikerd.autologistics.common.lib.util.tuple;

import static com.besuikerd.autologistics.common.lib.util.tuple.Tuple2.*;

public class Tuple5<A, B, C, D, E> {
	public final A _1;
	public final B _2;
	public final C _3;
	public final D _4;
	public final E _5;
	
	public Tuple5(A _1, B _2, C _3, D _4, E _5) {
		this._1 = _1;
		this._2 = _2;
		this._3 = _3;
		this._4 = _4;
		this._5 = _5;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Tuple5){
			Tuple5 tuple = (Tuple5) obj;
			return tuple._1.equals(_1) && tuple._2.equals(_2) && tuple._3.equals(_3) && tuple._4.equals(_4) && tuple._5.equals(_5);
		}
		return false;
	}

	public static <A,B,C,D,E> Tuple5 create(A _1, B _2, C _3, D _4, E _5){
		return new Tuple5<A,B,C,D,E>(_1, _2, _3, _4, _5);
	}

	@Override
	public int hashCode() {
		return _1.hashCode() * PRIME_1 ^ _2.hashCode() * PRIME_2 ^ _3.hashCode() * PRIME_3 ^ _4.hashCode() * PRIME_4 * _5.hashCode() * PRIME_5;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s, %s, %s, %s)", _1, _2, _3, _4, _5);
	}
}
