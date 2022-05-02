package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import main.*;

public class MaxLifeBooster extends Booster {
	public MaxLifeBooster(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y,strength, button, tankTactics, "Max\nLife\nBooster\n"+strength, new Color(0, 255, 0));
	}

	public String getType() {
		return Booster.MAX_LIFE;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.upgradeMaxLife(strength);
		}
	}
}
