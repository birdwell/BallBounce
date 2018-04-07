package edu.ou.cs.cg.homework;

public class Vector {
	private final double x;
	private final double y;
	private final double magnitude;
	private Point startPoint;
	private Point endPoint;

	public Vector() {
		x = y = (double) 0.0;
		this.magnitude = this.calcMagnitude();
	}

	public Vector(double x, double y, double magnitude) {
		this.x = x;
		this.y = y;

		this.startPoint = null;
		this.endPoint = null;

		this.magnitude = magnitude;
	}

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;

		this.startPoint = null;
		this.endPoint = null;

		this.magnitude = this.calcMagnitude();
	}

	public Vector(Point startPoint, Point endPoint) {
		this.x = endPoint.x - startPoint.y;
		this.y = endPoint.y - startPoint.y;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.magnitude = this.calcMagnitude();
	}

	public Vector add(Vector other) {
		return new Vector(x + other.x, y + other.y);
	}

	public Vector sub(Vector other) {
		return new Vector(x - other.x, y - other.y);
	}

	public Vector mul(Vector other) {
		return new Vector(x * other.x, y * other.y);
	}

	public double calcMagnitude() {
		return (double) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public double dot(Vector other) {
		return x * other.x + y * other.y;
	}

	public Vector normalize() {
		double mag = this.calcMagnitude();
		return new Vector (x / mag, y / mag);
	}

	public Vector scale(double s) {
		return new Vector(x * s, y * s);
	}

	public String toString() {
		return "Vector(" + x + ", " + y + ")";
	}

	/**
	 * @return the magnitude
	 */
	public double getMagnitude() {
		return magnitude;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public Point getStart() {
		return this.startPoint;
	}

	public Point getEnd() {
		return this.endPoint;
	}

	public double addX(double x) {
		return (double) x + this.x;
	}

	public double addY(double y) {
		return (double) x + this.y;
	}
}