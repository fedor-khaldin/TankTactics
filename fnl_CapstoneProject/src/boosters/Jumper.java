package boosters;

import javax.swing.*;
import java.awt.*;
import main.*;

public class Jumper extends Booster {
	public Jumper(int x, int y, int strength, JButton button, TankTactics tankTactics, String name, Color color) {
		super(x, y,strength, button, tankTactics, name, color);
	}
}
