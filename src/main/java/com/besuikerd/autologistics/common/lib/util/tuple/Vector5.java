package com.besuikerd.autologistics.common.lib.util.tuple;

import static com.besuikerd.autologistics.common.lib.util.tuple.Tuple2.*;

public class Vector5 extends Tuple5<Integer, Integer, Integer, Integer, Integer> {
    public final int x;
    public final int y;
    public final int z;
    public final int u;
    public final int v;

    public Vector5(Integer x, Integer y, Integer z, Integer u, Integer v) {
        super(x, y, z, u, v);
        this.x = x;
        this.y = y;
        this.z = z;
        this.u = u;
        this.v = v;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  Vector5){
            Vector5 other = (Vector5) obj;
            return other.x == x && other.y == y && other.z == z && other.u == u && other.v == v;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return x * PRIME_1 ^ y * PRIME_2 ^ z * PRIME_3 ^ u * PRIME_4 ^ v * PRIME_5;
    }
}
