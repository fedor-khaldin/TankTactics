/*
 * Author: Itay Volk
 * Date: 5/6/2022
 * Rev: 02
 * Notes: this class repreasents an AOE tank
 */

package tanks;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import main.*;

public class AOE_Tank extends Tank {
	
	//Fields
	private int areaOfEffect;

	//Constructor
	public AOE_Tank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int areaOfEffect, int votes, String password,
			JButton button, TankTactics tankTactics, boolean onJumper, boolean onShooter, ImageIcon icon) {
		
				super(x, y, name, power, shootingRange, movementRange, life, maxLife,
						energy, maxEnergy, votes, password, button, tankTactics, onJumper, onShooter, icon);
				this.areaOfEffect = areaOfEffect;
	}

	//Methods
	//Overriden to shoot multiple places
	public void hit(Tank other)
	{
		super.hit(other);
		
		for (int i = 0; i < tankTactics.getPlayers().length; i++)
		{
			if(tankTactics.getPlayers()[i].getX() <= x + areaOfEffect && tankTactics.getPlayers()[i].getX() >= x - areaOfEffect 
					&& tankTactics.getPlayers()[i].getY() <= y + areaOfEffect && tankTactics.getPlayers()[i].getY() >= y - areaOfEffect && tankTactics.getPlayers()[i] != this)
			{
				super.hit(tankTactics.getPlayers()[i]);
			}
		}
	}
	
	//Implemented methods
	//Updrages areaOfEffect
	public void upgradeSpecial (int upgradeAmt)
	{
		areaOfEffect += upgradeAmt;
		if (areaOfEffect < 1)
			areaOfEffect = 1;
	}
	
	//Returns the type of this tank
	public String getType()
	{
		return Tank.AOE;
	}
	
	//Returns areaOfEffect
	public int getSpecial()
	{
		return areaOfEffect;
	}

	//Returns a String representation of areaOfEffect
	@Override
	public String getSpecialText() {
		return "AOE: " + areaOfEffect + "\n";
	}
}
