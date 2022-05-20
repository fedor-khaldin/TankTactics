/*
 * Name: Wilson Wu
 * Date: 5/7/2022
 * Notes: This class represents a booster.
 */
package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class Booster extends FieldElement {
	protected int strength;
	
	//static variables for the different types of boosters
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
	public static final String DEBUFF = "debuff";
	
	public Booster (int x, int y, int strength, JButton button, TankTactics tankTactics, ImageIcon icon) {
		super(x, y, button, tankTactics, icon, ""+strength);
		this.strength = strength;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		tankTactics.draw();
	}
	
	//returns the strength
	public int getStrength() {
		return strength;
	}
	
	//abstract method to return the type
	public abstract String getType();
	
}
