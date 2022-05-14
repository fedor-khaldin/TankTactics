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

public class UnknownBooster extends Booster {
	public UnknownBooster(int x, int y, int strength, JButton button, TankTactics tankTactics) {
		super(x, y,strength, button, tankTactics, "<html>Unknown<br><html>"+strength, new Color(167, 192, 218));
	}
	
	//returns the booster type
	public String getType() {
		return Booster.UNKNOWN;
	}	
	
	//when the player clicks on this booster, they recieved a random boost
	@Override
	public void actionPerformed(ActionEvent e) {
		//super.actionPerformed(e);	
		Tank current = tankTactics.getCurrentPlayer();
		FieldElement[][] fieldElements = tankTactics.getFieldElements();
		JButton[][] buttons = tankTactics.getButtons();
		
		if(this.x<current.getX()+current.getMovementRange()&&this.y<current.getY()+current.getMovementRange()) {
				int boost = (int)Math.random()*10+1;
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
						current.setOnShooter(true);
						break;
					case 9:
						//debuffs each player
						Tank[] players = tankTactics.getPlayers();
						int debuff = (int)Math.random()*10;
						for(int i = 0; i<players.length; i++) {
							//makes sure the player isn't the player itself
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
					case 10:
						current.upgradeShootingRange(strength);
						break;
			}
			
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

