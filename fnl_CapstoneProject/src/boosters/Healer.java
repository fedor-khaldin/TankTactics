package boosters;

import java.awt.Color;

import javax.swing.*;
import java.awt.*;
import main.*;

public class Healer extends Booster {
	private int x;
	private int y;
	private int strength;
	private JButton button;
	private Color color;
	public Healer(int x, int y, int strength, JButton button) {
		// TODO Auto-generated constructor stub
		super(x, y, strength, button);
		this.color = Color.white;
	}

}
