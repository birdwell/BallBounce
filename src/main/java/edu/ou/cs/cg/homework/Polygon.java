package edu.ou.cs.cg.homework;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.awt.*;

public class Polygon {
	private double n;
	private double cx;
	private double cy;
	private ArrayList<Vector> sides;

	public Polygon(double cx, double cy, double n) {
		this.cx = cx;
		this.cy = cy;
		this.n = n;

		double offset = 360 / (double) n;
		double r = 0.25;
		this.sides = new ArrayList<Vector>();

		ArrayList<Point> points = new ArrayList<Point>();
		for (double theta = 0; theta < 360; theta += offset) {
			double x = cx + r * Math.cos(Math.toRadians(theta));
			double y = cy + r * Math.sin(Math.toRadians(theta));
			points.add(new Point(x, y));
		}

		for(int i = 0; i < n - 1; i++) {
			this.sides.add(new Vector(points.get(i), points.get(i + 1)));
		}
		this.sides.add(new Vector(points.get(points.size() - 1), points.get(0))); // Last Point
	}

	public void drawPolygon(GL2 gl) {
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
	}

	public Point getCenter() {
		return new Point(cx, cy);
	}

	public Vector maybeCollision(Vector future) {
		for (Vector side : sides) {
			if (intersection(side, future)) {
				return side;
			}
		}
		return null;
	}

	private Boolean intersection(Vector a, Vector b) {
		Point startA = a.getStart();
		Point endA = a.getEnd();
		Point startB = b.getStart();
		Point endB = b.getEnd();

		return Line2D.linesIntersect(
								startA.x, startA.y,
								endA.x, endA.y,
								startB.x, startB.y,
								endB.x, endB.y
							);
	}
}