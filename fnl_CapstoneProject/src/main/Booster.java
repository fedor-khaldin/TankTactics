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
	
	public Booster (int x, int y, int strength, JButton button, TankTactics tankTactics, String name, Color color) {
		super(x, y, button, tankTactics, color, name);
		this.strength = strength;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//super.actionPerformed(e);
		
		//super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		FieldElement[][] fieldElements = tankTactics.getFieldElements();
		JButton[][] buttons = tankTactics.getButtons();
		
		/*
		 * Once the tank touches the booster, fieldElement[x][y] becomes empty
		 * THEN after the tank leaves the spot, updates into a new fieldElement
		 */
		if(current.x == this.x && current.y == current.y) {
			fieldElements[this.x][this.y] = current;
		}else {
			if((this.x+this.y)%2==0) {
				fieldElements[this.x][this.y] = new FieldElement(x, y, new JButton(), tankTactics, new Color(69, 177, 72), name);
			}else {
				fieldElements[this.x][this.y] = new FieldElement(x, y, new JButton(), tankTactics, new Color(82, 188, 82), name);
			}
		}
		
		buttons[this.x][this.y] = fieldElements[this.x][this.y].getButton();
		tankTactics.setButtons(buttons);
		tankTactics.setFieldElements(fieldElements);
		tankTactics.draw();
	}
	
	//returns the strength
	public int getStrength() {
		return strength;
	}
	
	//abstract method to return the type
	public abstract String getType();
	
}
