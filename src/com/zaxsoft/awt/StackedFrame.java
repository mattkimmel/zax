// $Header: /Users/matt/cvsroot/JavaLib/awt/StackedFrame.java,v 1.3 2000/05/07 20:55:53 matt Exp $
//
// StackedFrame is a subclass of Frame which overrides some of the Container
// methods to insure that its components are drawn in descending order; that is,
// the components will be stacked such that component 0 is on top; component 1
// is under that, component 2 is under that, and so on.  java.awt.Container
// currently makes no such guarantees.
//
// Copyright (c) 1997-2000, Matthew E. Kimmel.
//
// $Log: StackedFrame.java,v $
// Revision 1.3  2000/05/07 20:55:53  matt
// Changed package to ZaxSoft.awt
//
// Revision 1.2  2000/05/07 20:24:57  matt
// Put under source control
//
//
package com.zaxsoft.awt;

import java.awt.Frame;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

public class StackedFrame extends java.awt.Frame {
	// Paint all components in the proper order.
	public void paintComponents(Graphics gc)
	{
		Component[] comps;
		Graphics compgc;
		Rectangle b;
		
		comps = getComponents();
		for (int i = (comps.length - 1);i >= 0;i--) {
			if (comps[i] == null)
				continue;
			b = comps[i].bounds();
			compgc = gc.create(b.x,b.y,b.width,b.height);
			comps[i].paintAll(compgc);
			compgc.dispose();
		}
	}

	// Print all components in the proper order.
	public void printComponents(Graphics gc)
	{
		Component[] comps;
		Graphics compgc;
		Rectangle b;
		
		comps = getComponents();
		for (int i = (comps.length - 1);i >= 0;i--) {
			if (comps[i] == null)
				continue;
			b = comps[i].bounds();
			compgc = gc.create(b.x,b.y,b.width,b.height);
			comps[i].printAll(compgc);
			compgc.dispose();
		}
	}
}
