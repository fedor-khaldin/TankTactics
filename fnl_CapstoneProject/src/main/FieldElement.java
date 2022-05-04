/*
 * Written By: Wilson Wu
 * Date:5/3/2022
 * Rev: 01
 * Notes: This represents a field element.
 */
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
	
	public void setButton(JButton button) {
		this.button.removeActionListener(this);
		button.addActionListener(this);
		this.button = button;
	}
	
	//replace current player with field element
	@Override
	public void actionPerformed(ActionEvent e) {
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			if(current.getEnergy()>0) {
				//replaces current player with field element
				FieldElement[][] newField = tankTactics.getFieldElements();
				Tank currentPlayer = tankTactics.getCurrentPlayer();
				int x = currentPlayer.getX();
				int y = currentPlayer.getY();
				int thisX= this.getX();
				int thisY = this.getY();
				
				newField[x][y] = this;
				newField[thisX][thisY] = currentPlayer;
				
				currentPlayer.x = thisX;
				currentPlayer.y = thisY;
				this.x = x;
				this.y = y;
				tankTactics.setFieldElements(newField);
			
				//replaces buttons
				JButton[][] buttons = tankTactics.getButtons();
				JButton button = buttons[x][y];
				buttons[x][y] = this.button;
				buttons[thisX][thisY] = button;
				Color color = button.getBackground();
				button.setBackground(this.button.getBackground());
				this.button.setBackground(color);
				this.setButton(buttons[thisX][thisY]);
				tankTactics.setButtons(buttons);
				
				current.gainEnergy(-1);
				tankTactics.draw();
			}
		}
		
	}

	public void draw() {
		button.setText(name);
		button.setBackground(color);
		button.setOpaque(true);
		button.setBorderPainted(false);
	}

}
