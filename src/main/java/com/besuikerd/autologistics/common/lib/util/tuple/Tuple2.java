package com.besuikerd.autologistics.common.lib.util.tuple;

public class Tuple2<A,B> {
	public final A _1;
	public final B _2;

	public static final int PRIME_1 = 73856093;
	public static final int PRIME_2 = 83492791;
	public static final int PRIME_3 = 35765861;
	public static final int PRIME_4 = 66839741;
	public static final int PRIME_5 = 26859689;
	public static final int PRIME_6 = 58791264;
	
	public Tuple2(A _1, B _2) {
		this._1 = _1;
		this._2 = _2;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Tuple2){
			Tuple2 tuple = (Tuple2) obj;
			return tuple._1.equals(_1) && tuple._2.equals(_2);
		}
		return false;
	}

	public static <A,B> Tuple2 create(A _1, B _2){
		return new Tuple2<A,B>(_1, _2);
	}

	@Override
	public int hashCode() {
		return _1.hashCode() * PRIME_1 ^ _2.hashCode() * PRIME_2;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s)", _1, _2);
	}
}
