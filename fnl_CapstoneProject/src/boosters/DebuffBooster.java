/*
 * Name: Wilson wu
 * Date: 5/10/2022
 * Notes: A booster that debuffs all other players except for the current player.
 */
package boosters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import main.*;

public class DebuffBooster extends Booster {
	public DebuffBooster(int x, int y,int strength, JButton button, TankTactics tankTactics, ImageIcon icon) {
		super(x, y, strength, button, tankTactics, icon);
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
							tankTactics.setActionsText(current.getName()+" lost "+strength+" energy");
							break;
						case 2:
							players[i].heal(strength);
							tankTactics.setActionsText(current.getName()+" lost "+strength+" strength");
							break;
						case 3:
							players[i].upgradeMaxEnergy(strength);
							tankTactics.setActionsText(current.getName()+" lost "+strength+" max energy");
							break;
						case 4:
							players[i].upgradeMaxLife(strength);
							tankTactics.setActionsText(current.getName()+" lost "+strength+" max life");
							break;
						case 5:
							players[i].upgradeMovementRange(strength);
							tankTactics.setActionsText(current.getName()+" lost "+strength+" movement range");
							break;
						case 6:
							players[i].upgradePower(strength);
							tankTactics.setActionsText(current.getName()+" lost "+strength+" power");
							break;
						case 7:
							players[i].upgradeShootingRange(strength);
							tankTactics.setActionsText(current.getName()+" lost "+strength+" shooting range");
							break;
					}
				}
			}
			tankTactics.setPlayers(players);	
			/*
			 * Once the tank touches the booster, fieldElement[x][y] becomes empty
			 * THEN after the tank leaves the spot, updates into a new fieldElement
			 */
			super.actionPerformed(e);	
		}
		tankTactics.draw();
	}
	
}