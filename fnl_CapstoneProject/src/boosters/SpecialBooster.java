/*
 * Name: Wilson Wu
 * Date: 5/7/2022
 * Notes: This class represents a booster.
 */
package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import main.*;

public class SpecialBooster extends Booster {
	public SpecialBooster(int x, int y, int strength, JButton button, TankTactics tankTactics, ImageIcon icon) {
		super(x, y, strength, button, tankTactics, icon);
	}
	
	//returns the booster type
	public String getType() {
		return Booster.SPECIAL;
	}
	
	//when the player clicks on this booster, the player's special skill is increased
	@Override
	public void actionPerformed(ActionEvent e) {
		//super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		FieldElement[][] fieldElements = tankTactics.getFieldElements();
		JButton[][] buttons = tankTactics.getButtons();
		
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
			current.upgradeSpecial(strength);
			
			/*
			 * Once the tank touches the booster, fieldElement[x][y] becomes empty
			 * THEN after the tank leaves the spot, updates into a new fieldElement
			 */
			String iconPath = "";
			if (System.getProperty("os.name").contains("Windows")) {
				iconPath = "assets" + File.separator + "icons" + File.separator;
			} else {
				iconPath = "fnl_CapstoneProject" + File.separator + "assets" + File.separator + "icons" + File.separator;
			}
			
			if(current.getX() == this.x && current.getY() == this.y) {
				fieldElements[this.x][this.y] = current;
			}else {
				if((this.x+this.y)%2==0) {
					fieldElements[this.x][this.y] = new FieldElement(x, y, new JButton(), tankTactics, new ImageIcon(iconPath + "grass1.png"), "");
				}else {
					fieldElements[this.x][this.y] = new FieldElement(x, y, new JButton(), tankTactics, new ImageIcon(iconPath + "grass1.png"), "");
				}
			}
			
			buttons[this.x][this.y] = fieldElements[this.x][this.y].getButton();
			tankTactics.setButtons(buttons);
			tankTactics.setFieldElements(fieldElements);
		}
		tankTactics.draw();
	}
}
