package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import main.*;

public class Shooter extends Booster {
	public Shooter(int x, int y, int strength, JButton button, TankTactics tankTactics, String name, Color color) {
		super(x, y,strength, button, tankTactics, name, color);
	}

	public String getType() {
		return Booster.SHOOT;
	}

}
