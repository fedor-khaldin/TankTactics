package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.JButton;

public class FieldElement implements ActionListener{
	private int x, y;
	private JButton button;
	private TankTactics tankTactics;
	public FieldElement(int x, int y, JButton button, TankTactics tankTactics) {
		this.x = x;
		this.y = y;
		this.button = button;
		this.tankTactics = tankTactics;
		button.addActionListener(this);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public TankTactics getTankTactics() {
		return this.tankTactics;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void Draw(Graphics g) {

	}

}
