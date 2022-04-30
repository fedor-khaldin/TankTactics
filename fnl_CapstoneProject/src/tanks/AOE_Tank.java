/*
 * Author: Itay Volk
 * Date: 4/29/2022
 * Rev: 02
 * Notes: this class repreasents an AOE tank
 */

package tanks;

import javax.swing.JButton;
import main.*;

public class AOE_Tank extends Tank {
	
	//field
	private int areaOfEffect;

	//constructor
	public AOE_Tank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int areaOfEffect, int votes, String password, JButton button, TankTactics tankTactics) {
		
				super(x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password, button, tankTactics);
				this.areaOfEffect = areaOfEffect;
	}

	//methods
	
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
	
	public void upgradeSpecial (int upgradeAmt)
	{
		areaOfEffect += upgradeAmt;
		if (areaOfEffect < 1)
			areaOfEffect = 1;
	}
	
	public String getType()
	{
		return Tank.AOE;
	}
	
	public int getSpecial()
	{
		return areaOfEffect;
	}

	@Override
	public String getSpecialText() {
		return "AOE: " + areaOfEffect + "\n";
	}
}
