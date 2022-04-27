package boosters;

import javax.swing.JButton;
import main.*;

public class EnergySupplier extends Booster {
	private int x;
	private int y;
	private int strength;
	private JButton button;
	private Color color;
	public EnergySupplier(int x, int y, int strength, JButton button) {
		super(x, y, strength, button);
	}

}
