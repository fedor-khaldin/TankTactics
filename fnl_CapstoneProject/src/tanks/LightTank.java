/*
 * Author: Itay Volk
 * Date: 5/6/2022
 * Rev: 02
 * Notes: this class repreasents a light tank
 */

package tanks;

import javax.swing.JButton;
import main.*;

public class LightTank extends Tank {

	//Fields
	private int energyGain;
	
	//Constructor
	public LightTank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int energyGain, int votes, String password,
			JButton button, TankTactics tankTactics, boolean onJumper, boolean onShooter) {
		
				super(x, y, name, power, shootingRange, movementRange, life, maxLife,
						energy, maxEnergy, votes, password, button, tankTactics, onJumper, onShooter);
				this.energyGain = energyGain;
	}
	
	//Methods
	//Overriden to implement energyGain
	public void gainEnergy(int rechargeAmt)
	{
		if (rechargeAmt > 0)
		{
			super.gainEnergy(rechargeAmt * energyGain);
		}
		else
		{
			super.gainEnergy(rechargeAmt);
		}
	}
	
	//Implemented methods
	//Upgrades energyGain
	public void upgradeSpecial (int upgradeAmt)
	{
		energyGain += upgradeAmt;
		if (energyGain < 1)
			energyGain = 1;
	}
	
	//Returns the type of this tank
	public String getType()
	{
		return Tank.LIGHT;
	}
	
	//Returns energyGain
	public int getSpecial()
	{
		return energyGain;
	}
	
	//Returns a String representation of energyGain
	@Override
	public String getSpecialText() {
		return "Energy gain: " + energyGain + "\n";
	}
}
