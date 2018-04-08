package edu.ou.cs.cg.homework;

import java.util.Random;

import java.awt.geom.Point2D;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.logging.Logger;

import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.awt.*;


public class Point extends Point2D.Double {
	private Vector velocity;
	private ArrayList<Point> points;
	private int n;
	private final double START_SPEED = 0.01667;
	public boolean isPolygon;
	private double radius;
	private ArrayList<Integer> pointOffsets;

	Random rand = new Random();

	public Point(double x, double y) {
		super(x, y);

		double xComponent = (rand.nextDouble() * 2.0 - 1.0) * this.START_SPEED;
		double yComponent = (double) Math.sqrt(Math.pow(this.START_SPEED, 2) - Math.pow(xComponent, 2));
		this.isPolygon = false;
		this.velocity = new Vector(xComponent, yComponent);
		this.points = null;
		this.n = 0;
		this.pointOffsets = null;
	}

	public Point(double x, double y, int n) {
		super(x, y);

		this.isPolygon = true;

		double xComponent = (rand.nextDouble() * 2.0 - 1.0) * this.START_SPEED;
		double yComponent = (double) Math.sqrt(Math.pow(this.START_SPEED, 2) - Math.pow(xComponent, 2));
		this.velocity = new Vector(xComponent, yComponent);
		this.n = n;
		this.pointOffsets = null;
		this.radius = 0.05;
		this.setupPoints();
	}

	public Point(double x, double y, ArrayList<Integer> pointOffsets) {
		super(x, y);

		this.isPolygon = true;

		double xComponent = (rand.nextDouble() * 2.0 - 1.0) * this.START_SPEED;
		double yComponent = (double) Math.sqrt(Math.pow(this.START_SPEED, 2) - Math.pow(xComponent, 2));
		this.velocity = new Vector(xComponent, yComponent);
		this.n = pointOffsets.size();
		this.radius = 0.05;
		this.pointOffsets = pointOffsets;
		this.setupPoints();
	}

	public void setupPoints () {
		this.points = new ArrayList<Point>();
		if (this.pointOffsets != null) {
			double theta = 0;
			for (int i = 0; i < n; i++) {
				theta += (double) pointOffsets.get(i);
				double cx = this.radius * Math.cos(Math.toRadians(theta));
				double cy = this.radius * Math.sin(Math.toRadians(theta));
				points.add(new Point(cx, cy));
			}
		} else {
			double offset = 360 / (double) this.n;
			for (double theta = 0; theta < 360; theta += offset) {
				double xPoint = this.radius * Math.cos(Math.toRadians(theta));
				double yPoint = this.radius * Math.sin(Math.toRadians(theta));
				points.add(new Point(xPoint, yPoint));
			}
		}
	}

	public void setVelocity(Vector newVelocity) {
		this.velocity = newVelocity;
	}

	public void modifyVelocity(double i) {
		this.velocity = this.velocity.scale(i);
	}

	public Point translate (double x, double y) {
		return new Point(this.x + x, this.y + y);
	}

	public void move() {
		this.x = this.velocity.addX(x);
		this.y = this.velocity.addY(y);
	}

	public void change(Point center) {
		this.x = center.getX();
		this.y = center.getY();
	}

	public Vector future() {
		return new Vector(this, new Point(this.velocity.addX(x), this.velocity.addY(y)));
	}

	public ArrayList<Vector> futures() {
		double futureX = this.velocity.addX(x);
		double futureY = this.velocity.addY(y);
		return getFutureSides(futureX, futureY);
	}

	private ArrayList<Vector> getFutureSides(double futureX, double futureY) {
		ArrayList<Vector> sides = new ArrayList<Vector>();

		for (int i = 0; i < this.n - 1; i++) {
			sides.add(
				new Vector(points.get(i).translate(futureX, futureY), 
				points.get(i + 1).translate(futureX, futureY))
			);
		}

		sides.add(
			new Vector(points.get(points.size() - 1).translate(futureX, futureY),
			points.get(0).translate(futureX, futureY))
		);

		return sides;
	}

	private ArrayList<Vector> getSides() {
		ArrayList<Vector> sides = new ArrayList<Vector>();
		for (int i = 0; i < this.n - 1; i++) {
			sides.add(new Vector(points.get(i).translate(this.x, this.y), points.get(i + 1).translate(this.x, this.y)));
		}
		sides.add(new Vector(points.get(points.size() - 1).translate(this.x, this.y), points.get(0).translate(this.x, this.y)));
		return sides;
	}

	public void draw(GL2 gl) {
		if (this.isPolygon) {
			ArrayList<Vector> sides = getSides();
			for (Vector side : sides) {
				gl.glBegin(GL.GL_LINES);
				gl.glColor3f(1.0f, 1.0f, 1.0f);

				Point start = side.getStart();
				Point end = side.getEnd();

				gl.glVertex2d(start.getX(), start.getY());
				gl.glVertex2d(end.getX(), end.getY());

				gl.glEnd();
			}
			gl.glEnd();
		} else {
			gl.glBegin(GL.GL_POINTS);
			gl.glColor3d(1.0f, 1.0f, 1.0f);
			// this.print();
			gl.glVertex2d(x, y);
			gl.glEnd();
		}
	}

	public void setRadius(double r) {
		this.radius *= r ;
		this.setupPoints();
	}

	public void print() {
		System.out.println("POINT: (" + x + "," + y + ")");
	}
}