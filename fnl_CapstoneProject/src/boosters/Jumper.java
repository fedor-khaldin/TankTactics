/*
 * Name: Wilson Wu
 * Date: 5/7/2022
 * Notes: This class represents a booster.
 */
package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import main.*;

public class Jumper extends Booster {
	public Jumper(int x, int y, JButton button, TankTactics tankTactics, ImageIcon icon) {
		super(x, y, 0, button, tankTactics, icon);
	}
	
	//returns the booster type
	public String getType() {
		return Booster.JUMPER;
	}
	
	//when booster is clicked, the player gains a extra move
	@Override
	public void actionPerformed(ActionEvent e) {
		//super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.setOnJumper(true);
			tankTactics.setActionsText("");
			/*
			 * Once the tank touches the booster, fieldElement[x][y] becomes empty
			 * THEN after the tank leaves the spot, updates into a new fieldElement
			 */
			super.actionPerformed(e);	
		}
		tankTactics.draw();
	}
}
