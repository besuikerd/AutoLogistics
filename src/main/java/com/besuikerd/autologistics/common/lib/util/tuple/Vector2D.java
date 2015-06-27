package com.besuikerd.autologistics.common.lib.util.tuple;

public class Vector2D extends Tuple2<Double, Double>{
	public final double x;
	public final double y;
	
	public Vector2D(double _1, double _2) {
		super(_1, _2);
		this.x = _1;
		this.y = _2;
	}
	
	public double dot(Vector2D other){
		return x * other.x + y * other.y;
	}
	
	public double magnitude(){
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public double distance(Vector2D other){
		return Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2));
	}
	
	public Vector2D subtract(Vector2D other){
		return new Vector2D(x - other.x, y - other.y);
	}
	
	public Vector2D add(Vector2D other){
		return new Vector2D(x + other.x, y + other.y);
	}
	
	public Vector2D multiply(double scalar){
		return new Vector2D(x * scalar, y * scalar);
	}
	
	public Vector2D divide(double scalar){
		return new Vector2D(x / scalar, y / scalar);
	}

	public Vector2D normalize(){
		double magnitude = magnitude();
		return new Vector2D(x / magnitude, y / magnitude);
	}
	
	public Vector2D flatten(Vector2D other){
		double distance = distance(other);
		return new Vector2D((x - other.x) / distance, (y - other.y) / distance);
	}
}
