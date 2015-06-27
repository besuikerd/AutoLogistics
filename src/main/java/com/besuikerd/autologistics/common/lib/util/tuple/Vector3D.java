package com.besuikerd.autologistics.common.lib.util.tuple;

public class Vector3D extends Tuple3<Double, Double, Double>{
	public final double x;
	public final double y;
	public final double z;
	
	public Vector3D(double _1, double _2, double _3) {
		super(_1, _2, _3);
		this.x = _1;
		this.y = _2;
		this.z = _3;
	}
}
