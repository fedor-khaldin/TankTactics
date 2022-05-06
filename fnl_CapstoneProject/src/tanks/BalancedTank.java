/*
 * Author: Itay Volk
 * Date: 5/6/2022
 * Rev: 02
 * Notes: this class repreasents a balanced tank
 */

package tanks;

import javax.swing.JButton;
import main.*;

public class BalancedTank extends Tank {

	//Fields
	private int lifeGain;
	
	//Constructor
	public BalancedTank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int lifeGain, int votes, String password,
			JButton button, TankTactics tankTactics, boolean onJumper, boolean onShooter) {
		
				super(x, y, name, power, shootingRange, movementRange, life, maxLife,
						energy, maxEnergy, votes, password, button, tankTactics, onJumper, onShooter);
				this.lifeGain = lifeGain;
	}
	
	//Methods
	//Overriden to add the effect of lifeGain
	public void heal(int healAmt)
	{
		if (healAmt > 0)
		{
			super.heal(healAmt * lifeGain);
		}
		else
		{
			super.heal(healAmt);
		}
	}
	
	//Implemented methods
	//Upgrades lifeGain
	public void upgradeSpecial (int upgradeAmt)
	{
		lifeGain += upgradeAmt;
		if (lifeGain < 1)
			lifeGain = 1;
	}
	
	//Returns the type of this tank
	public String getType()
	{
		return Tank.BALANCED;
	}
	
	//Returns lifeGain
	public int getSpecial()
	{
		return lifeGain;
	}
	
	//Returns a String representation of lifeGain
	@Override
	public String getSpecialText() {
		return "Life gain: " + lifeGain + "\n";
	}
}
