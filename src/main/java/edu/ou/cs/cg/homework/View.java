//******************************************************************************
// Copyright (C) 2016 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Tue Mar  1 18:52:22 2016 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20160209 [weaver]:	Original file.
//
//******************************************************************************
// Notes:
//
//******************************************************************************

package edu.ou.cs.cg.homework;

//import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;
import javax.media.opengl.glu.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.TextRenderer;
import edu.ou.cs.cg.homework.Polygon;

//******************************************************************************

/**
 * The <CODE>Interaction</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class View
	implements GLEventListener
{
	//**********************************************************************
	// Public Class Members
	//**********************************************************************

	public static final int				DEFAULT_FRAMES_PER_SECOND = 60;
	private static final DecimalFormat	FORMAT = new DecimalFormat("0.000");

	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private final GLJPanel			canvas;
	private int						w;				// Canvas width
	private int						h;				// Canvas height

	private final KeyHandler		keyHandler;

	private final FPSAnimator		animator;
	private int						counter = 0;	// Frame display counter

	private TextRenderer			renderer;

	private Point2D.Double				origin;		// Current origin coordinates
	private Point2D.Double				cursor;		// Current cursor coordinates
	private Point 						ball;
	private Polygon square;
	private Polygon hexagon;
	private Polygon thirtyTwoGon;
	private Polygon convexGon;
	private Polygon currentPoly;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public View(GLJPanel canvas)
	{
		this.canvas = canvas;

		// Initialize model
		origin = new Point2D.Double(0.0, 0.0);
		cursor = null;

		// Initialize rendering
		canvas.addGLEventListener(this);
		animator = new FPSAnimator(canvas, DEFAULT_FRAMES_PER_SECOND);
		animator.start();

		// Initialize interaction
		keyHandler = new KeyHandler(this);
	}

	//**********************************************************************
	// Getters and Setters
	//**********************************************************************

	public int	getWidth()
	{
		return w;
	}

	public int	getHeight()
	{
		return h;
	}

	public Point2D.Double	getOrigin()
	{
		return new Point2D.Double(origin.x, origin.y);
	}

	public void		setOrigin(Point2D.Double origin)
	{
		this.origin.x = origin.x;
		this.origin.y = origin.y;
		canvas.repaint();
	}

	public void		clear()
	{
		canvas.repaint();
	}

	//**********************************************************************
	// Public Methods
	//**********************************************************************

	public Component	getComponent()
	{
		return (Component)canvas;
	}

	//**********************************************************************
	// Override Methods (GLEventListener)
	//**********************************************************************

	public void		init(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		w = drawable.getWidth();
		h = drawable.getHeight();

		// Square
		square = new Polygon(-0.5, 0.5, 4);
		currentPoly = square;
		// Hexagon
		hexagon = new Polygon(-0.5, -0.5, 6);

		// 32-gon
		thirtyTwoGon = new Polygon(0.5, -0.5, 32);

		ArrayList<Double> offsets = new ArrayList<Double>();
		offsets.add(80.0);
		offsets.add(10.0);
		offsets.add(10.0);
		offsets.add(40.0);
		offsets.add(10.0);
		offsets.add(25.0);
		offsets.add(25.0);
		offsets.add(25.0);
		offsets.add(25.0);
		offsets.add(60.0);
		offsets.add(10.0);
		offsets.add(30.0);

		convexGon = new Polygon(0.5, 0.5, offsets);
		ball = new Point(-0.5, 0.5);

		renderer = new TextRenderer(new Font("Monospaced", Font.PLAIN, 12), true, true);
	}

	public void		dispose(GLAutoDrawable drawable)
	{
		renderer = null;
	}

	public void		display(GLAutoDrawable drawable)
	{
		updateProjection(drawable);

		update(drawable);
		render(drawable);
	}

	public void		reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
	{
		this.w = w;
		this.h = h;
	}

	//**********************************************************************
	// Private Methods (Viewport)
	//**********************************************************************

	private void	updateProjection(GLAutoDrawable drawable)
	{
		GL2		gl = drawable.getGL().getGL2();
		GLU		glu = new GLU();

		float	xmin = (float)(origin.x - 1.0);
		float	xmax = (float)(origin.x + 1.0);
		float	ymin = (float)(origin.y - 1.0);
		float	ymax = (float)(origin.y + 1.0);

		gl.glMatrixMode(GL2.GL_PROJECTION);			// Prepare for matrix xform
		gl.glLoadIdentity();						// Set to identity matrix
		glu.gluOrtho2D(xmin, xmax, ymin, ymax);		// 2D translate and scale
	}

	//**********************************************************************
	// Private Methods (Rendering)
	//**********************************************************************

	private void	update(GLAutoDrawable drawable)
	{
		counter++;								// Counters are useful, right?
		ball.move();

		if (ball.isPolygon) {
			ArrayList<Vector> futureVectors = ball.futures();
			Vector future = ball.future();
			Vector maybeCollision = currentPoly.maybeCollisions(futureVectors);
			
			if (maybeCollision != null) {
				Vector normalizedCollision = maybeCollision.normalize();
				Vector reflectedVelocity = future.reflect(normalizedCollision);
				ball.setVelocity(reflectedVelocity);
			}
		} else {
			Vector future = ball.future();
			Vector maybeCollision = currentPoly.maybeCollision(future);

			if (maybeCollision != null) {
				Vector normalizedCollision = maybeCollision.normalize();
				Vector reflectedVelocity = future.reflect(normalizedCollision);
				ball.setVelocity(reflectedVelocity);
			}
		}

	}

	private void	render(GLAutoDrawable drawable)
	{
		GL2		gl = drawable.getGL().getGL2();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT);		// Clear the buffer

		ball.draw(gl);
		square.drawPolygon(gl);
		hexagon.drawPolygon(gl);
		thirtyTwoGon.drawPolygon(gl);
		convexGon.drawPolygon(gl);
	}

	public void ballSpeedChange(double i) {
		ball.modifyVelocity(i);
	}

	public void setCurrentPoly(int i) {
		switch (i) {
			case 1:
				currentPoly = square;
				ball.change(currentPoly.getCenter());
				break;
			case 2:
				currentPoly = convexGon;
				ball.change(currentPoly.getCenter());
				break;
			case 3:
				currentPoly = hexagon;
				ball.change(currentPoly.getCenter());
				break;
			case 4:
				currentPoly = thirtyTwoGon;
				ball.change(currentPoly.getCenter());
				break;
			default:
				break;
		}
	}

	public void setBallType(int i) {
		Point center = currentPoly.getCenter();
		switch (i) {
			case 6:
				// 6 - point
				ball = new Point(center.getX(), center.getY());
				break;
			case 7:
				// 7 - square
				ball = new Point(center.getX(), center.getY(), 4);
				break;
			case 8:
				// 8 - octagon
				ball = new Point(center.getX(), center.getY(), 8);
				break;
			case 9:
				// 9 - weird polygon
				ArrayList<Integer> offsets = new ArrayList<Integer>();
				offsets.add(35);
				offsets.add(55);
				offsets.add(25);
				offsets.add(40);
				offsets.add(90);
				ball = new Point(center.getX(), center.getY(), offsets);
				break;
			default:
				break;
		}
	}
}

//******************************************************************************
