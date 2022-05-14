/*
 * Name: Wilson wu
 * Date: 5/10/2022
 * Notes: A booster that debuffs all other players except for the current player.
 */
package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import main.*;

public class DebuffBooster extends Booster {
	public DebuffBooster(int x, int y,int strength, JButton button, TankTactics tankTactics) {
		super(x, y, strength, button, tankTactics, "<html>Jumper<br><html>", new Color(43, 0, 255));
	}
	
	//returns the booster type
	public String getType() {
		return Booster.DEBUFF;
	}
	
	//when booster is clicked, the player gains a extra move
	@Override
	public void actionPerformed(ActionEvent e) {
		//super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		FieldElement[][] fieldElements = tankTactics.getFieldElements();
		JButton[][] buttons = tankTactics.getButtons();
		Tank[] players = tankTactics.getPlayers();
	
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()&&current.getEnergy()>0) {
			//debuffs other players
			int debuff = (int)Math.random()*10;
			//loops through the player array
			for(int i = 0; i<players.length; i++) {
				//make sure it isn't the current player
				if(!players[i].getName().equals(current.getName())) {
					switch(debuff) {
						case 1:
							players[i].gainEnergy(strength);
							break;
						case 2:
							players[i].heal(strength);
							break;
						case 3:
							players[i].upgradeMaxEnergy(strength);
							break;
						case 4:
							players[i].upgradeMaxLife(strength);
							break;
						case 5:
							players[i].upgradeMovementRange(strength);
							break;
						case 6:
							players[i].upgradePower(strength);
							break;
						case 7:
							players[i].upgradeShootingRange(strength);
							break;
					}
				}
			}
			tankTactics.setPlayers(players);	
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