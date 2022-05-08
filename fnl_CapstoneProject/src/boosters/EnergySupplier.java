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

public class EnergySupplier extends Booster {
	public EnergySupplier(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y,strength, button, tankTactics, "<html>Energy<br>Supplier<br><html>"+strength, new Color(255, 255, 224));
	}
	
	//returns the booster type
	public String getType() {
		return Booster.ENERGY;
	}
	
	//increases player's energy when clicked on
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.gainEnergy(strength);
		}
		tankTactics.draw();
	}
}
