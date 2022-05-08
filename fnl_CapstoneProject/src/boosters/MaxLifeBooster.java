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

public class MaxLifeBooster extends Booster {
	public MaxLifeBooster(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y,strength, button, tankTactics, "<html>Max<br>Life<br>Booster<br><html>"+strength, new Color(0, 255, 0));
	}
	
	//returns the booster type
	public String getType() {
		return Booster.MAX_LIFE;
	}
	
	//when clicked, the player's max life is increased
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.upgradeMaxLife(strength);
		}
		tankTactics.draw();
	}
}
