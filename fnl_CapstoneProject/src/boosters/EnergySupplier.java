package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import main.*;

public class EnergySupplier extends Booster {
	public EnergySupplier(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y,strength, button, tankTactics, "Energy Supplier "+strength, new Color(255, 255, 224));
	}

	public String getType() {
		return Booster.ENERGY;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			if(current.getEnergy()>0) {
				current.gainEnergy(strength-1);
			}
		}
	}
}
