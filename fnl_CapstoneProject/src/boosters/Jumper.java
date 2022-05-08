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

public class Jumper extends Booster {
	public Jumper(int x, int y, JButton button, TankTactics tankTactics) {
		super(x, y,0, button, tankTactics, "<html>Jumper<br><html>", new Color(43, 0, 255));
	}
	
	//returns the booster type
	public String getType() {
		return Booster.JUMPER;
	}
	
	//when booster is clicked, the player gains a extra move
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.setOnJumper(true);
		}
		tankTactics.draw();
	}
}
