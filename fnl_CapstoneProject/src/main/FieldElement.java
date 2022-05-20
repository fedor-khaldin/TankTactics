/*
 * Written By: Wilson Wu
 * Date:5/3/2022
 * Rev: 01
 * Notes: This represents a field element.
 */
package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

public class FieldElement implements ActionListener{
	protected int x, y;
	protected JButton button;
	protected TankTactics tankTactics;
	private ImageIcon icon;
	protected String name;
	public FieldElement(int x, int y, JButton button, TankTactics tankTactics, ImageIcon icon, String name) {
		this.x = x;
		this.y = y;
		this.button = button;
		this.tankTactics = tankTactics;
		this.icon = icon;
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
			//System.out.print("\nBefore\nCurrent: ("+currentPlayer.getButton().getX()+","+currentPlayer.getButton().getY()+")\nField Element: ("+this.button.getX()+","+this.button.getY()+")");
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
			String iconPath = "";
			if (System.getProperty("os.name").contains("Windows")) {
				iconPath = "assets" + File.separator + "icons" + File.separator;
			} else {
				iconPath = "fnl_CapstoneProject" + File.separator + "assets" + File.separator + "icons" + File.separator;
			}
			
			if((this.x+this.y)%2==0) {
				icon = new ImageIcon(iconPath + "grass1.png");
			}else {
				icon = new ImageIcon(iconPath + "grass2.png");
			}
				
			tankTactics.setButtons(buttons);
				
			currentPlayer.gainEnergy(-1);
			tankTactics.setActionsText(currentPlayer.getName()+" moved to ("+currentPlayer.getX()+", "+currentPlayer.getY()+")");
		}
		tankTactics.draw();
	}	
	
	//draws the field element
	public void draw() {
		button.setPreferredSize(new Dimension(100,100));
		button.setVisible(true);
		Image img = icon.getImage();
		Image imgScale = img.getScaledInstance(button.getWidth(),button.getHeight(),Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(imgScale);
		button.setIcon(scaledIcon);
		button.setOpaque(true);
		button.setBorderPainted(false);
	}

}
