package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import main.*;

public class Jumper extends Booster {
	public Jumper(int x, int y, JButton button, TankTactics tankTactics) {
		super(x, y,0, button, tankTactics, "<html>Jumper<br><html>", new Color(43, 0, 255));
	}

	public String getType() {
		return Booster.JUMPER;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.setOnJumper(true);
			tankTactics.draw();
		}
	}
}
