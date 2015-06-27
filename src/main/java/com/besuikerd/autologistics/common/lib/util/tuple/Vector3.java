package com.besuikerd.autologistics.common.lib.util.tuple;

import static com.besuikerd.autologistics.common.lib.util.tuple.Vector2.*;

public class Vector3 extends Tuple3<Integer, Integer, Integer>{
	public final int x;
	public final int y;
	public final int z;
	public Vector3(Integer _1, Integer _2, Integer _3) {
		super(_1, _2, _3);
		this.x = _1;
		this.y = _2;
		this.z = _3;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof  Vector3){
			Vector3 other = (Vector3) obj;
			return other.x == x && other.y == y && other.z == z;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return x * PRIME_1 ^ y * PRIME_2 * PRIME_3 ^ z;
	}
}
