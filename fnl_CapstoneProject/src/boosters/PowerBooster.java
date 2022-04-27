package boosters;

import javax.swing.*;
import java.awt.*;
import main.*;

public class PowerBooster extends Booster {
	private int x;
	private int y;
	private int strength;
	private String name;
	private JButton button;
	private Color color;
	public PowerBooster(int x, int y, int strength, String name, JButton button) {
		super(x, y,name, strength, button);
		this.color = Color.white;
	}

	public int getStrength() {
		return this.strength;
	}
}
