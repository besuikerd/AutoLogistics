package com.besuikerd.autologistics.common.lib.util.tuple;

public class Vector2 extends Tuple2<Integer, Integer>{
	public final int x;
	public final int y;



	public Vector2(Integer _1, Integer _2) {
		super(_1, _2);
		this.x = _1;
		this.y = _2;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof  Vector2){
			Vector2 other = (Vector2) obj;
			return other.x == x && other.y == y;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return x * PRIME_1 ^ y * PRIME_2;
	}

	public Vector2 min(Vector2 other){
		return y < other.y ? this : y > other.y ? other : x < other.x ? this: other;
	}

	public Vector2 max(Vector2 other){
		return min(other) == this ? other : this;
	}

}
