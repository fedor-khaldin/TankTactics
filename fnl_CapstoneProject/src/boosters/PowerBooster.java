/*
 * Name: Wilson Wu
 * Date: 5/7/2022
 * Notes: This class represents a booster.
 */
package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import main.*;

public class PowerBooster extends Booster {
	public PowerBooster(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y,strength, button, tankTactics, "<html>Power<br>Booster<br><html>"+strength, new Color(255, 85, 0));
	}
	
	//returns the booster type
	public String getType() {
		return Booster.POWER;
	}
	
	//when clicked on, the player's power is increased
	@Override
	public void actionPerformed(ActionEvent e) {
		//super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		FieldElement[][] fieldElements = tankTactics.getFieldElements();
		JButton[][] buttons = tankTactics.getButtons();
		
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.upgradePower(strength);
			
			/*
			 * Once the tank touches the booster, fieldElement[x][y] becomes empty
			 * THEN after the tank leaves the spot, updates into a new fieldElement
			 */
			if(current.getX() == this.x && current.getY() == this.y) {
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
		}
		tankTactics.draw();
	}
}
