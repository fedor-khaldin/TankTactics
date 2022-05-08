/*
 * Name: Wilson Wu
 * Date: 5/7/2022
 * Notes: This class represents a booster.
 */
package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import main.*;

public class SpecialBooster extends Booster {
	public SpecialBooster(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y, strength, button, tankTactics, "<html>Special<br>Booster<br><html>"+strength, new Color(0, 128, 255));
	}
	
	//returns the booster type
	public String getType() {
		return Booster.SPECIAL;
	}
	
	//when the player clicks on this booster, the player's special skill is increased
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.upgradeSpecial(strength);
		}
		tankTactics.draw();
	}
}
