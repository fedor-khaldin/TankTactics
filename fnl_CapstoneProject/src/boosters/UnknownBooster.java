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

public class UnknownBooster extends Booster {
	public UnknownBooster(int x, int y, int strength, JButton button, TankTactics tankTactics, ImageIcon icon) {
		super(x, y, strength, button, tankTactics, icon);
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
										tankTactics.setActionsText("");
										break;
									case 2:
										players[i].heal(strength);
										tankTactics.setActionsText("");
										break;
									case 3:
										players[i].upgradeMaxEnergy(strength);
										tankTactics.setActionsText("");
									break;
									case 4:
										players[i].upgradeMaxLife(strength);
										tankTactics.setActionsText("");
										break;
									case 5:
										players[i].upgradeMovementRange(strength);
										tankTactics.setActionsText("");
										break;
									case 6:
										players[i].upgradePower(strength);
										tankTactics.setActionsText("");
										break;
									case 7:
										players[i].upgradeShootingRange(strength);
										tankTactics.setActionsText("");
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
			super.actionPerformed(e);	
		}
		tankTactics.draw();
	}
}	

