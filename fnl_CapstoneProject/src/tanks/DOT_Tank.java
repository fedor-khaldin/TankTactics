/*
 * Author: Itay Volk
 * Date: 4/29/2022
 * Rev: 01
 * Notes: this class repreasents a DOT tank
 */

package tanks;

import javax.swing.JButton;
import main.*;

public class DOT_Tank extends Tank {
	
	//Fields
	private int damageOverTime;
	private Tank [] targets;
	private int [] times;

	//Constructor
	public DOT_Tank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int damageOverTime, int votes, String password, JButton button, TankTactics tankTactics) {
		
				super(x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password, button, tankTactics);
				this.damageOverTime = damageOverTime;
	}

	//Methods
	//Overriden to implement damagaOverTime
	public void hit(Tank other)
	{
		super.hit(other);
		
		boolean shot = false;
		for (int i = 0; i < targets.length; i++)
		{
			if (targets[i] == other)
			{
				times[i] += damageOverTime;
				shot = true;
				break;
			}
		}
		
		if (!shot)
		{
			Tank [] addedTargets = new Tank [targets.length + 1];
			int [] addedTimes = new int [times.length + 1];
			for (int i = 0; i < targets.length; i++)
			{
				addedTargets[i] = targets[i];
				addedTimes[i] = times[i];
			}
			addedTargets[targets.length] = other;
			targets = addedTargets;
			addedTimes[times.length] = damageOverTime;
			times = addedTimes;
		}
	}
	
	//Implemented methods
	//Upgrades damageOverTime
	public void upgradeSpecial (int upgradeAmt)
	{
		damageOverTime += upgradeAmt;
		if (damageOverTime < 1)
			damageOverTime = 1;
	}
	
	//Returns the type of this tank
	public String getType()
	{
		return Tank.DOT;
	}
	
	//Returns damageOverTime
	public int getSpecial()
	{
		return damageOverTime;
	}
	
	//Returns a String representation of damageOverTime
	@Override
	public String getSpecialText() {
		return "DOT: " + damageOverTime + "\n";
	}
	
	//New methods
	//Called whenever a cycle starts
	public void newCycle()
	{
		for(int i = 0; i < targets.length; i++)
		{
			super.hit(targets[i]);
			
			times[i]--;
			
			if (times[i] == 0)
			{
				Tank [] removedTargets = new Tank [targets.length - 1];
				int [] removedTimes = new int [times.length - 1];
				for (int j = 0; j < targets.length; j++)
				{
					if (j != i)
					{
						removedTargets[j] = targets[j];
						removedTimes[j] = times[j];
					}
				}
				targets = removedTargets;
				times = removedTimes;
			}
		}
	}
}
