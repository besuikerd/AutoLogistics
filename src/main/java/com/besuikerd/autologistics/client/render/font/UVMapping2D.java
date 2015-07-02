package com.besuikerd.autologistics.client.render.font;

public class UVMapping2D {
    public final int x;
    public final int y;
    public final int u;
    public final int v;

    public UVMapping2D(int x, int y, int u, int v) {
        this.x = x;
        this.y = y;
        this.u = u;
        this.v = v;
    }

    @Override
    public String toString() {
        return String.format("UVMapping(%d,%d,%d,%d)", x, y, u, v);
    }
}
