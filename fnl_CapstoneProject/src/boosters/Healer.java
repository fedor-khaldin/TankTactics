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

public class Healer extends Booster {
	public Healer(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y,strength, button, tankTactics, "<html>Healer<br><html>"+strength, new Color(255, 0, 0));
	}
	
	//returns the booster type
	public String getType() {
		return Booster.HEAL;
	}
	
	//heals the player when clicked on
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.heal(strength);
		}
		tankTactics.draw();
	}
}
