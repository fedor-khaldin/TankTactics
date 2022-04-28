package boosters;

import java.awt.Color;

import javax.swing.*;
import java.awt.*;
import main.*;

public class Healer extends Booster {
	public Healer(int x, int y, int strength, JButton button, TankTactics tankTactics, String name, Color color) {
		super(x, y,strength, button, tankTactics, name, color);
	}
}
