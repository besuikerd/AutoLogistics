package com.besuikerd.autologistics.common.lib.util.tuple;
import static com.besuikerd.autologistics.common.lib.util.tuple.Vector2.*;

public class Vector4 extends Tuple4<Integer, Integer, Integer, Integer>{
	public final int x;
	public final int y;
	public final int z;
	public final int u;
	
	public Vector4(Integer _1, Integer _2, Integer _3, Integer _4) {
		super(_1, _2, _3, _4);
		this.x = _1;
		this.y = _2;
		this.z = _3;
		this.u = _4;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof  Vector4){
			Vector4 other = (Vector4) obj;
			return other.x == x && other.y == y && other.z == z && other.u == u;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return x * PRIME_1 ^ y * PRIME_2 * PRIME_3 ^ z * PRIME_4 ^ u;
	}
}
