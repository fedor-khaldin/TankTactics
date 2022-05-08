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

public class PowerBooster extends Booster {
	public PowerBooster(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y,strength, button, tankTactics, "<html>Power<br>Booster<br><html>"+strength, new Color(255, 85, 0));
	}
	
	//returns the booster type
	public String getType() {
		return Booster.POWER;
	}
	
	//when clicked on, the player's power is increased
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.upgradePower(strength);
		}
		tankTactics.draw();
	}
}
