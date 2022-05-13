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
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

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
	
	//returns the button
	public JButton getButton() {
		return button;
	}
	
 	//sets a new button for the field element
	public void setButton(JButton button) {
		this.button.removeActionListener(this);
		this.button.setIcon(null);
		button.addActionListener(this);
		this.button = button;
	}
	
	//this happens when a player clicks on the field element
	@Override
	public void actionPerformed(ActionEvent e) {
		Tank currentPlayer = tankTactics.getCurrentPlayer();
		//if the field element is in the range of the current player
		if(this.x<=currentPlayer.getX()+currentPlayer.getMovementRange()&&this.y<=currentPlayer.getY()+currentPlayer.getMovementRange()&&currentPlayer.getEnergy()>0) {
			System.out.print("\nBefore\nCurrent: ("+currentPlayer.getButton().getX()+","+currentPlayer.getButton().getY()+")\nField Element: ("+this.button.getX()+","+this.button.getY()+")");
			//replaces current player with field element
			FieldElement[][] newField = tankTactics.getFieldElements();
			int x = currentPlayer.getX();
			int y = currentPlayer.getY();
			int thisX= this.x;
			int thisY = this.y;
				
			newField[x][y] = this;
			newField[thisX][thisY] = currentPlayer;
				
			currentPlayer.x = thisX;
			currentPlayer.y = thisY;
			this.x = x;
			this.y = y;
			tankTactics.setFieldElements(newField);
			
			//replaces the buttons position
			JButton[][] buttons = tankTactics.getButtons();
			JButton temp = buttons[x][y];
			JButton temp2 = this.button;
			buttons[x][y] = this.button;
			buttons[thisX][thisY] = button;
				
			currentPlayer.setButton(temp2);
			this.setButton(temp);
				
			//changes the fieldElement's color depending what it's x and y is
			if(this.x%2==0) {
				if(this.y%2==0) {
					color = new Color(69, 177, 72);
				}else {
					color = new Color(82, 188, 82);
				}
			}else {
				if(this.y%2==0) {
					color = new Color(82, 188, 82);
				}else {
					color = new Color(69, 177, 72);
				}
			}
				
			tankTactics.setButtons(buttons);
				
			currentPlayer.gainEnergy(-1);
				
			System.out.print("\nAfter\nCurrent: ("+currentPlayer.getButton().getX()+","+currentPlayer.getButton().getY()+")\nField Element: ("+this.button.getX()+","+this.button.getY()+")");
			
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
