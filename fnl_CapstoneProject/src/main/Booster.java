package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class Booster extends FieldElement {
	private int x, y;
	private String name;
	private int strength;
	private JButton button;

	public static final String POWER = "power";
	public static final String SHOOTING_RANGE = "shooting range";
	public static final String MOVEMENT_RANGE = "movement range";
	public static final String HEAL = "heal";
	public static final String MAX_LIFE = "max life";
	public static final String ENERGY = "energy";
	public static final String MAX_ENERGY = "max energy";
	public static final String SPECIAL = "special";
	public static final String SHOOT = "shoot";
	public static final String JUMPER = "jumper";
	public static final String UNKNOWN = "unknown";
	public static final String HIDDEN = "hidden";
	
	public Booster (int x, int y, String name, int strength, JButton button, Color color) {
		super(x, y, name, button, color);
		this.strength = strength;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void draw(Graphics g) {

	}


}
