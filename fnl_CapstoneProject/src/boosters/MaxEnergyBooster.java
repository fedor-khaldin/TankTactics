package boosters;

import javax.swing.*;
import java.awt.*;
import main.*;

public class MaxEnergyBooster extends Booster {
	private int x;
	private int y;
	private int strength;
	private JButton button;
	private Color color;
	public MaxEnergyBooster(int x, int y, int strength, JButton button) {
		super(x, y, strength, button);
		this.color = Color.white;
	}

}
