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

public class MaxEnergyBooster extends Booster {
	public MaxEnergyBooster(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y,strength, button, tankTactics, "<html>Max<br>Energy<br>Booster<br><html>"+strength, new Color(145, 255, 0));
	}
	
	//returns the type
	public String getType() {
		return Booster.MAX_ENERGY;
	}
	
	//when the player clicks on the booster, their max energy is increased
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.upgradeMaxEnergy(strength);
		}
		tankTactics.draw();
	}
}
