//******************************************************************************
// Copyright (C) 2016 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Mon Feb 29 23:36:04 2016 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20160225 [weaver]:	Original file.
//
//******************************************************************************
// Notes:
//
//******************************************************************************

package edu.ou.cs.cg.homework;

//import java.lang.*;
import java.awt.*;
import java.awt.event.*;

//******************************************************************************

/**
 * The <CODE>KeyHandler</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class KeyHandler extends KeyAdapter
{
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private final View	view;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public KeyHandler(View view)
	{
		this.view = view;

		Component	component = view.getComponent();

		component.addKeyListener(this);
	}

	//**********************************************************************
	// Override Methods (KeyListener)
	//**********************************************************************

	public void		keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_1:
				view.setCurrentPoly(1);
				break;
			case KeyEvent.VK_2:
				view.setCurrentPoly(2);
				break;
			case KeyEvent.VK_3:
				view.setCurrentPoly(3);
				break;
			case KeyEvent.VK_4:
				view.setCurrentPoly(4);
				break;
			case KeyEvent.VK_LEFT:
				view.ballSpeedChange(0.9);
				break;
			case KeyEvent.VK_RIGHT:
				view.ballSpeedChange(1.1);
				break;
			case KeyEvent.VK_DOWN:
				view.setBallRadius(0.9);
				break;
			case KeyEvent.VK_UP:
				view.setBallRadius(1.1);
				break;
			case KeyEvent.VK_6:
				view.setBallType(6);
				break;
			case KeyEvent.VK_7:
				view.setBallType(7);
				break;
			case KeyEvent.VK_8:
				view.setBallType(8);
				break;
			case KeyEvent.VK_9:
				view.setBallType(9);
				break;
		}
	}
}

//******************************************************************************
