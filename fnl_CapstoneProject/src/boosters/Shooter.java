package boosters;

import javax.swing.*;
import java.awt.*;
import main.*;

public class Shooter extends Booster {
	private int x;
	private int y;
	private int strength;
	private String name;
	private JButton button;
	private Color color;
	public Shooter(int x, int y, int strength, String name, JButton button, Color color) {
		super(x, y,name, strength, button, color);
	}

}
