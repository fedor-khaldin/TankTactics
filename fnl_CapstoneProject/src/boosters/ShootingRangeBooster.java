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

public class ShootingRangeBooster extends Booster {
	public ShootingRangeBooster(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y,strength, button, tankTactics, "<html>Shooting<br>Range<br>Booster<br><html>"+strength, new Color(0, 188, 255));
	}
	
	//returns the booster type
	public String getType() {
		return Booster.SHOOTING_RANGE;
	}
	
	//when the player clicks on the booster, the player's shooting range is increased
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {		
			current.upgradeShootingRange(strength);
		}
		tankTactics.draw();
	}
}
