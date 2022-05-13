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

public class HiddenBooster extends Booster {
	public HiddenBooster(int x, int y, int strength, JButton button, TankTactics tankTactics, Color color) {
		super(x, y,strength, button, tankTactics, "", color);
	}
	
	//returns the booster type
	public String getType() {
		return Booster.HIDDEN;
	}
	
	//the booster is hidden
	//if the player clicks on the booster, they will recieve a random boost
	@Override
	public void actionPerformed(ActionEvent e) {
		//super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		FieldElement[][] fieldElements = tankTactics.getFieldElements();
		JButton[][] buttons = tankTactics.getButtons();
		
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
				int boost = (int)Math.random()*10;
				switch(boost) {
					case 1:
						current.gainEnergy(strength);
						break;
					case 2:
						current.heal(strength);
						break;
					case 3:
						current.setOnJumper(true);
						break;
					case 4:
						current.upgradeMaxEnergy(strength);
						break;
					case 5:
						current.upgradeMaxLife(strength);
						break;
					case 6:
						current.upgradeMovementRange(strength);
						break;
					case 7:
						current.upgradePower(strength);
						break;
					case 8:
						Tank[] players = tankTactics.getPlayers();
						int debuff = (int)Math.random()*10;
						//loops through the player array to debuff the player
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
						break;
					default:
						current.upgradeShootingRange(strength);
						break;
				}
				//creates a new field element in place of the booster
				super.actionPerformed(e);	
//				if(this.x%2==0) {
//					if(this.y%2==0) {
//						fieldElements[this.x][this.y] = new FieldElement(x, y, button, tankTactics, new Color(69, 177, 72), name);
//					}else {
//						fieldElements[this.x][this.y] = new FieldElement(x, y, button, tankTactics, new Color(82, 188, 82), name);
//					}
//				}else {
//					if(this.y%2==0) {
//						fieldElements[this.x][this.y] = new FieldElement(x, y, button, tankTactics, new Color(82, 188, 82), name);
//					}else {
//						fieldElements[this.x][this.y] = new FieldElement(x, y, button, tankTactics, new Color(69, 177, 72), name);
//					}
//				}
//				buttons[this.x][this.y] = fieldElements[this.x][this.y].getButton();
//				tankTactics.setButtons(buttons);
//				tankTactics.setFieldElements(fieldElements);
		}
		tankTactics.draw();
	}
}
