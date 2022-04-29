package boosters;

import javax.swing.*;
import java.awt.*;
import main.*;

public class MovementRangeBooster extends Booster {
	public MovementRangeBooster(int x, int y, int strength, JButton button, TankTactics tankTactics, String name, Color color) {
		super(x, y,strength, button, tankTactics, name, color);
	}

	public String getType() {
		return Booster.MOVEMENT_RANGE;
	}
}