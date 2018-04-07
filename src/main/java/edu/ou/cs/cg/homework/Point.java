package edu.ou.cs.cg.homework;

import java.util.Random;

import java.awt.geom.Point2D;
import javax.media.opengl.*;


public class Point extends Point2D.Double {
	private Vector velocity;
	private final double START_SPEED = 0.01667f;

	Random rand = new Random();

	public Point(double x, double y) {
		super(x, y);

		double randomdouble = (double) rand.nextDouble();
		double xComponent = (double) (randomdouble) * this.START_SPEED;
		double yComponent = (double) Math.sqrt(Math.pow(this.START_SPEED, 2) - Math.pow(xComponent, 2));

		this.velocity = new Vector(xComponent, yComponent);
	}

	public void move() {
		this.x = this.velocity.addX(x);
		this.y = this.velocity.addY(y);
	}

	public Vector future() {
		return new Vector(this, new Point(this.velocity.addX(x), this.velocity.addY(y)));
	}

	public void draw(GL2 gl) {
		gl.glBegin(GL.GL_POINTS);
		gl.glColor3d(1.0f, 1.0f, 1.0f);
		gl.glVertex2d(x, y);
		gl.glEnd();
	}
}