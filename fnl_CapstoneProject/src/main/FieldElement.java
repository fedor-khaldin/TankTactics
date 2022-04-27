package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.JButton;

public class FieldElement implements ActionListener{
	private int x, y;
	private String name;
	private JButton button;
	public FieldElement(int x, int y, String name, JButton button) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.button = button;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static void newGame(JButton[][] buttons, int startingTime, int cycleLength) {
		// TODO Auto-generated method stub
		
	}

	public void Draw(Graphics g) {

	}

}
