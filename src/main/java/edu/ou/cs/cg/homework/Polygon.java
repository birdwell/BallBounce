//******************************************************************************
// Copyright (C) 2018 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Sat Apr 07 2018 22:16:00 GMT-0500 (CDT) by Josh Birdwell
//******************************************************************************
// Major Modification History:
// 20180407 [birdwell]: Homework 4	
// 
//
//******************************************************************************
// Notes:
/*
	Part 2 - Initial Reflection & Velocity Changer:
		Started with a simple square. Used the circle points knowing I would have more 
		complex polygons down the line. This is where the maybe collision and intersections
		were very important to get right. The drawing was simple yet extendable at this point.
	Part 3 - Polygon Container Change & Dynamic Selection:
		I added support for the other containers. There was weird offset work needed for the 
		convex polygon. The constructors need to be adabitble. 

*/
//******************************************************************************

package edu.ou.cs.cg.homework;

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

public class Polygon {
	private double n;
	private double cx;
	private double cy;
	private ArrayList<Vector> sides;

	public Polygon(double cx, double cy, ArrayList<Double> offsets) {
		this.cx = cx;
		this.cy = cy;
		this.n = offsets.size();
		
		double r = 0.25;
		this.sides = new ArrayList<Vector>();

		ArrayList<Point> points = new ArrayList<Point>();
		int theta = 0;
		for (Double offset : offsets) {
			theta += offset;
			double x = cx + r * Math.cos(Math.toRadians(theta));
			double y = cy + r * Math.sin(Math.toRadians(theta));
			points.add(new Point(x, y));
		}

		for (int i = 0; i < offsets.size() - 1; i++) {
			this.sides.add(new Vector(points.get(i), points.get(i + 1)));
		}
		this.sides.add(new Vector(points.get(points.size() - 1), points.get(0))); // Last Point
	}

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

	public Vector maybeCollisions(ArrayList<Vector> futureVectors) {
		for(Vector future: futureVectors) {
			for(Vector side: sides) {
				if(intersection(side, future)) {
					return side;
				}
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