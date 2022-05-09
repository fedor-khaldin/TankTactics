/*
 * Name: Wilson Wu
 * Date: 5/7/2022
 * Notes: This class represents a booster.
 */
package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import main.*;

public class MaxLifeBooster extends Booster {
	public MaxLifeBooster(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y,strength, button, tankTactics, "<html>Max<br>Life<br>Booster<br><html>"+strength, new Color(0, 255, 0));
	}
	
	//returns the booster type
	public String getType() {
		return Booster.MAX_LIFE;
	}
	
	//when clicked, the player's max life is increased
	@Override
	public void actionPerformed(ActionEvent e) {
		//super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		FieldElement[][] fieldElements = tankTactics.getFieldElements();
		JButton[][] buttons = tankTactics.getButtons();
		
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.upgradeMaxLife(strength);
			
			//creates a new field element in place of the booster
			if(this.x%2==0) {
				if(this.y%2==0) {
					fieldElements[this.x][this.y] = new FieldElement(x, y, button, tankTactics, new Color(69, 177, 72), name);
				}else {
					fieldElements[this.x][this.y] = new FieldElement(x, y, button, tankTactics, new Color(82, 188, 82), name);
				}
			}else {
				if(this.y%2==0) {
					fieldElements[this.x][this.y] = new FieldElement(x, y, button, tankTactics, new Color(82, 188, 82), name);
				}else {
					fieldElements[this.x][this.y] = new FieldElement(x, y, button, tankTactics, new Color(69, 177, 72), name);
				}
			}
			buttons[this.x][this.y] = fieldElements[this.x][this.y].getButton();
			tankTactics.setButtons(buttons);
			tankTactics.setFieldElements(fieldElements);
		}
		tankTactics.draw();
	}
}
