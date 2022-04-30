package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import main.*;

public class PowerBooster extends Booster {
	public PowerBooster(int x, int y, int strength, JButton button, TankTactics tankTactics, String name, Color color) {
		super(x, y,strength, button, tankTactics, name, color);
	}
	
	public String getType() {
		return Booster.POWER;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			if(current.getEnergy()>0) {
				current.upgradePower(strength);
				current.gainEnergy(-1);
			}
		}
	}
}
