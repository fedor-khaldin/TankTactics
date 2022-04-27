package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.JButton;

public class FieldElement implements ActionListener{
	private int x, y;
	private String name;
	private JButton button;
	private Color color;
	public FieldElement(int x, int y, String name, JButton button, Color color) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.button = button;
		this.color = color;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void Draw(Graphics g) {

	}

}
