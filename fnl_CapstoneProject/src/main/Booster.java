package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class Booster extends FieldElement {
	protected int strength;

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
	
	public Booster (int x, int y, int strength, JButton button, TankTactics tankTactics, String name, Color color) {
		super(x, y, button, tankTactics, color, name);
		this.strength = strength;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	public void draw() {
		super.draw();
	}
	
	public int getStrength() {
		return strength;
	}


}