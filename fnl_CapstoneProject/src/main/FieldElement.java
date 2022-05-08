/*
 * Written By: Wilson Wu
 * Date:5/3/2022
 * Rev: 01
 * Notes: This represents a field element.
 */
package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Button;
import java.awt.Color;
import javax.swing.JButton;

public class FieldElement implements ActionListener{
	protected int x, y;
	protected JButton button;
	protected TankTactics tankTactics;
	private Color color;
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
	
	//returns the x value
	public int getX() {
		return x;
	}
	
	//returns the y value
	public int getY() {
		return y;
	}
	
	//returns the name
	public String getName() {
		return name;
	}
	
	//returns the tank tactics
	public TankTactics getTankTactics() {
		return tankTactics;
	}
	
	//sets a new button for the field element
	public void setButton(JButton button) {
		this.button.removeActionListener(this);
		button.addActionListener(this);
		this.button = button;
	}
	
	//this happens when a player clicks on the field element
	@Override
	public void actionPerformed(ActionEvent e) {
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			if(current.getEnergy()>0) {
				//replaces current player with field element
				FieldElement[][] newField = tankTactics.getFieldElements();
			//	Tank currentPlayer = tankTactics.getCurrentPlayer();
				int x = current.getX();
				int y = current.getY();
				int thisX= this.x;
				int thisY = this.y;
				
				newField[x][y] = this;
				newField[thisX][thisY] = current;
				
				current.x = thisX;
				current.y = thisY;
				this.x = x;
				this.y = y;
				tankTactics.setFieldElements(newField);
			
				//replaces the buttons position
				JButton[][] buttons = tankTactics.getButtons();
				JButton temp = buttons[x][y];
				JButton temp2 = this.button;
				buttons[x][y] = this.button;
				buttons[thisX][thisY] = button;
				
				current.setButton(temp2);
				this.setButton(temp);
				tankTactics.setButtons(buttons);
				
//				Color color = button.getBackground();
//				String textString =  button.getText();
//				button.setBackground(this.button.getBackground());
//				button.setText(this.button.getText());
//				this.button.setBackground(color);
//				this.button.setText(textString);				
//				current.setButton(buttons[thisX][thisY]);
//				this.setButton(buttons[x][y]);
				
				current.gainEnergy(-1);
			}
		}
		tankTactics.draw();
	}	
	
	//draws the field element
	public void draw() {
		button.setText(name);
		button.setBackground(color);
		button.setOpaque(true);
		button.setBorderPainted(false);
	}

}
