/*
 * Name: Wilson Wu
 * Date: 5/7/2022
 * Notes: This class represents a booster.
 */
package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import main.*;

public class Shooter extends Booster {
	public Shooter(int x, int y, JButton button, TankTactics tankTactics, ImageIcon icon) {
		super(x, y, 0, button, tankTactics, icon);
	}
	
	//returns the booster type
	public String getType() {
		return Booster.SHOOT;
	}
	
	//allows the player to shoot again when clicked on
	@Override
	public void actionPerformed(ActionEvent e) {
		//super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		FieldElement[][] fieldElements = tankTactics.getFieldElements();
		JButton[][] buttons = tankTactics.getButtons();
		
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.setOnShooter(true);
			
			/*
			 * Once the tank touches the booster, fieldElement[x][y] becomes empty
			 * THEN after the tank leaves the spot, updates into a new fieldElement
			 */
			super.actionPerformed(e);	
		}
		tankTactics.draw();
	}
}
