/*
 * Author: Itay Volk
 * Date: 4/29/2022
 * Rev: 01
 * Notes: this class repreasents a light tank
 */

package tanks;

import javax.swing.JButton;
import main.*;

public class LightTank extends Tank {

	//fields
	private int energyGain;
	
	//constructor
	public LightTank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int energyGain, int votes, String password, JButton button, TankTactics game) {
				super(x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password, button, game);
				this.energyGain = energyGain;
	}
	
	//methods
	
	public void gainEnergy(int rechargeAmt)
	{
		if (rechargeAmt > 0)
		{
			super.gainEnergy(rechargeAmt * energyGain);
		}
	}
	
	public void upgradeSpecial (int upgradeAmt)
	{
		energyGain += upgradeAmt;
		if (energyGain < 1)
			energyGain = 1;
	}
	
	public String getType()
	{
		return Tank.LIGHT;
	}
	
	public int getSpecial()
	{
		return energyGain;
	}
	
	@Override
	public String getSpecialText() {
		return "Anergy gain: " + energyGain + "\n";
	}
}
