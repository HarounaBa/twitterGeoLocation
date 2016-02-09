package org.harouna.model;

import java.applet.Applet;

import javax.swing.JApplet;

import javax.swing.JFrame;

public class JFrameAppletDemo extends Applet {
	
	@Override
	public void init() {
		new JFrame().setVisible(true);
	}
}
