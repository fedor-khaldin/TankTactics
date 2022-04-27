package main;

import java.awt.Color;

import javax.swing.JButton;

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
	
	public Booster (int x, int y, String name, int strength, JButton button) {
		super(x, y, name, button);
		this.strength = strength;
	}

	public abstract int getStrength();

}
