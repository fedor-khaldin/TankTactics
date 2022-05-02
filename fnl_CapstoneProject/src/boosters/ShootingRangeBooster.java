package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import main.*;

public class ShootingRangeBooster extends Booster {
	public ShootingRangeBooster(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y,strength, button, tankTactics, "Shooting\nRange\nBooster\n"+strength, new Color(0, 188, 255));
	}

	public String getType() {
		return Booster.SHOOTING_RANGE;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {		
			current.upgradeShootingRange(strength);
		}
	}
}
