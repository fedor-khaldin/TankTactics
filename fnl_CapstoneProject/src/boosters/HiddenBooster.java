package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import main.*;

public class HiddenBooster extends Booster {
	public HiddenBooster(int x, int y, int strength, JButton button, TankTactics tankTactics, String name, Color color) {
		super(x, y,strength, button, tankTactics, name, color);
	}

	public String getType() {
		return Booster.HIDDEN;
	}
}
