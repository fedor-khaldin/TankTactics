/*
 * Name: Wilson Wu
 * Date: 5/7/2022
 * Notes: This class represents a booster.
 */
package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import main.*;

public class MovementRangeBooster extends Booster {
	public MovementRangeBooster(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y,strength, button, tankTactics, "<html>Movement<br>Range<br>Booster<br><html>"+strength, new Color(255, 154, 0));
	}
	
	//returns the booster type
	public String getType() {
		return Booster.MOVEMENT_RANGE;
	}
	
	//when clicked on, the player's movement range is increase
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.upgradeMovementRange(strength);
		}
		tankTactics.draw();
	}
}
