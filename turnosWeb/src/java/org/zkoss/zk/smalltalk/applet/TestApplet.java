package org.zkoss.zk.smalltalk.applet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

public class TestApplet extends JApplet implements ActionListener {

	private static final long serialVersionUID = 1L;
	public void start() {
		super.start();
		
		System.out.println("Color Applet Starting");
	
		lblColor= new JLabel("COLOR APPLET");
		btnShowMessage= new JButton("SEND EVENT!!");
		pnlColor= new JPanel();
		pnlColor.setOpaque(true);
		pnlColor.setBackground(new Color(255,255,255));
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(pnlColor, BorderLayout.CENTER);
		this.getContentPane().add(lblColor, BorderLayout.NORTH);
		this.getContentPane().add(btnShowMessage, BorderLayout.SOUTH);
		
		this.btnShowMessage.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		JSObject jso = null;
		
		try {
			jso = JSObject.getWindow(this);
			jso.call("notifyServer", new Object[] {});
			System.out.println("notifyServer Fired!");
		} catch(JSException ex) {
			System.out.println("Could not create JS Object. Javascript Disabled!");
		}

	}
	
	public void changeColor(String red, String green, String blue) {
		this.pnlColor.setBackground(new Color(Integer.parseInt(red),Integer.parseInt(green),Integer.parseInt(blue)));
	}
	
	public void stop() {
		super.stop();
	}
	
	private JLabel lblColor= null;
	private JPanel pnlColor= null;
	private JButton btnShowMessage= null;
}
