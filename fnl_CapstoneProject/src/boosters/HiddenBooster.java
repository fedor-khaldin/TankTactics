package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import main.*;

public class HiddenBooster extends Booster {
	public HiddenBooster(int x, int y, int strength, JButton button, TankTactics tankTactics, Color color) {
		super(x, y,strength, button, tankTactics, "", color);
	}

	public String getType() {
		return Booster.HIDDEN;
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
			tankTactics.draw();
		}
	}
}
