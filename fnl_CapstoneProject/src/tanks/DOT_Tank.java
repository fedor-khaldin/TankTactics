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
	
	//field
	private int damageOverTime;
	private Tank [] targets;
	private int [] times;

	//constructor
	public DOT_Tank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int damageOverTime, int votes, String password, JButton button, TankTactics tankTactics) {
		
				super(x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password, button, tankTactics);
				this.damageOverTime = damageOverTime;
	}

	//methods
	
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
	
	public void upgradeSpecial (int upgradeAmt)
	{
		damageOverTime += upgradeAmt;
		if (damageOverTime < 1)
			damageOverTime = 1;
	}
	
	public String getType()
	{
		return Tank.AOE;
	}
	
	public int getSpecial()
	{
		return damageOverTime;
	}
	
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
	
	@Override
	public String getSpecialText() {
		return "DOT: " + damageOverTime + "\n";
	}
}
