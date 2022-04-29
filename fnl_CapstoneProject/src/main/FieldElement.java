package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.JButton;

public class FieldElement implements ActionListener{
	protected int x, y;
	protected JButton button;
	protected TankTactics tankTactics;
	protected Color color;
	protected String name;
	public FieldElement(int x, int y, JButton button, TankTactics tankTactics, Color color, String name) {
		this.x = x;
		this.y = y;
		this.button = button;
		this.tankTactics = tankTactics;
		this.color = color;
		this.name = name;
		button.addActionListener(this);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getName() {
		return this.name;
	}

	public TankTactics getTankTactics() {
		return this.tankTactics;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	public void draw() {

	}

}
