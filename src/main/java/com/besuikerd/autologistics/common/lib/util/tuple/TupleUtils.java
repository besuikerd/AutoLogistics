package com.besuikerd.autologistics.common.lib.util.tuple;


public class TupleUtils {
    public static final Vector4 nullVector4 = new Vector4(0, 0, 0, 0);

    /**
     * transforms a Vector4 (x1, y1, x2, y2) to (x2 - x1 + 1, y2 - y1 + 1). throws
     * a RuntimeException if y2 > y1 or x2 > x1
     *
     * @param t
     *            Vector4(x1, y1, x2, y2)
     * @return Vector2(x2 - x1 + 1, y2 - y1 + 1)
     */
    public static Vector2 xyDiff(Vector4 t) {
        return new Vector2(xDiff(t), yDiff(t));
    }

    /**
     * transforms a Vector4 (x1, y1, x2, y2) to y2 - y1 + 1. throws a
     * RuntimeException if y2 > y1
     *
     * @param t
     *            Vector4(x1, y1, x2, y2)
     * @return y2 - y1 + 1
     */
    public static int yDiff(Vector4 t) {
        if (t.y > t.u)
            throw new RuntimeException("y2 is greater than y1 in yDiff comparison");
        return t.u - t.y + 1;
    }

    /**
     * transforms a Vector4 (x1, y1, x2, y2) to x2 - x1 + 1. throws a
     * RuntimeException if x2 > x1
     *
     * @param t
     *            Vector4(x1, y1, x2, y2)
     * @return x2 - x1 + 1
     */
    public static int xDiff(Vector4 t) {
        if (t.y > t.u)
            throw new RuntimeException("x2 is greater than x1 in yDiff comparison");
        return t.z - t.x + 1;
    }
}
