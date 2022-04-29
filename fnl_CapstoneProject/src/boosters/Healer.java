package boosters;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.*;
import java.awt.*;
import main.*;

public class Healer extends Booster {
	public Healer(int x, int y, int strength, JButton button, TankTactics tankTactics, String name, Color color) {
		super(x, y,strength, button, tankTactics, name, color);
	}

	public String getType() {
		return Booster.HEAL;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.heal(strength);
		}
	}
}
