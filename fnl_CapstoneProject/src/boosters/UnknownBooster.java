package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import main.*;

public class UnknownBooster extends Booster {
	public UnknownBooster(int x, int y, int strength, JButton button, TankTactics tankTactics, String name, Color color) {
		super(x, y,strength, button, tankTactics, name, color);
	}

	public String getType() {
		return Booster.UNKNOWN;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
				Random random = new Random();
				int boost = random.nextInt(8) + 1;
				switch(boost) {
					case 1:
						current.gainEnergy(strength);
						break;
					case 2:
						current.heal(strength);
						break;
					case 3:
						current.setOnJumper(true);
						break;
					case 4:
						current.upgradeMaxEnergy(strength);
						break;
					case 5:
						current.upgradeMaxLife(strength);
						break;
					case 6:
						current.upgradeMovementRange(strength);
						break;
					case 7:
						current.upgradePower(strength);
						break;
					case 8:
						
						break;
					default:
						current.upgradeShootingRange(strength);
						break;
			}
		}
	}
}	

