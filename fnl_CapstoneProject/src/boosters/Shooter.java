package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import main.*;

public class Shooter extends Booster {
	public Shooter(int x, int y, JButton button, TankTactics tankTactics) {
		super(x, y,0, button, tankTactics, "<html>Shooter<br><html>", new Color(0, 255, 255));
	}

	public String getType() {
		return Booster.SHOOT;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			
			tankTactics.draw();
		}
	}
}
