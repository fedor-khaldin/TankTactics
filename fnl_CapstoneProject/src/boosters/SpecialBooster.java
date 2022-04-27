package boosters;

import javax.swing.*;
import java.awt.*;
import main.*;

public class SpecialBooster extends Booster {
	private int x;
	private int y;
	private int strength;
	private String name;
	private JButton button;
	private Color color;
	public SpecialBooster(int x, int y, int strength, String name, JButton button, Color color) {
		super(x, y,name, strength, button, color);
	}

	public int getStrength() {
		return this.strength;
	}
}
