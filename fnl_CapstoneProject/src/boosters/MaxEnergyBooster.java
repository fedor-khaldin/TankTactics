package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import main.*;

public class MaxEnergyBooster extends Booster {
	public MaxEnergyBooster(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y,strength, button, tankTactics, "Max\nEnergy\nBooster\n"+strength, new Color(145, 255, 0));
	}

	public String getType() {
		return Booster.MAX_ENERGY;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.upgradeMaxEnergy(strength);
		}
	}
}
