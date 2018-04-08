//******************************************************************************
// Copyright (C) 2018 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Sat Apr 07 2018 22:16:00 GMT-0500 (CDT) by Josh Birdwell
//******************************************************************************
// Major Modification History:
// 20180407 [birdwell]: Homework 4	
// 20160225 [weaver]:	Original file.
//
//******************************************************************************
// Notes:
/*
	Part 2 - Initial Reflection & Velocity Changer:

	Part 3 - Polygon Container Change & Dynamic Selection:

	Part 4 - Polygon Ball & Ball Size Changer:

	Part 5 - Add More Balls to Container: 
		Added the mouse click handler for adding more balls.
*/
//******************************************************************************
package edu.ou.cs.cg.homework;

//import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

//******************************************************************************

/**
 * The <CODE>MouseHandler</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class MouseHandler extends MouseAdapter {
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private final View view;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public MouseHandler(View view) {
		this.view = view;

		Component component = view.getComponent();

		component.addMouseListener(this);
		component.addMouseMotionListener(this);
		component.addMouseWheelListener(this);
	}

	//**********************************************************************
	// Override Methods (MouseListener)
	//**********************************************************************

	public void mouseClicked(MouseEvent e) {
		view.addBall();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	//**********************************************************************
	// Override Methods (MouseMotionListener)
	//**********************************************************************

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	//**********************************************************************
	// Override Methods (MouseWheelListener)
	//**********************************************************************

	public void mouseWheelMoved(MouseWheelEvent e) {
	}
}

//******************************************************************************
